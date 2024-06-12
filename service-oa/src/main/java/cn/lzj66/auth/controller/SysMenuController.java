package cn.lzj66.auth.controller;

import cn.lzj66.auth.service.SysMenuService;
import cn.lzj66.entity.system.SysMenu;
import cn.lzj66.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: SysMenuController
 * Package: cn.lzj66.system.controller
 * Description:菜单管理接口
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/5 11:01
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 菜单类型，分为：目录、菜单与按钮
     * 目录：一个分类（可理解为一级菜单）、目录下级节点可以为目录与菜单
     * 菜单：一个具体页面，菜单的下级节点只能是按钮
     * 按钮：页面上的功能
     * @return
     */
    @ApiOperation("获取菜单")
    @GetMapping("findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }
    @ApiOperation("新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation("删除菜单(逻辑删除)")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }
}
