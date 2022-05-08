package com.mengxuegu.member.controller;


import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Member;
import com.mengxuegu.member.entity.Supplier;
import com.mengxuegu.member.req.SupplierREQ;
import com.mengxuegu.member.service.ISupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 供应商信息表 前端控制器
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISupplierService supplierService;

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false)SupplierREQ req){
        logger.info("分页查询供应商列表：page={}, size={}", page, size);
        return supplierService.search(page, size, req);
    }

    @PostMapping
    public Result add(@RequestBody Supplier supplier){
        boolean b = supplierService.save(supplier);
        if (b){
            return Result.ok();
        }

        return Result.error("add supplier fail");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id){
        return supplierService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id){
        Supplier supplier = supplierService.getById(id);
        return Result.ok(supplier);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Supplier supplier){
        return supplierService.update(id,supplier);
    }

}
