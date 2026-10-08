package com.gym.controller;

import com.github.pagehelper.PageInfo;
import com.gym.entity.Common;
import com.gym.entity.Register;
import com.gym.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping(path= "/getAllRegister")
    @CrossOrigin
    public PageInfo<Register> getAllRegister(int page, int size){
        return registerService.getAllRegister(page,size);
    }

    @RequestMapping(path= "/getRegisterByMemberNo")
    @CrossOrigin
    public PageInfo<Register> getRegisterByMemberNo(int memberNo,int page, int size){
        return registerService.getRegisterByMemberNo(memberNo,page,size);
    }

    @RequestMapping(path= "/addRegister")
    @CrossOrigin
    public Map<String,Object> addRegister(Register register){
        return registerService.addRegister(register);
    }

    @RequestMapping(path ="/updateRegister")
    @CrossOrigin
    public Map<String,Object> updateRegister(Register register){
        return registerService.updateRegister(register);
    }

    @RequestMapping(path ="/deleteRegister")
    @CrossOrigin
    public Map<String,Object> deleteRegister(int registerNo){
        return registerService.deleteRegister(registerNo);
    }

    @RequestMapping(path = "/totalRegister")
    @CrossOrigin
    public Common totalRegister() {
        return registerService.totalRegister();
    }

    @RequestMapping(path = "/getByKeywordRegister")
    @CrossOrigin
    public PageInfo<Register> getByKeywordRegister(String keyWord, int page, int size){
        return registerService.getByKeywordRegister(keyWord,page,size);
    }

    @RequestMapping(path = "/totalRegisterFuzzy")
    @CrossOrigin
    public Common totalRegisterFuzzy(String keyWord) {
        return registerService.totalRegisterFuzzy(keyWord);
    }

//    //柱状图--课程种类接口
//    @RequestMapping(path ="getAllCourseDuration" )
//    public List<Register> getAllCourseDuration(int courseDuration){
//        return registerService.getAllCourseDuration(courseDuration);
//    }

}

