package cn.lzj66.auth.controller;

import cn.lzj66.auth.service.SysUserService;
import cn.lzj66.entity.system.SysUser;
import cn.lzj66.result.Result;
import cn.lzj66.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> userIPage = sysUserService.index(page, limit, sysUserQueryVo);
        return Result.ok(userIPage);
    }

    @ApiOperation("获取用户")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        return Result.ok(sysUserService.getUserById(id));
    }

    @ApiOperation("保存用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.ok();
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    @Transactional
    public Result updateById(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return Result.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysUserService.removeById(id);
        return Result.ok();
    }
}
