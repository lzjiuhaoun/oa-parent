package cn.lzj66.auth.service;

import cn.lzj66.entity.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * ClassName: SysRoleService
 * Package: cn.lzj66.auth.service
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 15:34
 */
public interface SysRoleService {
    List<SysRole> list();

    //条件分页查询
    IPage<SysRole> page(Page<SysRole> pageParam, LambdaQueryWrapper<SysRole> wrapper);

    SysRole getById(Long id);

    void save(SysRole role);

    void updateById(SysRole role);

    void removeById(Long id);

    void removeByIds(List<Long> idList);
}
