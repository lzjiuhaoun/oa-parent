package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.service.SysRoleService;
import cn.lzj66.entity.system.SysRole;
import cn.lzj66.mybatisPlus.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: SysRoleServiceImpl
 * Package: cn.lzj66.auth.service.impl
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 15:55
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public List<SysRole> list() {
        return sysRoleMapper.selectList(null);
    }
}
