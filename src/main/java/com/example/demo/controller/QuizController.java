package com.example.demo.controller;

import com.example.demo.entity.Quiz;
import com.example.demo.repository.specification.QuizSpec;
import com.example.demo.service.QuizService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    @Autowired
    private QuizService service;
    @ApiOperation(value = "Gets all quizzes",
            notes = "You can provide different request params for sorting and pagination operations",
    response = Quiz.class)
    @GetMapping("/")
    public ResponseEntity<List<Quiz>> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String dir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date createDate,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue ="ASC") String questionSortOrder)
    {
        Specification<Quiz> spec= Specification.where(null);
        if (name!=null) spec=spec.and(QuizSpec.nameContains(name));
        if (createDate!=null) spec=spec.and(QuizSpec.dateContains(createDate));
        if (status!=null) {
            if (status.equals("false")) spec=spec.and(QuizSpec.statusContains(false));
            else spec=spec.and(QuizSpec.statusContains(true));

        }
        List<Quiz> list = service.getAllQuizzes(spec,pageNo, pageSize, sortBy, dir,questionSortOrder);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz){
        Quiz serviceQuiz = service.createQuiz(quiz);
        if (serviceQuiz==null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(serviceQuiz,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz){
        Optional<Quiz> quizById = service.getQuizById(id);
        if (quizById.isPresent()){
            service.updateQuiz(quizById.get(),quiz);
            return new ResponseEntity<>(quizById.get(),HttpStatus.OK);
        }
        else {

            service.createQuiz(quiz);
            return new ResponseEntity<>(quiz,HttpStatus.CREATED);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable Long id){
        boolean isRemoved = service.deleteQuiz(id);
        if (!isRemoved) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
