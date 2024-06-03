package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.service.SysRoleService;
import cn.lzj66.entity.system.SysRole;
import cn.lzj66.auth.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    //条件分页查询
    @Override
    public IPage<SysRole> page(Page<SysRole> pageParam, LambdaQueryWrapper<SysRole> wrapper) {
        return sysRoleMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public SysRole getById(Long id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public void save(SysRole role) {
        sysRoleMapper.insert(role);
    }

    @Override
    public void updateById(SysRole role) {
        sysRoleMapper.updateById(role);
    }

    @Override
    public void removeById(Long id) {
        sysRoleMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<Long> idList) {
        sysRoleMapper.deleteBatchIds(idList);
    }
}
