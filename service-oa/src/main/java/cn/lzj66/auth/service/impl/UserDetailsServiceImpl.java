package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.service.SysUserService;
import cn.lzj66.entity.system.SysUser;
import cn.lzj66.security.custom.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * ClassName: UserDetailsServiceImpl
 * Package: cn.lzj66.auth.service.impl
 * Description: 自定义 spring security 用户信息查询接口
 * 根据用户名获取用户对象（获取不到直接抛异常）
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/13 11:20
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    /**
     * 根据用户名获取用户对象（获取不到直接抛异常）
     * implements UserDetailsService 是要重写spring security获取用户对象的方法，使用我们自己的业务方法。
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectUserByUsername(userName);
        if (sysUser==null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        if (sysUser.getStatus().intValue()==0) {
            throw new UsernameNotFoundException("账户已停用！");
        }
        //将用户和用户权限返回给spring security后续用户认证、授权使用
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
