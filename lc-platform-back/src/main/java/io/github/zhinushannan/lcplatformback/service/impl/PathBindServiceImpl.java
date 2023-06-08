package io.github.zhinushannan.lcplatformback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhinushannan.lcplatformback.bean.BaseEntity;
import io.github.zhinushannan.lcplatformback.dto.condition.PathBindPageCondition;
import io.github.zhinushannan.lcplatformback.dto.req.PathBindReq;
import io.github.zhinushannan.lcplatformback.dto.resp.PathBindResp;
import io.github.zhinushannan.lcplatformback.dto.resp.SideBarItemsResp;
import io.github.zhinushannan.lcplatformback.dto.resp.TableMetaInfoPathBindResp;
import io.github.zhinushannan.lcplatformback.entity.PathBind;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.PathBindException;
import io.github.zhinushannan.lcplatformback.mapper.PathBindMapper;
import io.github.zhinushannan.lcplatformback.service.PathBindService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PathBindServiceImpl extends ServiceImpl<PathBindMapper, PathBind> implements PathBindService {

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @Override
    public PathBind getByIdWithAssertNull(Long id) {
        return Optional.of(getById(id)).orElseThrow(() -> PathBindException.NOT_FOUNT);
    }

    @Override
    public IPage<PathBindResp> page(PathBindPageCondition condition) {
        // 分页查询目录，即没有父级的
        Page<PathBind> page = condition.getPage();
        QueryWrapper<PathBind> wrapper = condition.getQueryWrapper();
        wrapper.isNull("parent_id");
        this.page(page, wrapper);

        // 拿到所有的 目录的 id，如果为空，返回空值
        List<Long> ids = page.getRecords().stream().map(BaseEntity::getId).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return Page.of(1L, 0L);
        }

        // 查询所有路径，并根据 目录的id 进行分组
        condition.getQueryWrapper().clear();
        Map<Long, List<PathBind>> parentIdPathMap = this.list(condition.getQueryWrapper()
                        .in("parent_id", ids))
                .stream().collect(Collectors.groupingBy(PathBind::getParentId));

        return page.convert(pathBind -> {
            // 转换目录
            PathBindResp pathBindResp = BeanUtil.copyProperties(pathBind, PathBindResp.class);
            // 转换路径
            List<PathBind> pathBinds = Optional.ofNullable(parentIdPathMap.get(pathBindResp.getId())).orElse(new ArrayList<>());
            List<PathBindResp> pathBindResps = BeanUtil.copyToList(pathBinds, PathBindResp.class);
            // 组装并返回
            pathBindResp.setChildren(pathBindResps);
            return pathBindResp;
        });
    }

    @Override
    public void addDir(PathBindReq pathBindReq) {
        // 判别是否存在重复的 name 或 prefix
        long count = this.count(new QueryWrapper<PathBind>().eq("name", pathBindReq.getName()).or().eq("prefix", pathBindReq.getPrefix()));
        if (count > 0) {
            throw PathBindException.PATH_NAME_OR_PREFIX_REPEAT;
        }
        // 保存数据
        PathBind dir = PathBind.builder()
                .name(Opt.ofBlankAble(pathBindReq.getName()).orElseThrow(() -> PathBindException.DIR_NAME_BLANK))
                .icon(pathBindReq.getIcon())
                .prefix(Opt.ofBlankAble(pathBindReq.getPrefix()).orElseThrow(() -> PathBindException.DIR_PATH_BLANK))
                .sort(pathBindReq.getSort())
                .build();
        this.save(dir);
    }

    @Override
    public void addPath(PathBindReq pathBindReq) {
        // 查询目录
        PathBind pathBind = this.getByIdWithAssertNull(pathBindReq.getParentId());
        // 查询表
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getByIdWithAssertNull(pathBindReq.getTableMetaInfoId());
        // 保存
        PathBind path = PathBind.builder()
                .parentId(pathBind.getId())
                .name(Opt.ofBlankAble(pathBindReq.getName()).orElseThrow(() -> PathBindException.DIR_NAME_BLANK))
                .tableMetaInfoId(tableMetaInfo.getId())
                .sort(pathBindReq.getSort())
                .build();
        this.save(path);
    }

    @Override
    @Transactional
    public void delete(Long dirId) {
        this.getByIdWithAssertNull(dirId);
        // 删除目录和所属路径
        this.remove(new QueryWrapper<PathBind>().eq("parent_id", dirId));
        this.removeById(dirId);
    }

    @Override
    public void modifyDir(PathBindReq pathBindReq) {
        PathBind pathBind = this.getByIdWithAssertNull(pathBindReq.getId());
        if (pathBind.getParentId() != null) {
            throw PathBindException.NOT_IS_DIR;
        }
        PathBind pathBindUpdate = BeanUtil.copyProperties(pathBindReq, PathBind.class);
        this.updateById(pathBindUpdate);
    }

    @Override
    public void modifyPath(PathBindReq pathBindReq) {
        PathBind pathBind = this.getByIdWithAssertNull(pathBindReq.getId());
        if (pathBind.getParentId() == null) {
            throw PathBindException.NOT_IS_PATH;
        }
        PathBind pathBindUpdate = BeanUtil.copyProperties(pathBindReq, PathBind.class);
        this.updateById(pathBindUpdate);
    }

    @Override
    public List<PathBindResp> queryDir(String name, Long id) {
        if (StrUtil.isBlank(name) && id == null) {
            return Collections.emptyList();
        }

        List<PathBind> pathBinds = this.list(
                new QueryWrapper<PathBind>()
                        .like(id == null, "name", name)
                        .eq(id != null, "id", id)
                        .isNull("parent_id")
                        .orderByAsc("sort")
        );
        return BeanUtil.copyToList(pathBinds, PathBindResp.class);
    }

    @Override
    public List<TableMetaInfoPathBindResp> queryTable(String tableBusinessName, Long id) {
        if (StrUtil.isBlank(tableBusinessName) && id == null) {
            return Collections.emptyList();
        }

        List<TableMetaInfo> tableMetaInfos = tableMetaInfoService.list(
                new QueryWrapper<TableMetaInfo>()
                        .like(id == null, "business_table_name", tableBusinessName)
                        .eq(id != null, "id", id)
        );

        if (tableMetaInfos.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> tableIds = tableMetaInfos.stream().map(TableMetaInfo::getId).collect(Collectors.toList());
        Map<Long, PathBind> tableMetaInfoIdPathBindMap = this.list(new QueryWrapper<PathBind>().in("table_meta_info_id", tableIds).orderByAsc("sort")).stream().collect(Collectors.toMap(PathBind::getTableMetaInfoId, p -> p));

        List<TableMetaInfoPathBindResp> tableMetaInfoPathBindResps = BeanUtil.copyToList(tableMetaInfos, TableMetaInfoPathBindResp.class);
        tableMetaInfoPathBindResps.forEach(tableMetaInfoPathBindResp -> tableMetaInfoPathBindResp.setPathBindResp(BeanUtil.copyProperties(tableMetaInfoIdPathBindMap.get(tableMetaInfoPathBindResp.getId()), PathBindResp.class)));

        return tableMetaInfoPathBindResps;
    }

    @Override
    public List<SideBarItemsResp> getSideBar() {
        // 查询所有启用的目录/路径
        List<PathBind> list = this.list(new QueryWrapper<PathBind>().eq("enable", true).orderByAsc("sort"));

        // 筛选出 目录
        List<PathBind> dir = list.stream().filter(pathBind -> pathBind.getParentId() == null).collect(Collectors.toList());

        // 移除目录后，剩下的就是路径
        list.removeAll(dir);
        Map<Long, List<PathBind>> parentIdPathMap = list.stream().collect(Collectors.groupingBy(PathBind::getParentId));

        // 查询路径对应的表
        Set<Long> tableIds = list.stream().map(PathBind::getTableMetaInfoId).collect(Collectors.toSet());
        Map<Long, TableMetaInfo> tableIdTableMap = tableIds.isEmpty() ? new HashMap<>() : tableMetaInfoService.listByIds(tableIds).stream().collect(Collectors.toMap(TableMetaInfo::getId, t -> t));

        return dir.stream().map(dir1 -> SideBarItemsResp.builder()
                .icon(dir1.getIcon())
                .index(dir1.getName())
                .title(dir1.getName())
                .subs(Optional.ofNullable(parentIdPathMap.get(dir1.getId())).orElse(new ArrayList<>()).stream()
                        .map(path -> SideBarItemsResp.SubSideBar.builder()
                                .tableId(path.getTableMetaInfoId())
                                .index("/lc/" + dir1.getPrefix() + "/" + tableIdTableMap.get(path.getTableMetaInfoId()).getLogicTableName())
                                .title(path.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeEnable(PathBindReq pathBindReq) {
        Long id = pathBindReq.getId();

        PathBind pathBind = this.getByIdWithAssertNull(id);

        // 操作的是路径
        if (pathBind.getParentId() != null) {
            pathBind.setEnable(pathBindReq.getEnable());
            this.updateById(pathBind);

            // 是否是启用路径
            if (pathBind.getEnable()) {
                // 查询所属目录
                PathBind dir = this.getById(pathBind.getParentId());
                // 如果该目录被禁用了，则进行启用
                if (!dir.getEnable()) {
                    dir.setEnable(Boolean.TRUE);
                    this.updateById(dir);
                }
            }
        }
        // 操作的是目录
        else {
            pathBind.setEnable(pathBindReq.getEnable());
            this.updateById(pathBind);
            // 判断是否是禁用
            if (!pathBind.getEnable()) {
                List<PathBind> list = this.list(new QueryWrapper<PathBind>().eq("parent_id", pathBind.getId()));
                List<PathBind> collect = list.stream().peek(p -> p.setEnable(pathBindReq.getEnable())).collect(Collectors.toList());
                this.updateBatchById(collect);
            }
        }
    }

}
