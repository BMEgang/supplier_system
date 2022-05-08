package com.mengxuegu.member.controller;


import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Staff;
import com.mengxuegu.member.entity.Supplier;
import com.mengxuegu.member.req.StaffREQ;
import com.mengxuegu.member.req.SupplierREQ;
import com.mengxuegu.member.service.IStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工信息表 前端控制器
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStaffService staffService;

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) StaffREQ req){
        logger.info("分页查询员工列表：page={}, size={}", page, size);
        return staffService.search(page, size, req);
    }

    @PostMapping
    public Result add(@RequestBody Staff staff){
        return staffService.add(staff);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id){
        boolean b = staffService.removeById(id);
        if (b){
            return Result.ok();
        }

        return Result.error("删除员工信息失败");
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id){
        Staff staff = staffService.getById(id);
        return Result.ok(staff);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Staff staff){
        return staffService.update(id,staff);
    }
}
