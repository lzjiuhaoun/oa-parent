package cn.lzj66.mybatisPlus;

import cn.lzj66.auth.mapper.SysRoleMapper;
import cn.lzj66.entity.system.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: SysRoleMapperTest
 * Package: cn.lzj66.mybatisPlus
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 10:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void testSelectList(){
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
//        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
//        sysRoles.forEach(System.out::println);
//        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.like(SysRole::getRoleName,"管理员");
//        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
//        sysRoles.forEach(System.out::println);
        SysRole sysRole = sysRoleMapper.selectByRoleId(1L);
        System.out.println(sysRole.toString());
    }
}
