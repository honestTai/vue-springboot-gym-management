package com.gym.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Equipment {
    private Integer equipmentNo;
    private String equipmentName;
    private String equipmentLocation;
    private String equipmentMessage;
    private BigDecimal price;
    private Integer num;
    private String pic;
    private String info;
    private Integer tmpNum;
}
