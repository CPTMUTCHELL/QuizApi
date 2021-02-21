package com.example.demo.service;

import com.example.demo.entity.Quiz;
import com.example.demo.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository repository;

    public List<Quiz> getAllQuizzes(Specification<Quiz> quizSpecification,
                                        Integer pageNo, Integer pageSize, String sortBy,
                                    String dir,String questionOrder){

        Sort sort=dir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable paging = PageRequest.of(pageNo, pageSize, sort);
        Page<Quiz> pagedResult = repository.findAll(quizSpecification,paging);
        List<Quiz> content = pagedResult.getContent();
        if (questionOrder.equalsIgnoreCase("DESC")) {
            content.forEach(quiz -> quiz.getQuestions().sort((o1, o2) -> (int) (o2.getId() - o1.getId())));
        }
        if(pagedResult.hasContent()) {
            return content;
        } else {
            return new ArrayList<>();
        }
    }
    public Optional<Quiz> getQuizById(Long id){
        return repository.findById(id);
    }
    public Quiz createQuiz(Quiz quiz){
        quiz.setCreatedAt(new Date());

        return repository.save(quiz);
    }
    public Quiz updateQuiz(Quiz quiz, Quiz quizFromResponse){
        quiz.setName(quizFromResponse.getName());
        quiz.setCreatedAt(quizFromResponse.getCreatedAt());
        quiz.setStatus(quizFromResponse.isStatus());
        quiz.setEndedAt(quizFromResponse.getEndedAt());
        quiz.setQuestions(quizFromResponse.getQuestions());
        return repository.save(quiz);

    }


    public boolean deleteQuiz(Long id){
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception ignored) {
            return false;
        }

    }
}
