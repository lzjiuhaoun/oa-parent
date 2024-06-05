package cn.lzj66.auth.service.impl;

import cn.lzj66.auth.mapper.SysUserMapper;
import cn.lzj66.auth.service.SysUserService;
import cn.lzj66.entity.system.SysUser;
import cn.lzj66.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: SysUserServiceImpl
 * Package: cn.lzj66.auth.service.impl
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/4 10:42
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 用户条件、分页查询
     * 单表查询可用MP
     *
     * @param page
     * @param limit
     * @param sysUserQueryVo
     * @return
     */
    @Override
    public IPage<SysUser> index(Long page, Long limit, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> sysUserPage = new Page<>(page, limit);
        String userName = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(userName)) {
            wrapper.like(SysUser::getName, userName);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) { //ge >=
            wrapper.ge(SysUser::getCreateTime, createTimeBegin);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) { //le <=
            wrapper.le(SysUser::getCreateTime, createTimeEnd);
        }
        return sysUserMapper.selectPage(sysUserPage, wrapper);
    }

    //根据用户名查询用户信息
    @Override
    public SysUser selectUserByUsername(String username) {
        List<SysUser> sysUsers = sysUserMapper.selectList(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (sysUsers != null && sysUsers.size() > 0) {
            return sysUsers.get(0);
        }
        return null;
    }

    @Override
    public SysUser getUserById(Long userId) {
        return sysUserMapper.selectById(userId);
    }
}
