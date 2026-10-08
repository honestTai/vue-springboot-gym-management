package com.gym.controller;

import com.github.pagehelper.PageInfo;
import com.gym.entity.Common;

import com.gym.entity.Equipment;
import com.gym.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(path= "/getAllEquipment")
    @CrossOrigin
    public PageInfo<Equipment> getAllEquipment(int page, int size){
        return equipmentService.getAllEquipment(page,size);
    }

    @RequestMapping(path= "/addEquipment")
    @CrossOrigin
    public Map<String,Object> addEquipment(Equipment equipment){
        return equipmentService.addEquipment(equipment);
    }

    @RequestMapping(path ="/updateEquipment")
    @CrossOrigin
    public Map<String,Object> updateEquipment(Equipment equipment){
        return equipmentService.updateEquipment(equipment);
    }

    @RequestMapping(path ="/deleteEquipment")
    @CrossOrigin
    public Map<String,Object> deleteEmployee(int equipmentNo){
        return equipmentService.deleteEquipment(equipmentNo);
    }

    @RequestMapping(path = "/totalEquipment")
    @CrossOrigin
    public Common totalEmployee() {
        return equipmentService.totalEquipment();
    }

    @RequestMapping(path = "/getByKeywordEquipment")
    @CrossOrigin
    public PageInfo<Equipment> getByKeywordEquipment(String keyWord, int page, int size){
        return equipmentService.getByKeywordEquipment(keyWord,page,size);
    }

    @RequestMapping(path = "/totalEquipmentFuzzy")
    @CrossOrigin
    public Common totalEquipmentFuzzy(String keyWord) {
        return equipmentService.totalEquipmentFuzzy(keyWord);
    }
}
