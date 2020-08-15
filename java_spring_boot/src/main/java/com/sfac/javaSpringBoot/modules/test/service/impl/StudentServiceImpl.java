package com.sfac.javaSpringBoot.modules.test.service.impl;

import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.test.entity.Student;
import com.sfac.javaSpringBoot.modules.test.repository.StudentRepository;
import com.sfac.javaSpringBoot.modules.test.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public Result<Student> insertStudent(Student student) {
        student.setCreateDate(LocalDateTime.now());
        studentRepository.saveAndFlush(student);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"Insert success",student);
    }

    @Override
    public Student getStudentByStudentId(int studentId) {
        return studentRepository.findById(studentId).get();
    }

    @Override
    public Page<Student> getStudentsBySearchVo(SearchVo searchVo) {
        String orderBy = StringUtils.isBlank(searchVo.getOrderBy()) ? "studentId" : searchVo.getOrderBy();
        Sort.Direction direction = StringUtils.isBlank(searchVo.getSort()) || searchVo.getSort().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = new Sort(direction,orderBy);
        //searchVo.getCurrentPage()起始页从零开始
        Pageable pageable = PageRequest.of(
                searchVo.getCurrentPage()-1,searchVo.getPageSize(),sort);
        //build Example对象
        Student student = new Student();
        student.setStudentName(searchVo.getKeyWord());
        //匹配的规则
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("studentName",math -> math.contains()).withIgnorePaths("studentId");
        Example<Student> example = Example.of(student,matcher);
        return studentRepository.findAll(example,pageable);

    }

    //@Override
    //public List<Student> getStudents() {
    //    Sort.Direction direction = Sort.Direction.DESC;
    //    Sort sort = new Sort(direction,"studentName");
    //    return studentRepository.findAll(sort);
    //}

    @Override
    public List<Student> getStudentByStudentName(String studentName) {
        //return Optional.ofNullable(studentRepository.findByStudentName(studentName)).orElse(Collections.emptyList());
        //return Optional.ofNullable(studentRepository.findByStudentNameLike(String.format("%s%s%s","%",studentName,"%"))).orElse(Collections.emptyList());
        return Optional.ofNullable(studentRepository.findTop2ByStudentNameLike(String.format("%s%s%s","%",studentName,"%"))).orElse(Collections.emptyList());
    }

    @Override
    public List<Student> getStudentByStudentName(String studentName, int cardId) {
        if(cardId > 0 ){
            return studentRepository.getStudentsByParams(studentName,cardId);
        }else {
            return Optional.ofNullable(studentRepository.findTop2ByStudentNameLike(String.format("%s%s%s","%",studentName,"%"))).orElse(Collections.emptyList());
        }
    }
}
