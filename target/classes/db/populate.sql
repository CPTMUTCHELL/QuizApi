insert into quizzes (name) VALUES ('Quiz1'),('Quiz2'),('Quiz3');
insert into questions(text) values ('Question1'),('Question2'),('Question3');
insert into quizzes_questions (quiz_id, question_id) VALUES (1,1),(1,2),(1,3),(2,2),(2,3),(3,1);