package com.gym.controller;


import com.gym.service.AdminService;
import com.gym.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
public class AdminController {


    @Autowired
    private AdminService adminService;

    @RequestMapping(path="/getAdminPassword")
    @CrossOrigin
    public Map<String,Object> getAdminPassword(String adminAccount,String adminPassword ) {
        return adminService.getAdminPassword(adminAccount,adminPassword);
    }

    @GetMapping(path="/checkToken")
    public Boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        return JwtUtil.checkToken(token);
    }
}
