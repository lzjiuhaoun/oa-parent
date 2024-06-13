package cn.lzj66.security.custom;

import cn.lzj66.entity.system.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * ClassName: CustomUser
 * Package: cn.lzj66.security.custom
 * Description:spring security自定义用户类
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/13 11:15
 */
public class CustomUser extends User {

    /**
     * 我们自己的用户实体对象，要调取用户信息时直接获取这个实体对象。（这里我就不写get/set方法了）
     */
    private SysUser sysUser;

    //包含用户对象和用户权限的构造方法
    public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

}