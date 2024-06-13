package cn.lzj66.auth.controller;

import cn.lzj66.auth.service.SysMenuService;
import cn.lzj66.auth.service.SysUserService;
import cn.lzj66.common.execption.Lzj66ExceptionHandler;
import cn.lzj66.dto.UserInfoDto;
import cn.lzj66.entity.system.SysUser;
import cn.lzj66.jwt.JwtHelper;
import cn.lzj66.result.Result;
import cn.lzj66.utils.MD5;
import cn.lzj66.vo.system.LoginVo;
import cn.lzj66.vo.system.RouterVo;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: IndexController
 * Package: cn.lzj66.system.controller
 * Description:后台登录管理
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/4 11:07
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        if (StringUtils.isEmpty(loginVo.getUsername())) {
            throw new Lzj66ExceptionHandler(201, "用户名不存在");
        }
        SysUser sysUserByName = sysUserService.selectUserByUsername(loginVo.getUsername());
        if (sysUserByName == null) {
            throw new Lzj66ExceptionHandler(201, "用户不存在");
        }
        if (!sysUserByName.getPassword().equals(MD5.encrypt(loginVo.getPassword()))) {
            throw new Lzj66ExceptionHandler(201, "用户名或密码错误");
        }
        //判断用户是否禁用  1:可用 0：禁用
        if (sysUserByName.getStatus().intValue() == 0) {
            throw new Lzj66ExceptionHandler(201, "用户被禁用");
        }
        //根据userid和username创建token
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtHelper.createToken(sysUserByName.getId(), sysUserByName.getUsername()));
        return Result.ok(map);
    }

    //根据token获取用户信息
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        Long userId = JwtHelper.getUserId(request.getHeader("token"));
        SysUser sysUser = sysUserService.getUserById(userId);
        if (sysUser == null) {
            throw new Lzj66ExceptionHandler(201, "用户不存在");
        }
        //根据用户id获取用户可操作的菜单列表
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(userId);
        //根据用户id获取用户可操作的按钮列表
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setRoles("[admin]");
        userInfoDto.setName(sysUser.getUsername());
        userInfoDto.setAvatar(sysUser.getHeadUrl());
        userInfoDto.setRouters(routerList);
        userInfoDto.setButtons(permsList);
        return Result.ok(userInfoDto);
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }
}
