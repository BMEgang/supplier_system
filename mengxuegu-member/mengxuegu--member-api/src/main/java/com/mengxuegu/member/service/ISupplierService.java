package com.mengxuegu.member.service;

import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.member.req.MemberREQ;
import com.mengxuegu.member.req.SupplierREQ;

/**
 * <p>
 * 供应商信息表 服务类
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
public interface ISupplierService extends IService<Supplier> {
    Result search(long page, long size, SupplierREQ req);
    Result deleteById(int id);

    Result update(int id, Supplier supplier);
}
