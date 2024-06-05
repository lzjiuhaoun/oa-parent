package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.utils.MenuHelper;
import cn.lzj66.entity.system.SysMenu;
import cn.lzj66.auth.mapper.SysMenuMapper;
import cn.lzj66.auth.service.SysMenuService;
import cn.lzj66.vo.system.MetaVo;
import cn.lzj66.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: SysMenuServiceImpl
 * Package: cn.lzj66.auth.service.impl
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/5 11:02
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    //根据用户id获取用户可操作的菜单列表
    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {
        List<SysMenu> sysMenuList = null;
        //如果是管理员，查询所有菜单列表
        if (userId.longValue() == 1) {
            sysMenuList = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1).orderByAsc(SysMenu::getSortValue));
        } else {
            //不是管理员，则根据userId查询可操作的菜单列表
            sysMenuList = sysMenuMapper.findMenuListByUserId(userId);
        }
        //将菜单列表转换为前端框架需要的路由列表
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
        List<RouterVo> routerList = this.buildRouter(sysMenus);
        return routerList;
    }

    //构建成框架要求的路由结构
    private List<RouterVo> buildRouter(List<SysMenu> menus) {
        //创建list集合，存储最终数据
        List<RouterVo> routers = new ArrayList<>();
        //menus遍历
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            //下一层数据部分
            List<SysMenu> children = menu.getChildren();
            if (menu.getType().intValue() == 1) {
                //加载出来下面隐藏路由
                List<SysMenu> hiddenMenuList = children.stream()
                        .filter(item -> !StringUtils.isEmpty(item.getComponent()))
                        .collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    //true 隐藏路由
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));

                    routers.add(hiddenRouter);
                }

            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if (children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    //递归
                    router.setChildren(buildRouter(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if (menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }

    /**
     * 根据用户id获取用户可操作的按钮列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> findUserPermsByUserId(Long userId) {
        List<SysMenu> sysMenus = null;
        if (userId.longValue() == 1) { //管理员 userId=1,查询所有按钮列表
            sysMenus = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        } else { //非管理员，根据userId查询按钮列表
            sysMenus = sysMenuMapper.findMenuListByUserId(userId);
        }
        //筛选出sysMenus集合中item.getType() == 2 元素，并将item.getPerms()列单独取出作为List集合返回.
        List<String> permsList = sysMenus.stream().filter(item -> item.getType() == 2).map(item -> item.getPerms()).collect(Collectors.toList());
        return permsList;
    }
}
