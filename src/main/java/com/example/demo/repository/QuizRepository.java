package com.example.demo.repository;

import com.example.demo.entity.Question;
import com.example.demo.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> , JpaSpecificationExecutor<Quiz> {

}
