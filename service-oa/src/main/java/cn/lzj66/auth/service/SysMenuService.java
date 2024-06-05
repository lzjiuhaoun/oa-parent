package cn.lzj66.auth.service;

import cn.lzj66.vo.system.RouterVo;

import java.util.List;

/**
 * ClassName: SysMenuService
 * Package: cn.lzj66.auth.service
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/5 11:02
 */
public interface SysMenuService {

    //根据用户id获取用户可操作的菜单列表
    List<RouterVo> findUserMenuListByUserId(Long userId);

    //根据用户id获取用户可操作的按钮列表
    List<String> findUserPermsByUserId(Long userId);
}
