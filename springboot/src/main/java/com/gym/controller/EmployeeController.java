package com.gym.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gym.entity.Common;
import com.gym.entity.Employee;

import com.gym.entity.Equipment;
import com.gym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(path= "/getAllEmployee")
    @CrossOrigin
    public PageInfo<Employee> getAllEmployee(int page, int size){

        return employeeService.getAllEmployee(page,size);
    }

    @RequestMapping(path= "/addEmployee")
    @CrossOrigin
    public Map<String,Object> addEmployee(Employee employee){
        return employeeService.addEmployee(employee);
    }

    @RequestMapping(path ="/updateEmployee")
    @CrossOrigin
    public Map<String,Object> updateEmployee(Employee employee){
        return employeeService.updateEmployee(employee);
    }

    @RequestMapping(path ="/deleteEmployee")
    public Map<String,Object> deleteEmployee(int employeeNo){
        return employeeService.deleteEmployee(employeeNo);
    }

    @RequestMapping(path = "/totalEmployee")
    @CrossOrigin
    public Common totalEmployee() {
        return employeeService.totalEmployee();
    }

    @RequestMapping(path = "/getByKeywordEmployee")
    @CrossOrigin
    public PageInfo<Employee> getByKeywordEmployee(String keyWord, int page, int size){
        return employeeService.getByKeywordEmployee(keyWord,page,size);
    }

    @RequestMapping(path = "/totalEmployeeFuzzy")
    @CrossOrigin
    public Common totalEmployeeFuzzy(String keyWord) {
        return employeeService.totalEmployeeFuzzy(keyWord);
    }

    @GetMapping("/getAllCoach")
    @CrossOrigin
    public List<Employee> getAllCoach() {
        return employeeService.getAllCoach();
    }
}

