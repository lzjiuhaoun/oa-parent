package cn.lzj66.auth.controller;

import cn.lzj66.auth.service.SysMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
