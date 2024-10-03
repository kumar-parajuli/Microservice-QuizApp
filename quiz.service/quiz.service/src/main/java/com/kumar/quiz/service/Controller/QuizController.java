package com.kumar.quiz.service.Controller;


import com.kumar.quiz.service.Services.QuizService;
import com.kumar.quiz.service.model.QuestionWrapper;
import com.kumar.quiz.service.model.QuizDto;
import com.kumar.quiz.service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")

public class QuizController {

    @Autowired
    QuizService quizService;



    @PostMapping("/create")
    public ResponseEntity<String> CreateQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
    }


//    Get the quize
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }
//    //Get the response of right answer
    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);

    }
}
