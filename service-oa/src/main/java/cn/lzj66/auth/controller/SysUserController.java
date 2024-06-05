package cn.lzj66.auth.controller;

import cn.lzj66.auth.service.SysUserService;
import cn.lzj66.entity.system.SysUser;
import cn.lzj66.result.Result;
import cn.lzj66.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: SysUserController
 * Package: cn.lzj66.auth.controller
 * Description: 用户管理
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/4 10:41
 */
@Api("用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //用户条件分页查询
    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> userIPage = sysUserService.index(page, limit, sysUserQueryVo);
        return Result.ok(userIPage);
    }
}
