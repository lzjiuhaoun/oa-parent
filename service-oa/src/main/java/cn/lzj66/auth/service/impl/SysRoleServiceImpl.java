package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.service.SysRoleService;
import cn.lzj66.common.execption.Lzj66ExceptionHandler;
import cn.lzj66.entity.system.SysRole;
import cn.lzj66.auth.mapper.SysRoleMapper;
import cn.lzj66.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public IPage<SysRole> pageQueryRole(Long page, Long limit, SysRoleQueryVo sysRoleQueryVo) {
        if (page==null && limit==null) {
            throw new Lzj66ExceptionHandler(201,"页码数和页大小不能为空");
        }
        IPage<SysRole> rolePage = new Page<>(page, limit);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(sysRoleQueryVo.getRoleName())) {
            queryWrapper.like(SysRole::getRoleName, sysRoleQueryVo.getRoleName());
        }
        return sysRoleMapper.selectPage(rolePage, queryWrapper);
    }
}
