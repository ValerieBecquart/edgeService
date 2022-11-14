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
import java.util.List;

@RestController
public class EdgeServiceController {

    String http = "http://";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;

    /* GET ALL QUESTIONS */
    @GetMapping("questions")
    public List<Question> getAllQuestions(){
        ResponseEntity<List<Question>> responseEntityQuestions =
                restTemplate.exchange(http + gameServiceBaseUrl + "/questions",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
                        });

        return responseEntityQuestions.getBody();
    }

    /* GET SPECIFIC QUESTION BY OBJECTNAME*/
    @GetMapping("question/{objectname}")
    public Question getQuestionByObjectName(@PathVariable String objectname){
        return restTemplate.getForObject(http + gameServiceBaseUrl + "/question/{objectname}",
                Question.class, objectname);
    }

    /* GET SPECIFIC QUESTION BY LEVEL*/
    @GetMapping("/questionsbylevel/{level}")
    public List<Question> getAllQuestionsByLevel(@PathVariable Integer level){
        ResponseEntity<List<Question>> responseEntityQuestions =
                restTemplate.exchange(http + gameServiceBaseUrl + "/questionsbylevel/{level}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
                        },level);

        return responseEntityQuestions.getBody();
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntityUsers =
                restTemplate.exchange(http + userServiceBaseUrl + "/users",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        return responseEntityUsers.getBody();
    }

    /* GET SPECIFIC USER */
    @GetMapping("user/{userID}")
    public User getUserById(@PathVariable int userID){
        return restTemplate.getForObject(http + userServiceBaseUrl +"/user/{userID}",
                User.class, userID);
    }

    /* GETTING ALL SCORES TO DISPLAY HIGHSCORES */
    @GetMapping("/scores")
    public List<User> getTop5HighScoresAsc(){
        ResponseEntity<List<User>> responseEntityUsers =
                restTemplate.exchange(http + userServiceBaseUrl + "/scores",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        return responseEntityUsers.getBody();
    }

    //Get question of the highest level of the game
    @GetMapping("/highestlevel")
    public Question getHighestLevel(){
        return restTemplate.getForObject(http + gameServiceBaseUrl + "/highestlevel", Question.class);
    }

    /* POST NEW USER */
    @PostMapping("user")
    public User createNewUser(@RequestParam String username, @RequestParam Integer avatarID){

        /*Get user count*/
        int userID = getAllUsers().toArray().length + 1;

        /*CREATE NEW USER TO PUT IN DB*/
        User u = new User(userID, username, avatarID);

        /*Return user*/
        return restTemplate.postForObject(http + userServiceBaseUrl + "/user",
                u, User.class);
    }

    /* UPDATE USER SCORE */
    @PutMapping("/user")
    public ResponseEntity<Void> updateScore(@RequestBody User u ){

        restTemplate.exchange(http + userServiceBaseUrl + "/user" ,
                HttpMethod.PUT, new HttpEntity<>(u), User.class);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("user/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userID){
        restTemplate.delete(http + userServiceBaseUrl + "/user/" + userID);

        return ResponseEntity.ok().build();
    }

}
