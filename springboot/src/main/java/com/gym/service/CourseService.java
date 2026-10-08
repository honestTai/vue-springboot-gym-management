package com.gym.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gym.entity.Common;

import com.gym.entity.Course;
import com.gym.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;


    public PageInfo<Course> getAllCourse(int page, int size){
        PageHelper.startPage(page, size);
        List<Course> list = courseMapper.getAllCourse();
        return PageInfo.of(list);
    }


    public List<Course> getAllCourseRegister(){
        return courseMapper.getAllCourseRegister();
    }

    public double getCoursePriceByCourseNo(int courseNo){
        return courseMapper.getCoursePriceByCourseNo(courseNo);
    }

    public Map<String,Object> addCourse(Course course){
        Map<String,Object> resultMap = new HashMap<>();


        int result =  courseMapper.addCourse(course);


        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","添加成功");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","添加失败");
        }

        return resultMap;
    }

    public Map<String,Object>  updateCourse(Course employee) {
        Map<String,Object> resultMap = new HashMap<>();
        int result =  courseMapper.updateCourse(employee);

        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","修改成功");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","修改失败");
        }

        return resultMap;
    }

    public Map<String,Object>  deleteCourse(int courseNo) {
        Map<String,Object> resultMap = new HashMap<>();
        int result =  courseMapper.deleteCourse(courseNo);

        if(result>0){
            resultMap.put("code",200);
            resultMap.put("message","删除成功");
        }else{
            resultMap.put("code",400);
            resultMap.put("message","删除失败");
        }

        return resultMap;
    }

    public Common totalCourse() {
        return courseMapper.totalCourse();
    }

    public PageInfo<Course> getByKeywordCourse(String keyWord,int page,int size) {
        PageHelper.startPage(page, size);
        List<Course> list = courseMapper.getByKeywordCourse(keyWord);
        return PageInfo.of(list);
    }

    public Common totalCourseFuzzy(String keyWord){
        return courseMapper.totalCourseFuzzy(keyWord);
    }



}


