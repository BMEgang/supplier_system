package com.mengxuegu.member.controller;


import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Goods;
import com.mengxuegu.member.entity.Supplier;
import com.mengxuegu.member.req.GoodsREQ;
import com.mengxuegu.member.req.SupplierREQ;
import com.mengxuegu.member.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品信息表 前端控制器
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) GoodsREQ req){

        return goodsService.search(page, size, req);
    }

    @PostMapping
    public Result add(@RequestBody Goods goods){
        boolean b = goodsService.save(goods);
        if (b){
            return Result.ok();
        }

        return Result.error("add goods fail");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id){
        boolean b = goodsService.removeById(id);
        if (b){
            return Result.ok();
        }
        return Result.error("delete goods fail");
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id){
        return goodsService.findById(id);
    }
//
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Goods goods){
        return goodsService.update(id,goods);
    }
}
