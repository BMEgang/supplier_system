package com.mengxuegu.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.member.base.Page;
import com.mengxuegu.member.base.Result;
import com.mengxuegu.member.entity.Member;
import com.mengxuegu.member.mapper.MemberMapper;
import com.mengxuegu.member.req.MemberREQ;
import com.mengxuegu.member.service.IMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper,Member> implements IMemberService {

    @Override
    public Result search(long page,long size, MemberREQ req) {
        QueryWrapper<Member> query = new QueryWrapper<>();
        if (req != null)
        {
            if (StringUtils.isNotBlank(req.getName())){
                query.like("name", req.getName());
            }
            if (StringUtils.isNotBlank(req.getCardNum())){
                query.like("card_num", req.getCardNum());
            }
            if (StringUtils.isNotBlank(req.getPayType())){
                query.like("pay_type", req.getPayType());
            }
            if (req.getBirthday() != null){
                query.like("birthday", req.getBirthday());
            }
        }

        IPage<Member> p = new Page<>(page,size);
        IPage<Member> data = baseMapper.selectPage(p,query);
        return Result.ok(data);
    }

    @Override
    public Result update(int id, Member member) {
        if (member.getId() == null){
            member.setId(id);
        }

        int size = baseMapper.updateById(member);
        if (size < 1){
            return Result.error("修改会员信息失败");
        }

        return Result.ok();
    }

}
