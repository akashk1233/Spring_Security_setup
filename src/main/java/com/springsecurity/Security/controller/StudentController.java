package com.springsecurity.Security.controller;

import com.springsecurity.Security.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    List<Student> studentList = new ArrayList<>(List.of(
            new Student(1,"allen","21"),
            new Student(2,"gadda","23"),
            new Student(3,"Akash","23")
    ));
    @GetMapping("/student")
    public List<Student> getStudent(){
        return studentList;
    }

    @PostMapping("/students")
    public Student postStudent(@RequestBody Student st){
//        Student st = new Student(4,"charan","23");
        studentList.add(st);
        return st;
    }

    @GetMapping("/crsf")
    public CsrfToken getToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
