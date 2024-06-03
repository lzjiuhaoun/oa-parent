package cn.lzj66.auth.mapper;

import cn.lzj66.entity.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: SysRoleMapper
 * Package: cn.lzj66.mybatisPlus
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 10:17
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    SysRole selectByRoleId(Long id);
}
