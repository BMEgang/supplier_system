package com.mengxuegu.member.controller;

import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.req.PasswordREQ;
import com.mengxuegu.member.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private IStaffService staffService;

    @PostMapping("/pwd")
    public Result checkPwd(@RequestBody PasswordREQ req){
        return staffService.checkPassword(req);
    }

    @PutMapping("/pwd")
    public Result updatePwd(@RequestBody PasswordREQ req){
        return staffService.updatePassword(req);
    }
}
