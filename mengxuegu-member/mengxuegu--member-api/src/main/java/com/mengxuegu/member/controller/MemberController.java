package com.mengxuegu.member.controller;

import com.mengxuegu.member.entity.Member;
import com.mengxuegu.member.req.MemberREQ;
import com.mengxuegu.member.service.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mengxuegu.member.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMemberService memberService;

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false)MemberREQ req){
        logger.info("分页查询会员列表：page={}, size={}", page, size);
        return memberService.search(page,size,req);
    }

    @PostMapping
    public Result add(@RequestBody Member member){
        boolean b = memberService.save(member);
        if (b){
            return Result.ok();
        }

        return Result.error("add VIP member fail");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id){
        boolean b = memberService.removeById(id);
        if (b){
            return Result.ok();
        }

        return Result.error("delete VIP member fail");
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id){
        Member member = memberService.getById(id);
        return Result.ok(member);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Member member){
        return memberService.update(id,member);
    }
}
