package cn.lzj66.auth.mapper;

import cn.lzj66.entity.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: SysMenuMapper
 * Package: cn.lzj66.auth.mapper
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/5 11:05
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //多表关联查询：用户角色关系表 、 角色菜单关系表、 菜单表
    List<SysMenu> findMenuListByUserId(Long userId);
}
