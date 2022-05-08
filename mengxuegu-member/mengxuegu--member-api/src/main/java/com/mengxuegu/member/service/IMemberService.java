package com.mengxuegu.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Member;
import com.mengxuegu.member.req.MemberREQ;

public interface IMemberService extends IService<Member> {
    Result search(long page,long size, MemberREQ req);
    Result update(int id, Member member);
}
