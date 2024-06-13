package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.mapper.SysMenuMapper;
import cn.lzj66.auth.mapper.SysRoleMenuMapper;
import cn.lzj66.auth.service.SysMenuService;
import cn.lzj66.auth.utils.MenuHelper;
import cn.lzj66.common.execption.Lzj66ExceptionHandler;
import cn.lzj66.entity.system.SysMenu;
import cn.lzj66.entity.system.SysRoleMenu;
import cn.lzj66.vo.system.AssginMenuVo;
import cn.lzj66.vo.system.MetaVo;
import cn.lzj66.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

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
        //将菜单列表转换为前端框架需要的路由列表(即树结构)
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
        List<RouterVo> routerList = this.buildRouter(sysMenus);
        return routerList;
    }

    //将树结构构建为路由结构
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

    /**
     * 获取菜单
     *
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenus = sysMenuMapper.selectList(null);
        if (CollectionUtils.isEmpty(sysMenus)) {
            return null;
        }
        //将菜单列表构建为树结构返回
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenus);
        return sysMenuTreeList;
    }

    /**
     * 新增菜单
     *
     * @param sysMenu
     */
    @Override
    public void save(SysMenu sysMenu) {
        if (sysMenu == null) {
            return;
        }
        sysMenuMapper.insert(sysMenu);
    }

    /**
     * 修改菜单
     *
     * @param sysMenu
     */
    @Override
    public void updateById(SysMenu sysMenu) {
        if (sysMenu == null) {
            return;
        }
        sysMenuMapper.updateById(sysMenu);
    }

    /**
     * 删除菜单
     * 只有叶子结点才能删除
     */
    @Override
    public void removeMenuById(Long id) {
        //检查该id是否为孩子结点：若该菜单id不是任何结点的parentId，说明该结点是孩子结点，可以删除，否者不能删除
        List<SysMenu> sysMenus = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (!CollectionUtils.isEmpty(sysMenus)) {
            throw new Lzj66ExceptionHandler(201, "无法删除，请先删除子结点");
        }
        //该菜单是孩子菜单
        sysMenuMapper.deleteById(id);
    }

    /**
     * 给角色分配权限
     *
     * @param assginMenuVo
     */
    @Override
    @Transactional
    public void doAssion(AssginMenuVo assginMenuVo) {
        //删除角色原来的权限在给角色分配权限
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId()));
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if (StringUtils.isEmpty(menuId)) {
                continue;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu(assginMenuVo.getRoleId(), menuId);
            sysRoleMenu.setCreateTime(new Date());
            sysRoleMenu.setUpdateTime(new Date());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }

    }

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //角色拥有的权限id
        List<Long> sysMenuIds = sysRoleMenuMapper.findSysMenuByRoleId(roleId);
        //所有的权限
        List<SysMenu> allSysMenu = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        allSysMenu.forEach(e -> {
            if (sysMenuIds.contains(e.getId())) {
                e.setSelect(true);
            } else {
                e.setSelect(false);
            }
        });
        //将结果构建为树结构后，返回
        return MenuHelper.buildTree(allSysMenu);
    }
}
