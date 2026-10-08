package com.gym.entity;

import lombok.Data;

@Data
public class CartDto{
    private Integer id;

    private Integer userId;

    private Integer eqId;

    private Integer eqNum;

    private Equipment equipment;
}
