package io.github.zhinushannan.lcplatformback.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.zhinushannan.lcplatformback.bean.ResultBean;
import io.github.zhinushannan.lcplatformback.dto.condition.PathBindPageCondition;
import io.github.zhinushannan.lcplatformback.dto.req.PathBindReq;
import io.github.zhinushannan.lcplatformback.dto.resp.PathBindResp;
import io.github.zhinushannan.lcplatformback.dto.resp.SideBarItemsResp;
import io.github.zhinushannan.lcplatformback.dto.resp.TableMetaInfoPathBindResp;
import io.github.zhinushannan.lcplatformback.service.PathBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("path-bind")
public class PathBindController {

    @Autowired
    private PathBindService pathBindService;

    /**
     * 分页查询业务绑定信息
     */
    @PostMapping("page")
    public ResultBean<IPage<PathBindResp>> page(@RequestBody PathBindPageCondition condition) {
        IPage<PathBindResp> page = pathBindService.page(condition);
        return ResultBean.success(page);
    }

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
    @PostMapping("add-dir")
    public ResultBean<String> addDir(@RequestBody PathBindReq pathBindReq) {
        pathBindService.addDir(pathBindReq);
        return ResultBean.success("添加成功！");
    }

    /**
     * 添加路径
     * 路径：
     * - parentId
     * - name
     * - tableMetaInfoId
     * - sort
     * - enable(默认启用)
     * <p>
     * 要求：
     * 1、name、tableMetaInfoId 均不能有重复
     * 2、parentId：需要存在 id 为 parentId 的目录
     * 3、需要存在 id 为 tableMetaInfoId 的表，并且该表没有被其他业务绑定
     */
    @PostMapping("add-path")
    public ResultBean<String> addPath(@RequestBody PathBindReq pathBindReq) {
        pathBindService.addPath(pathBindReq);
        return ResultBean.success("添加成功！");
    }

    /**
     * 删除目录
     * 注意：
     * 1、判定是不是目录
     * 2、如果是目录，需要删除自己和所属的路径
     */
    @DeleteMapping("delete")
    public ResultBean<String> deleteDir(@RequestParam("id") Long id) {
        pathBindService.delete(id);
        return ResultBean.success("删除成功！");
    }

    /**
     * 修改目录
     * 注意：
     * 1、判定是不是目录
     */
    @PostMapping("modify-dir")
    public ResultBean<String> modifyDir(@RequestBody PathBindReq pathBindReq) {
        pathBindService.modifyDir(pathBindReq);
        return ResultBean.success("更新成功");
    }

    /**
     * 修改路径
     * 注意：
     * 1、判定是不是路径
     */
    @PostMapping("modify-path")
    public ResultBean<String> modifyPath(@RequestBody PathBindReq pathBindReq) {
        pathBindService.modifyPath(pathBindReq);
        return ResultBean.success("更新成功");
    }

    /**
     * 条件查询目录列表
     */
    @GetMapping("query-dir")
    public ResultBean<List<PathBindResp>> queryDir(@RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "id", required = false) Long id) {
        List<PathBindResp> pathBindResps = pathBindService.queryDir(name, id);
        return ResultBean.success(pathBindResps);
    }

    /**
     * 条件查询数据表
     */
    @GetMapping("query-table")
    public ResultBean<List<TableMetaInfoPathBindResp>> queryTable(@RequestParam(value = "tableBusinessName", required = false) String tableBusinessName,
                                                                  @RequestParam(value = "id", required = false) Long id) {
        List<TableMetaInfoPathBindResp> pathBindResps = pathBindService.queryTable(tableBusinessName, id);
        return ResultBean.success(pathBindResps);

    }

    /**
     * 获取侧边栏菜单
     */
    @GetMapping("sidebar")
    public ResultBean<List<SideBarItemsResp>> getSidebar() {
        List<SideBarItemsResp> sideBars = pathBindService.getSideBar();
        return ResultBean.success(sideBars);
    }

    /**
     * 更改启用/禁用状态
     * 注意：
     * 1、不需要检查：禁用路径、启用目录
     * 2、需要检查：
     * 2.1 禁用目录时，需要把其下所有的路径全部禁用
     * 2.2 启用路径时，如果所属目录被禁用，则需要启用目录
     */
    @PostMapping("change-enable")
    public ResultBean<String> changeEnable(@RequestBody PathBindReq pathBindReq) {
        pathBindService.changeEnable(pathBindReq);
        return ResultBean.success("更新成功！");
    }

}
