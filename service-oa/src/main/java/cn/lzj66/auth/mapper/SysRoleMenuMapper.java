package cn.lzj66.auth.mapper;

import cn.lzj66.entity.system.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: SysRoleMenuMapper
 * Package: cn.lzj66.auth.mapper
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/12 15:16
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    List<Long> findSysMenuByRoleId(@Param("roleId") Long roleId);
}
