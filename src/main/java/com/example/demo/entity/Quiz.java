package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @CreatedDate()
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "ended_at")
    private Date endedAt;
    private boolean status;
    @ManyToMany( cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "quizzes_questions",
    joinColumns = @JoinColumn(name = "quiz_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "question_id",referencedColumnName = "id"))
    private List<Question> questions;
}
