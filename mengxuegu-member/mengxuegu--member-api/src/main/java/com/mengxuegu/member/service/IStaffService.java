package com.mengxuegu.member.service;

import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.member.req.PasswordREQ;
import com.mengxuegu.member.req.StaffREQ;
import com.mengxuegu.member.req.SupplierREQ;

/**
 * <p>
 * 员工信息表 服务类
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
public interface IStaffService extends IService<Staff> {
    Result search(long page, long size, StaffREQ req);

    Result add(Staff staff);

    Result update(int id, Staff staff);

    Result checkPassword(PasswordREQ req);

    Result updatePassword(PasswordREQ req);
}
