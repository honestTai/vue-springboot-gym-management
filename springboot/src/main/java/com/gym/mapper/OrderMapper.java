package com.gym.mapper;

import com.gym.entity.Orders;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("insert into orders(user_id,eq_id,time,username,phone,address,num,price,status) values(#{userId},#{eqId},now(),#{username},#{phone},#{address},#{num},#{price},0)")
    void addOrder(Orders order);

    @Update("update equipment set num = num - #{num} where equipmentNo = #{eqId}")
    void updateOrderNumEq(Integer eqId, Integer num);

    @Select("SELECT id, user_id AS userId, eq_id AS eqId, time, username, phone, address, num, status, price FROM orders WHERE user_id = #{userId}")
    List<Orders> getOrdersByUserId(@Param("userId") Integer userId);

    @Select("SELECT id, user_id AS userId, eq_id AS eqId, time, username, phone, address, num, status, price FROM orders")
    List<Orders> getOrders();

    @Update("update orders set status = 1 where id = #{id}")
    void updateOrderStatus(Integer id);

    @Update("update orders set status = 2 where id = #{id}")
    void updateOrderStatus2(Integer id);
    @Select("SELECT id, user_id AS userId, eq_id AS eqId, time, username, phone, address, num, status, price FROM orders WHERE username LIKE CONCAT('%', #{keyWord}, '%') OR phone LIKE CONCAT('%', #{keyWord}, '%') OR address LIKE CONCAT('%', #{keyWord}, '%')")
    List<Orders> searchOrdersByKeyword(@Param("keyWord") String keyWord);
    @Update("update orders set status = 3 where id = #{id}")
    void updateOrderStatus3(Integer id);
}
