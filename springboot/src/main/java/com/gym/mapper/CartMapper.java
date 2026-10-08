package com.gym.mapper;

import com.gym.entity.Cart;
import com.gym.entity.CartDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("insert into cart(user_id,eq_id,eq_num) values(#{userId},#{eqId},#{eqNum})")
    void addCart(Cart cart);

    @Select("select id,user_id as userId,eq_id as egId, eq_num as eqNum from cart where user_id=#{userId} and eq_id = #{egId} ")
    Cart cartByUserId(Integer userId, Integer egId);

    @Update("update cart set eq_num = #{eqNum} where id = #{id}")
    void updateById(Cart cartDb);

    @Select("select id,user_id as userId,eq_id as eqId, eq_num as eqNum from cart where user_id = #{userId}")
    List<CartDto> getCartDto(Integer userId);

    @Delete("delete from cart where id = #{id}")
    void deleteCart(Integer id);
}
