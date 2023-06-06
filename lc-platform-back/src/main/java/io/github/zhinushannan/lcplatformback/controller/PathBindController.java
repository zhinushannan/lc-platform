package io.github.zhinushannan.lcplatformback.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Opt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zhinushannan.lcplatformback.bean.BaseEntity;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.condition.PathBindPageCondition;
import io.github.zhinushannan.lcplatformback.dto.req.PathBindReq;
import io.github.zhinushannan.lcplatformback.dto.resp.PathBindResp;
import io.github.zhinushannan.lcplatformback.dto.resp.TableMetaInfoPathBindResp;
import io.github.zhinushannan.lcplatformback.entity.PathBind;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;
import io.github.zhinushannan.lcplatformback.exception.PathBindException;
import io.github.zhinushannan.lcplatformback.service.PathBindService;
import io.github.zhinushannan.lcplatformback.service.TableMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RestController
@RequestMapping("path-bind")
public class PathBindController {

    @Autowired
    private PathBindService pathBindService;

    @Autowired
    private TableMetaInfoService tableMetaInfoService;

    @PostMapping("page")
    public ResultBean<IPage<PathBindResp>> page(@RequestBody PathBindPageCondition condition) {
        Page<PathBind> page = condition.getPage();
        QueryWrapper<PathBind> wrapper = condition.getQueryWrapper();
        wrapper.isNull("parent_id");
        pathBindService.page(page, wrapper);

        List<Long> ids = page.getRecords().stream().map(BaseEntity::getId).collect(Collectors.toList());

        if (ids.isEmpty()) {
            Page<PathBindResp> nullPage = Page.of(1L, 0L);
            return ResultBean.success(nullPage);
        }

        Map<Long, List<PathBind>> parentIdPathMap = pathBindService.list(new QueryWrapper<PathBind>().in("parent_id", ids)).stream().collect(Collectors.groupingBy(PathBind::getParentId));

        IPage<PathBindResp> convert = page.convert(pathBind -> {
            PathBindResp pathBindResp = BeanUtil.copyProperties(pathBind, PathBindResp.class);
            List<PathBind> pathBinds = Optional.ofNullable(parentIdPathMap.get(pathBindResp.getId())).orElse(new ArrayList<>());
            List<PathBindResp> pathBindResps = BeanUtil.copyToList(pathBinds, PathBindResp.class);
            pathBindResp.setChildren(pathBindResps);
            return pathBindResp;
        });
        return ResultBean.success(convert);
    }

    @PostMapping("add-dir")
    public ResultBean<String> addDir(@RequestBody PathBindReq pathBindReq) {
        PathBind dir = PathBind.builder()
                .name(Opt.ofBlankAble(pathBindReq.getName()).orElseThrow(() -> PathBindException.DIR_NAME_BLANK))
                .prefix(Opt.ofBlankAble(pathBindReq.getPrefix()).orElseThrow(() -> PathBindException.DIR_PATH_BLANK))
                .build();
        pathBindService.save(dir);
        return ResultBean.success("添加成功！");
    }

    @PostMapping("add-path")
    public ResultBean<String> addPath(@RequestBody PathBindReq pathBindReq) {
        PathBind pathBind = pathBindService.getByIdWithAssertNull(pathBindReq.getParentId());
        TableMetaInfo tableMetaInfo = tableMetaInfoService.getByIdWithAssertNull(pathBindReq.getTableMetaInfoId());

        PathBind build = PathBind.builder()
                .parentId(pathBind.getId())
                .name(Opt.ofBlankAble(pathBindReq.getName()).orElseThrow(() -> PathBindException.DIR_NAME_BLANK))
                .tableMetaInfoId(tableMetaInfo.getId())
                .build();
        pathBindService.save(build);
        return ResultBean.success("添加成功！");
    }

    @DeleteMapping("delete-dir")
    public ResultBean<String> deleteDir(@RequestParam("dirId") Long dirId) {
        PathBind pathBind = pathBindService.getByIdWithAssertNull(dirId);


        if (pathBind.getParentId() != null) {
            throw PathBindException.NOT_IS_DIR;
        }

        pathBindService.remove(new QueryWrapper<PathBind>().eq("parent_id", dirId));
        pathBindService.removeById(dirId);

        return ResultBean.success("删除成功！");
    }

    @DeleteMapping("delete-path")
    public ResultBean<String> deletePath(@RequestParam("pathId") Long pathId) {
        PathBind pathBind = pathBindService.getByIdWithAssertNull(pathId);

        if (pathBind.getParentId() == null) {
            throw PathBindException.NOT_IS_PATH;
        }

        pathBindService.removeById(pathId);

        return ResultBean.success("删除成功！");
    }


    @PostMapping("modify-dir")
    public ResultBean<String> modifyDir(@RequestBody PathBindReq pathBindReq) {
        pathBindService.getByIdWithAssertNull(pathBindReq.getId());

        PathBind pathBindUpdate = BeanUtil.copyProperties(pathBindReq, PathBind.class);
        pathBindService.updateById(pathBindUpdate);
        return ResultBean.success("更新成功");
    }

    @PostMapping("modify-path")
    public ResultBean<String> modifyPath(@RequestBody PathBindReq pathBindReq) {
        PathBind pathBind = pathBindService.getByIdWithAssertNull(pathBindReq.getId());

        if (!pathBind.getParentId().equals(Optional.of(pathBindReq.getParentId()).orElseThrow(() -> PathBindException.PARENT_ID_NULL))) {
            pathBindService.getByIdWithAssertNull(pathBindReq.getId());
        }


        PathBind pathBindUpdate = BeanUtil.copyProperties(pathBindReq, PathBind.class);
        pathBindService.updateById(pathBindUpdate);
        return ResultBean.success("更新成功");
    }

    @GetMapping("query-dir")
    public ResultBean<List<PathBindResp>> queryDir(@RequestParam("name") String name) {
        List<PathBind> pathBinds = pathBindService.list(new QueryWrapper<PathBind>().like("name", name).isNull("parent_id"));
        List<PathBindResp> pathBindResps = BeanUtil.copyToList(pathBinds, PathBindResp.class);
        return ResultBean.success(pathBindResps);
    }

    @GetMapping("query-table")
    public ResultBean<List<TableMetaInfoPathBindResp>> queryTable(@RequestParam("tableBusinessName") String tableBusinessName) {
        List<TableMetaInfo> tableMetaInfos = tableMetaInfoService.list(new QueryWrapper<TableMetaInfo>().like("business_table_name", tableBusinessName));

        if (tableMetaInfos.isEmpty()) {
            return ResultBean.success(new ArrayList<>());
        }

        List<Long> tableIds = tableMetaInfos.stream().map(TableMetaInfo::getId).collect(Collectors.toList());
        Map<Long, PathBind> tableMetaInfoIdPathBindMap = pathBindService.list(new QueryWrapper<PathBind>().in("table_meta_info_id", tableIds)).stream().collect(Collectors.toMap(PathBind::getTableMetaInfoId, p -> p));

        List<TableMetaInfoPathBindResp> tableMetaInfoPathBindResps = BeanUtil.copyToList(tableMetaInfos, TableMetaInfoPathBindResp.class);
        tableMetaInfoPathBindResps.forEach(tableMetaInfoPathBindResp -> tableMetaInfoPathBindResp.setPathBindResp(BeanUtil.copyProperties(tableMetaInfoIdPathBindMap.get(tableMetaInfoPathBindResp.getId()), PathBindResp.class)));

        return ResultBean.success(tableMetaInfoPathBindResps);
    }

}
