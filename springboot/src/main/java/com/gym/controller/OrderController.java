package com.gym.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gym.entity.*;
import com.gym.mapper.CartMapper;
import com.gym.mapper.EquipmentMapper;
import com.gym.mapper.MemberMapper;
import com.gym.mapper.OrderMapper;
import com.gym.service.CartService;
import com.gym.service.MemberService;
import com.gym.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 订单，购物车
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    EquipmentMapper equipmentMapper;

    @Autowired
    MemberMapper memberMapper;

    //生成订单
    @RequestMapping(path = "/addOrder")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addOrder(Orders order) {
        Map<String, Object> resultMap = new HashMap<>();
        double memberChange = memberMapper.getMemberChange(order.getUserId());
        double totalBuy = order.getPrice();
        if (memberChange > totalBuy) {
            orderMapper.addOrder(order);
            orderMapper.updateOrderNumEq(order.getEqId(), order.getNum());
            cartMapper.deleteCart(order.getCartId());
            resultMap.put("code", 200);
            resultMap.put("message", "购买成功");
        } else {
            resultMap.put("code", 400);
            resultMap.put("message", "购买失败,余额不足请充值余额后再试");
        }
        return resultMap;

    }

    //查看订单
    @RequestMapping(path = "/getOrder")
    public PageInfo<Orders> getOrder(int page, int size, Integer userId) {
        PageHelper.startPage(page, size);
        List<Orders> list = new ArrayList<>();
        if (userId.equals(-1)) {
            list = orderMapper.getOrders();
        } else {
            list = orderMapper.getOrdersByUserId(userId);
        }
        list.forEach(x -> {
            x.setEquipment(equipmentMapper.getEquipmentByEqId(x.getEqId()));
        });
        return PageInfo.of(list);
    }

    @RequestMapping(path = "/getOrderSearch")
    public PageInfo<Orders> getOrder(int page, int size, String keyWord) {
        PageHelper.startPage(page, size);
        List<Orders> list = new ArrayList<>();

        list = orderMapper.searchOrdersByKeyword(keyWord);

        list.forEach(x -> {
            x.setEquipment(equipmentMapper.getEquipmentByEqId(x.getEqId()));
        });
        return PageInfo.of(list);
    }

    //编辑订单
    @RequestMapping(path = "/cancelOrders")
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrders(Orders orders) {
        orderMapper.updateOrderStatus(orders.getId());
        Equipment equipment = equipmentMapper.getEquipmentByEqId(orders.getEqId());
        equipment.setNum(equipment.getNum() + orders.getNum());
        equipmentMapper.updateEquipment(equipment);
        memberMapper.updateMemberChangeByMemberNoAdd(orders.getUserId(), orders.getPrice());
    }

    @RequestMapping(path = "/sendOrders")
    @Transactional(rollbackFor = Exception.class)
    public void sendOrders(Orders orders) {
        orderMapper.updateOrderStatus2(orders.getId());
    }

    @RequestMapping(path = "/confirmOrders")
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrders(Orders orders) {
        orderMapper.updateOrderStatus3(orders.getId());
    }



    //增加购物车
    @RequestMapping(path = "/addCart")
    public void addCart(Cart cart) {
        Cart cartDb = cartMapper.cartByUserId(cart.getUserId(), cart.getEqId());
        if (Objects.nonNull(cartDb)) {
            cartDb.setEqNum(cartDb.getEqNum() + cart.getEqNum());
            cartMapper.updateById(cartDb);
        } else {
            cartMapper.addCart(cart);
        }
    }

    //删除购物车
    @RequestMapping(path = "/deleteCartApi")
    public void deleteCartApi(int id) {
        cartMapper.deleteCart(id);
    }

    //列表查购物车
    @RequestMapping(path = "/getCart")
    public PageInfo<CartDto> getCart(int page, int size, Integer userId) {
        PageHelper.startPage(page, size);
        List<CartDto> list = cartMapper.getCartDto(userId);
        list.forEach(x -> {
            x.setEquipment(equipmentMapper.getEquipmentByEqId(x.getEqId()));
        });
        return PageInfo.of(list);
    }

    //编辑购物车
    @RequestMapping(path = "/updateCart")
    public void updateCart(Cart cart) {
        cartMapper.updateById(cart);
    }
}
