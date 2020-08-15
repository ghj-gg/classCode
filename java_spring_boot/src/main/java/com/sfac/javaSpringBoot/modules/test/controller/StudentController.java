package com.sfac.javaSpringBoot.modules.test.controller;

import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.test.entity.Student;
import com.sfac.javaSpringBoot.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 127.0.0.1/api/student  -------  post
     * {"studentName":"ghj","studentCard":"{"cardId":"1"}"}
     */
    @PostMapping(value = "/student",consumes = "application/json")
    public Result<Student> insertStudent(@RequestBody Student student){
        return studentService.insertStudent(student);
    }

    /**
     * 127.0.0.1/api/student/1  -----  get
     */
    @GetMapping("/student/{studentId}")
    public Student getStudentByStudentId(@PathVariable int studentId){
        return studentService.getStudentByStudentId(studentId);
    }

    /**
     *127.0.0.1/api/students ---  post
     * {"currentPage":"1","pageSize":"5","keyWord":"ghj","orderBy":"studentName","sort":"desc"}
     */
    @PostMapping(value = "/students", consumes = "application/json")
    public Page<Student> getStudentsBySearchVo(@RequestBody SearchVo searchVo){
        return studentService.getStudentsBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1/api/students  -----  get
     */
    //@GetMapping("/students")
    //public List<Student> getStudents(){
    //    return studentService.getStudents();
    //}

    /**
     * 127.0.0.1/api/students?studentName=ghj1  ----  get
     */
    //@GetMapping("/students")
    //public List<Student> getStudentByStudentName(@RequestParam String studentName){
    //    return studentService.getStudentByStudentName(studentName);
    //}
    /**
     * 127.0.0.1/api/students?studentName=ghj1&cardId=1  ----  get
     */
    @GetMapping("/students")
    public List<Student> getStudentByParams(@RequestParam String studentName,@RequestParam(required = false,defaultValue = "1") Integer cardId ){
        return studentService.getStudentByStudentName(studentName);
    }
}
