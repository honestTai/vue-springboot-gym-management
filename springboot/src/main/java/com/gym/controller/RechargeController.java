package com.gym.controller;

import com.gym.entity.Recharge;

import com.gym.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @RequestMapping(path="/getRechargeByMemberNo")
    @CrossOrigin
    public List<Recharge> getRechargeByMemberNo(int memberNo){
        return rechargeService.getRechargeByMemberNo(memberNo);
    }

    @RequestMapping(path = "/addRechargeByMemberNo")
    @CrossOrigin
    public Map<String,Object> addRechargeByMemberNo(Recharge recharge) {
        return rechargeService.addRechargeByMemberNo(recharge);
    }





}
