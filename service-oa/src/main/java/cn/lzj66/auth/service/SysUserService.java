package cn.lzj66.auth.service;

import cn.lzj66.entity.system.SysUser;
import cn.lzj66.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * ClassName: SysUserService
 * Package: cn.lzj66.auth.service
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/4 10:42
 */
public interface SysUserService {
    IPage<SysUser> index(Long page, Long limit, SysUserQueryVo sysUserQueryVo);

    //根据用户名查询用户信息
    SysUser selectUserByUsername(String username);

    SysUser getUserById(Long userId);

    void updateById(SysUser sysUser);

    void save(SysUser sysUser);

    void removeById(Long id);
}
