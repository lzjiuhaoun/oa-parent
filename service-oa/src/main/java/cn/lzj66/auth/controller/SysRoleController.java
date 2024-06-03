package cn.lzj66.auth.controller;

import cn.lzj66.auth.service.SysRoleService;
import cn.lzj66.entity.system.SysRole;
import cn.lzj66.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: SysRoleController
 * Package: cn.lzj66.auth.controller
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 15:30
 */
@Api(tags = "角色管理")
@RestController //@Controller+@ResponseBody
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色列表")
    @GetMapping("findAll")
    public Result<List<SysRole>> findAll() {
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok(roleList);
    }
}
