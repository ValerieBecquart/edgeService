package fact.it.edge_service.controller;

import fact.it.edge_service.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("questions")
    public List<Question> getAllQuestions(){
        List<Question> returnList = new ArrayList<>();

        ResponseEntity<List<Question>> responseEntityQuestions =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/question",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
                        });

        returnList = responseEntityQuestions.getBody();

        return  returnList;

    }


}
