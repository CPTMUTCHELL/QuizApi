package com.example.demo.repository.specification;

import com.example.demo.entity.Quiz;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class QuizSpec {
    public static Specification<Quiz> nameContains(String word){
        return (Specification<Quiz>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"),"%"+word+"%");
    }
    public static Specification<Quiz> statusContains(boolean status){
        return (Specification<Quiz>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"),status);
    }
    public static Specification<Quiz> dateContains(Date date){
        return (Specification<Quiz>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createdAt"),date);
    }
//
}
