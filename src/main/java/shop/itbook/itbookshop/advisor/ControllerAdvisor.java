package com.nhnacademy.exam.depart.depart.departmentinfo.advisor;

import com.nhnacademy.exam.depart.depart.departmentinfo.exception.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<String> badRequestException400(BadRequestException e) {
        return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<String> internalErrorException500(Exception e) {
        return ResponseEntity.internalServerError().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
    }

}
