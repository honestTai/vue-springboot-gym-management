package com.gym.controller;

import com.github.pagehelper.PageInfo;
import com.gym.entity.Common;
import com.gym.entity.Course;
import com.gym.entity.CourseType;
import com.gym.entity.Equipment;
import com.gym.mapper.CourseMapper;
import com.gym.service.AdminService;
import com.gym.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(path= "/getAllCourse")
    @CrossOrigin
    public PageInfo<Course> getAllCourse(int page, int size){
        return courseService.getAllCourse(page,size);
    }

    @RequestMapping(path= "/getAllCourseRegister")
    @CrossOrigin
    public List<Course> getAllCourseRegister(){
        return courseService.getAllCourseRegister();
    }

    @RequestMapping(path= "/addCourse")
    @CrossOrigin
    public Map<String,Object> addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @RequestMapping(path ="/updateCourse")
    @CrossOrigin
    public Map<String,Object> updateEquipment(@RequestBody Course course){
        return courseService.updateCourse(course);
    }

    @RequestMapping(path ="/deleteCourse")
    @CrossOrigin
    public Map<String,Object> deleteEmployee(int courseNo){
        return courseService.deleteCourse(courseNo);
    }

    @RequestMapping(path = "/totalCourse")
    @CrossOrigin
    public Common totalCourse() {
        return courseService.totalCourse();
    }

    /**
     * 模糊搜索接口
     * @param keyWord 前端传过来的模糊搜索 关键词
     * @param page 第几页
     * @param size 一页的大小
     * @return 返回查到的课程的列表
     */
    @RequestMapping(path = "/getByKeywordCourse")
    @CrossOrigin
    public PageInfo<Course> getByKeywordCourse(String keyWord, int page, int size){
        return courseService.getByKeywordCourse(keyWord,page,size);
    }

    @RequestMapping(path = "/totalCourseFuzzy")
    @CrossOrigin
    public Common totalCourseFuzzy(String keyWord) {
        return courseService.totalCourseFuzzy(keyWord);
    }


    @Autowired
    CourseMapper courseMapper;
    @GetMapping("/courseType")
    @CrossOrigin
    public List<CourseType> courseType() {
        return courseMapper.getAllCourse().stream()
                .map(t -> new CourseType(t.getCourseDesc(), t.getCourseDesc()))
                .collect(Collectors.toList());
    }

}
