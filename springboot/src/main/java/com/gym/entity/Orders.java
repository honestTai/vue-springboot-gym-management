package com.gym.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {

    private Integer id;
    private Integer userId;
    private Integer eqId;
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private Date time;
    private String username;
    private String phone;
    private String address;
    private Integer num;
    //0待发货，1已退款，2已发货，3已收货
    private String status;
    private Double price;
    private Integer cartId;

    private Equipment equipment;

}
