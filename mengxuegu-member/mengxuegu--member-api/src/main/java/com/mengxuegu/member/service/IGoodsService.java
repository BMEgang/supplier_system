package com.mengxuegu.member.service;

import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.member.req.GoodsREQ;
import com.mengxuegu.member.req.MemberREQ;

import java.util.List;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
public interface IGoodsService extends IService<Goods> {
    List<Goods> selectBySupplierId(int supplierId);
    Result search(long page, long size, GoodsREQ req);

    Result findById(int id);
    Result update(int id, Goods goods);
}
