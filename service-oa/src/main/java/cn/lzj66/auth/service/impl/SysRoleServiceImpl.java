package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.mapper.SysRoleMapper;
import cn.lzj66.auth.mapper.SysUserRoleMapper;
import cn.lzj66.auth.service.SysRoleService;
import cn.lzj66.common.execption.Lzj66ExceptionHandler;
import cn.lzj66.entity.system.SysRole;
import cn.lzj66.entity.system.SysUserRole;
import cn.lzj66.vo.system.AssginRoleVo;
import cn.lzj66.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

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
        if (page == null && limit == null) {
            throw new Lzj66ExceptionHandler(201, "页码数和页大小不能为空");
        }
        IPage<SysRole> rolePage = new Page<>(page, limit);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(sysRoleQueryVo.getRoleName())) {
            queryWrapper.like(SysRole::getRoleName, sysRoleQueryVo.getRoleName());
        }
        return sysRoleMapper.selectPage(rolePage, queryWrapper);
    }

    //    @Override
//    public Map<String, Object> findRoleDataByUserId(Long userId) {
//        //查询所有的角色
//        List<SysRole> allRoleList = sysRoleMapper.selectList(null);
//        //查询已分配的角色数据id
//        List<Long> existRoleIdList = sysUserRoleMapper.findRoleIdByUserId(userId);
//        //sysRoles已分配的角色集合
//        ArrayList<SysRole> sysRoles = new ArrayList<>();
//        allRoleList.forEach(sysRole -> {
//            if (existRoleIdList.contains(sysRole.getId())) {
//                sysRoles.add(sysRole);
//            }
//        });
//        Map<String, Object> map = new HashMap<>();
//        map.put("assginRoleList", sysRoles);
//        map.put("allRolesList", allRoleList);
//        return map;
//    }
    @Override
    public Map<String, Object> findRoleDataByUserId(Long userId) {
        //查询所有的角色
        List<SysRole> allRoleList = sysRoleMapper.selectList(null);
        //查询已分配的角色数据
        ArrayList<SysRole> sysRoles = sysUserRoleMapper.findRoleIdByUserId(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("assginRoleList", sysRoles);
        map.put("allRolesList", allRoleList);
        return map;
    }

    /**
     * 给用户分配角色
     *
     * @param assginRoleVo
     */
    @Override
    @Transactional
    public void doAssign(AssginRoleVo assginRoleVo) {
        if (assginRoleVo.getRoleIdList() == null) {
            return;
        }
        if (assginRoleVo.getUserId() == null) {
            throw new Lzj66ExceptionHandler(201, "用户id不能为空");
        }
        //删除用户原来的角色【逻辑删除，MP会将条件中的数据状态设置为不可用】
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, assginRoleVo.getUserId()));
        //给用户分配角色
        int res = sysUserRoleMapper.doAssign(assginRoleVo.getUserId(),assginRoleVo.getRoleIdList());
        if (res == 0) {
            throw new Lzj66ExceptionHandler(201, "分配角色失败");
        }
    }
}
