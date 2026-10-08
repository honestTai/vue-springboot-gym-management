package com.gym.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gym.entity.Common;

import com.gym.entity.Course;
import com.gym.entity.Equipment;
import com.gym.mapper.EquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    public PageInfo<Equipment> getAllEquipment(int page, int size){
        PageHelper.startPage(page, size);
        List<Equipment> list = equipmentMapper.getAllEquipment();
        list.forEach(x->{
            x.setTmpNum(0);
        });
        return PageInfo.of(list);
    }

    public Map<String,Object> addEquipment(Equipment equipment){
        Map<String,Object> resultMap = new HashMap<>();


        int result =  equipmentMapper.addEquipment(equipment);

        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","添加成功");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","添加失败");
        }

        return resultMap;
    }

    public Map<String,Object>  updateEquipment(Equipment equipment) {
        Map<String,Object> resultMap = new HashMap<>();
        int result =  equipmentMapper.updateEquipment(equipment);

        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","修改成功");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","修改失败");
        }

        return resultMap;
    }

    public Map<String,Object>  deleteEquipment(int equipmentNo) {
        Map<String,Object> resultMap = new HashMap<>();
        int result =  equipmentMapper.deleteEquipment(equipmentNo);

        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","删除成功");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","删除失败");
        }

        return resultMap;
    }

    public Common totalEquipment() {
        return equipmentMapper.totalEquipment();
    }

    public PageInfo<Equipment> getByKeywordEquipment(String keyWord, int page, int size) {
        PageHelper.startPage(page, size);
        List<Equipment> list = equipmentMapper.getByKeywordEquipment(keyWord);
        return PageInfo.of(list);
    }

    public  Common totalEquipmentFuzzy(String keyWord){
        return equipmentMapper.totalEquipmentFuzzy(keyWord);
    }

}


