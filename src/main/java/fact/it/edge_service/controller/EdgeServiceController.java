package fact.it.edge_service.controller;

import fact.it.edge_service.model.Question;
import fact.it.edge_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EdgeServiceController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;

    /* GET ALL QUESTIONS */
    //GET: /questions
    @GetMapping("questions")
    public List<Question> getAllQuestions(){
        ResponseEntity<List<Question>> responseEntityQuestions =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/question",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
                        });

        return responseEntityQuestions.getBody();
    }

    /* GET SPECIFIC QUESTION */
    //GET: /question/{id}
    @GetMapping("question/{id}")
    public Question getQuestionById(@PathVariable int id){
        Question q = restTemplate.getForObject("http://" + gameServiceBaseUrl + "/question/{id}",
                Question.class, id);

        return q;
    }

    /* GET SPECIFIC USER */
    //GET: user/{userID}
    @GetMapping("user/{userID}")
    public User getUserById(@PathVariable String userID){
        User u = restTemplate.getForObject("http://" + userServiceBaseUrl +"/user/{userID}",
                User.class, userID);
        return u;
    }

    /* GETTING ALL SCORES TO DISPLAY HIGHSCORES */
    //GET: /scores
    @GetMapping("/scores")
    public List<User> getAllScoresAsc(){
        ResponseEntity<List<User>> responseEntityUsers =
                restTemplate.exchange("http://" + userServiceBaseUrl + "/scores",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        return responseEntityUsers.getBody();
    }

    @GetMapping("/scoreuser")
    public User getMethodWorking(){
        User u = restTemplate.getForObject("http://" + userServiceBaseUrl +"/user/{userID}",
                User.class, "u1");
        return u;
    }

    /* POST NEW USER */
    //POST: /user
    @PostMapping("/user")
    public User createNewUser(@RequestBody User u){

        User user = restTemplate.postForObject("http://" + userServiceBaseUrl + "/users",
                u, User.class);

        return user;
    }

    /* UPDATE USER SCORE */
    //PUT: /user/{userID}
    @PutMapping("user/{userID}")
    public User updateScore(@RequestBody User u, @PathVariable String userID){

        ResponseEntity<User> responseEntityUser = restTemplate.exchange("http://" + userServiceBaseUrl + "/users/" + userID,
                HttpMethod.PUT, new HttpEntity<>(u), User.class);

        return responseEntityUser.getBody();
    }

    @DeleteMapping("user/{userID}")
    public ResponseEntity deleteUser(@PathVariable String userID){
        restTemplate.delete("http://" + userServiceBaseUrl + "/users/" + userID);

        return ResponseEntity.ok().build();
    }

}
