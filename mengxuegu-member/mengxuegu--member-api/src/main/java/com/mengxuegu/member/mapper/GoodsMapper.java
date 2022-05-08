package com.mengxuegu.member.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mengxuegu.member.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxuegu.member.req.GoodsREQ;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 * @since 2022-05-07
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<Goods> searchPage(IPage<Goods> page, @Param("req")GoodsREQ req);
}
