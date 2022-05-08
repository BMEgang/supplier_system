package com.mengxuegu.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Goods;
import com.mengxuegu.member.entity.Supplier;
import com.mengxuegu.member.mapper.GoodsMapper;
import com.mengxuegu.member.req.GoodsREQ;
import com.mengxuegu.member.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.member.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Override
    public List<Goods> selectBySupplierId(int supplierId) {
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.eq("supplier_id",supplierId);
        return baseMapper.selectList(query);
    }

    @Override
    public Result search(long page, long size, GoodsREQ req) {
        if (req == null){
            req = new GoodsREQ();
        }
        IPage<Goods> data = baseMapper.searchPage(new Page<Goods>(page, size), req);

        return Result.ok(data);
    }

    @Autowired
    private ISupplierService supplierService;

    @Override
    public Result findById(int id) {
        Goods goods = baseMapper.selectById(id);

        if (goods != null && goods.getSupplierId() != null){
            Supplier supplier = supplierService.getById(goods.getSupplierId());
            if (supplier != null){
                goods.setSupplierName(supplier.getName());
            }
        }
        return Result.ok(goods);
    }

    @Override
    public Result update(int id, Goods goods) {
        if (goods.getId() == null){
            goods.setId(id);
        }

        int i = baseMapper.updateById(goods);

        if (i < 1){
            return Result.error("modify fail");
        }
        return Result.ok();
    }
}
