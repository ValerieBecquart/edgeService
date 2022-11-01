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
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/questions",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
                        });

        return responseEntityQuestions.getBody();
    }

    /* GET SPECIFIC QUESTION BY OBJECTNAME*/
    //GET: /question/{objectname}
    @GetMapping("question/{objectname}")
    public Question getQuestionById(@PathVariable String objectname){
        Question q = restTemplate.getForObject("http://" + gameServiceBaseUrl + "/question/{objectname}",
                Question.class, objectname);

        return q;
    }

    /* GET SPECIFIC USER */
    //GET: user/{userID}
    @GetMapping("user/{userID}")
    public User getUserById(@PathVariable int userID){
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

    /* POST NEW USER */
    //POST: /user?username={username}&avatarID={avatarID}
    @PostMapping("/user")
    public User createNewUser(@RequestParam String username, @RequestParam Integer avatarID){

        /*Get user count*/
        int userID = (int)getAllScoresAsc().stream().count() + 1;

        /*CREATE NEW USER TO PUT IN DB*/
        User u = new User(userID, username, avatarID);

        /* POST USER TO DB MICROSERVICE */
        User user = restTemplate.postForObject("http://" + userServiceBaseUrl + "/users",
                u, User.class);

        /*Return user*/
        return user;
    }

    /* UPDATE USER SCORE */
    //PUT: /user/{userID}
    @PutMapping("user/{userID}")
    public User updateScore(@RequestBody User u, @PathVariable int userID){

        ResponseEntity<User> responseEntityUser = restTemplate.exchange("http://" + userServiceBaseUrl + "/users/" + userID,
                HttpMethod.PUT, new HttpEntity<>(u), User.class);

        return responseEntityUser.getBody();
    }

    @DeleteMapping("user/{userID}")
    public ResponseEntity deleteUser(@PathVariable int userID){
        restTemplate.delete("http://" + userServiceBaseUrl + "/users/" + userID);

        return ResponseEntity.ok().build();
    }

}
