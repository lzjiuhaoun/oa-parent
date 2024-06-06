package cn.lzj66.auth.mapper;

import cn.lzj66.entity.system.SysRole;
import cn.lzj66.entity.system.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SysUserRoleMapper
 * Package: cn.lzj66.auth.mapper
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/6 11:21
 */
@Repository
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    ArrayList<SysRole> findRoleIdByUserId(@Param("userId") Long userId);

    int doAssign(Long userId, List<Long> roleIdList);
}
