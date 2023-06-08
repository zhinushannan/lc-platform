package io.github.zhinushannan.lcplatformback.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.zhinushannan.lcplatformback.dto.condition.PathBindPageCondition;
import io.github.zhinushannan.lcplatformback.dto.req.PathBindReq;
import io.github.zhinushannan.lcplatformback.dto.resp.PathBindResp;
import io.github.zhinushannan.lcplatformback.dto.resp.SideBarItemsResp;
import io.github.zhinushannan.lcplatformback.dto.resp.TableMetaInfoPathBindResp;
import io.github.zhinushannan.lcplatformback.entity.PathBind;

import java.util.List;

public interface PathBindService extends IService<PathBind> {
    PathBind getByIdWithAssertNull(Long id);

    /**
     * 分页查询业务绑定信息
     */
    IPage<PathBindResp> page(PathBindPageCondition condition);

    /**
     * 添加目录
     * 目录：
     * - name
     * - icon
     * - prefix
     * - sort
     * - enable(默认启用)
     * <p>
     * 要求：name、prefix 均不能有重复
     */
    void addDir(PathBindReq pathBindReq);

    /**
     * 添加路径
     * 路径：
     * - parentId
     * - name
     * - tableMetaInfoId
     * - sort
     * - enable(默认启用)
     * <p>
     * 要求：name、tableMetaInfoId 均不能有重复
     */
    void addPath(PathBindReq pathBindReq);

    /**
     * 删除目录
     * 注意：
     * 1、判定是不是目录
     * 2、如果是目录，需要删除自己和所属的路径
     */
    void deleteDir(Long dirId);

    /**
     * 删除路径
     * 注意：
     * 1、判定是不是路径
     */
    void deletePath(Long pathId);

    /**
     * 修改目录
     * 注意：
     * 1、判定是不是目录
     */
    void modifyDir(PathBindReq pathBindReq);

    /**
     * 修改路径
     * 注意：
     * 1、判定是不是路径
     */
    void modifyPath(PathBindReq pathBindReq);

    /**
     * 条件查询目录列表
     */
    List<PathBindResp> queryDir(String name, Long id);

    /**
     * 条件查询数据表
     */
    List<TableMetaInfoPathBindResp> queryTable(String tableBusinessName, Long id);

    /**
     * 获取侧边栏菜单
     */
    List<SideBarItemsResp> getSideBar();

    /**
     * 更改启用/禁用状态
     * 注意：
     * 1、不需要检查：禁用路径、启用目录
     * 2、需要检查：
     * 2.1 禁用目录时，需要把其下所有的路径全部禁用
     * 2.2 启用路径时，如果所属目录被禁用，则需要启用目录
     */
    void changeEnable(PathBindReq pathBindReq);
}
