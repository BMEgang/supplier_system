package com.mengxuegu.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengxuegu.member.base.Page;
import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Goods;
import com.mengxuegu.member.entity.Staff;
import com.mengxuegu.member.mapper.StaffMapper;
import com.mengxuegu.member.req.PasswordREQ;
import com.mengxuegu.member.req.StaffREQ;
import com.mengxuegu.member.req.SupplierREQ;
import com.mengxuegu.member.service.IStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

    @Override
    public Result search(long page, long size, StaffREQ req) {
        QueryWrapper<Staff> query = new QueryWrapper<>();
        if (req != null){
            if (StringUtils.isNotBlank(req.getName())){
                query.like("name",req.getName());
            }
            if (StringUtils.isNotBlank(req.getUsername())){
                query.like("username",req.getUsername());
            }
        }

        Page<Staff> data = baseMapper.selectPage(new Page<Staff>(page, size), query);

        return Result.ok(data);
    }

    @Override
    public Result add(Staff staff) {
        if (staff == null || StringUtils.isEmpty(staff.getUsername())){
            return Result.error("username cannot be null");
        }

        Staff s = getByUsername(staff.getUsername());
        if (s != null){
            return Result.error("用户名存在");
        }

        String password = new BCryptPasswordEncoder().encode(staff.getPassword());
        staff.setPassword(password);

        boolean b = this.save(staff);
        if (b){
            return Result.ok();
        }

        return Result.error("新增失败");
    }

    @Override
    public Result update(int id, Staff staff) {
        if (staff.getId() == null){
            staff.setId(id);
        }

        int size = baseMapper.updateById(staff);
        if (size < 1){
            return Result.error("update fail");
        }

        return Result.ok();
    }

    @Override
    public Result checkPassword(PasswordREQ req) {
        if (req == null || StringUtils.isEmpty(req.getPassword())){
            return Result.error("原密码不能为空");
        }

        Staff staff = baseMapper.selectById(req.getUserId());
        if (!new BCryptPasswordEncoder().matches(req.getPassword(),staff.getPassword())){
            return Result.error("原密码错误");
        }

        return Result.ok();
    }

    @Override
    public Result updatePassword(PasswordREQ req) {
        if (req == null || StringUtils.isEmpty(req.getPassword())){
            return Result.error("新密码不能为空");
        }

        Staff staff = baseMapper.selectById(req.getUserId());
        staff.setPassword(new BCryptPasswordEncoder().encode(req.getPassword()));
        baseMapper.updateById(staff);
        return Result.ok();
    }

    private Staff getByUsername(String username) {
        QueryWrapper<Staff> query = new QueryWrapper<>();
        query.eq("useraname",username);
        return baseMapper.selectOne(query);
    }


}
