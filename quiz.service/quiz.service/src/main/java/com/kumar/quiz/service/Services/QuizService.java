package com.kumar.quiz.service.Services;

import com.kumar.quiz.service.Dao.QuizDao;
import com.kumar.quiz.service.feign.QuizInterface;
import com.kumar.quiz.service.model.QuestionWrapper;
import com.kumar.quiz.service.model.Quiz;
import com.kumar.quiz.service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class QuizService {


    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        //Call the generate URL -RestTemplate http://localhost:8080/question/generate
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        if (questions == null || questions.isEmpty()) {
            return new ResponseEntity<>("No questions found for the given category", HttpStatus.BAD_REQUEST);
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

//    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
//
//        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
//        Quiz quiz = new Quiz();
//        quiz.setTitle(title);
//        quiz.setQuestionIds(questions);
//        quizDao.save(quiz);
//
//        return new ResponseEntity<>("Success", HttpStatus.CREATED);
//    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }
//public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
//    Optional<Quiz> quiz = quizDao.findById(id);
//    List<Integer> questionsIds = quiz.get().getQuestionIds();
//
//    ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionsIds);
//
//    return questions;
//}
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}