package cn.lzj66.auth.utils;

import cn.lzj66.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MenuHelper
 * Package: cn.lzj66.auth.utils
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/5 11:26
 */
public class MenuHelper {
    //使用递归方法构建书树结构菜单
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //创建list集合，用于最终数据
        List<SysMenu> trees = new ArrayList<>();
        //把所有菜单数据进行遍历
        for(SysMenu sysMenu:sysMenuList) {
            //递归入口进入
            //parentId=0是入口
            if(sysMenu.getParentId().longValue()==0) {
                trees.add(getChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找菜单的孩子结点
     * @param sysMenu
     * @param sysMenuList
     * @return
     */
    public static SysMenu getChildren(SysMenu sysMenu,
                                      List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        //遍历所有菜单数据，判断 id 和 parentId对应关系
        for(SysMenu it: sysMenuList) {
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(getChildren(it,sysMenuList));
            }
        }
        return sysMenu;
    }
}
