package com.mengxuegu.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mengxuegu.member.base.Page;
import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Goods;
import com.mengxuegu.member.entity.Supplier;
import com.mengxuegu.member.mapper.SupplierMapper;
import com.mengxuegu.member.req.SupplierREQ;
import com.mengxuegu.member.service.IGoodsService;
import com.mengxuegu.member.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 供应商信息表 服务实现类
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {

    @Override
    public Result search(long page, long size, SupplierREQ req) {
        QueryWrapper<Supplier> query = new QueryWrapper<>();
        if (req != null){
            if (StringUtils.isNotBlank(req.getName())){
                query.like("name",req.getName());
            }
            if (StringUtils.isNotBlank(req.getLinkman())){
                query.like("Linkman",req.getLinkman());
            }
            if (StringUtils.isNotBlank(req.getMobile())){
                query.like("Mobile",req.getMobile());
            }
        }

        IPage<Supplier> data = baseMapper.selectPage(new Page<Supplier>(page,size),query);

        return Result.ok(data);
    }

    @Autowired
    private IGoodsService goodsService;

    @Override
    public Result deleteById(int id) {
        List<Goods> goodsList = goodsService.selectBySupplierId(id);
        if (CollectionUtils.isNotEmpty(goodsList)){
            return Result.error("this supplier has been used, cannot be deleted");
        }

        int i = baseMapper.deleteById(id);
        if (i < 1){
            return Result.error("delete fail");
        }

        return Result.ok();
    }

    @Override
    public Result update(int id, Supplier supplier) {
        if (supplier.getId() == null){
            supplier.setId(id);
        }

        int i = baseMapper.updateById(supplier);

        if (i < 1){
            return Result.error("modeifier supplier fail");
        }
        return Result.ok();
    }


}
