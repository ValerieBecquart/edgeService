package fact.it.edge_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.edge_service.model.Question;
import fact.it.edge_service.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


//Testing endpoints isolated form other services
@SpringBootTest //configures the class as a class that contains tests
@AutoConfigureMockMvc //sets up the MockMvc object for us to inject
public class FilledUserQuestionControllerUnitTests {

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();


    //testdata
    private User u1 = new User(1, "Harry", "harry@test1.com", 1, 0);
    private User u2 = new User(2, "Meghan", "meghan@test1.com", 2, 5);
    private User u3 = new User(3, "Kate", "kate@test1.com", 3, 20);

    String juist = "juist";
    String fout = "fout";
    private Question g1 = new Question(1, "Vraag 1", 1, 1, 5, juist, fout, fout, 10, 5, "EXTRA_PotionVial");
    private Question g2 = new Question(2, "Vraag 2", 1, 4, 1, juist, fout, fout, 5, 0, "DEF_RoundShield");

    private List<Question> allQuestions = Arrays.asList(g1, g2);
    private List<User> allUsers = Arrays.asList(u1, u2, u3);

    @BeforeEach
    public void initializeMockserver() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetAllQuestions_thenReturnUsersJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + gameServiceBaseUrl + "/questions")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allQuestions))
                );
        mockMvc.perform(get("/questions"))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameId", is(1)))
                .andExpect(jsonPath("$[0].question", is("Vraag 1")))
                .andExpect(jsonPath("$[0].level", is(1)))
                .andExpect(jsonPath("$[0].x", is(1.0)))
                .andExpect(jsonPath("$[0].y", is(5.0)))
                .andExpect(jsonPath("$[0].correctanswer", is("juist")))
                .andExpect(jsonPath("$[0].answertwo", is("fout")))
                .andExpect(jsonPath("$[0].answerthree", is("fout")))
                .andExpect(jsonPath("$[0].objectName", is("EXTRA_PotionVial")))
                .andExpect(jsonPath("$[0].scoreDefensive", is(10)))
                .andExpect(jsonPath("$[0].scoreOffensive", is(5)))

                .andExpect(jsonPath("$[1].gameId", is(2)))
                .andExpect(jsonPath("$[1].question", is("Vraag 2")))
                .andExpect(jsonPath("$[1].level", is(1)))
                .andExpect(jsonPath("$[1].x", is(4.0)))
                .andExpect(jsonPath("$[1].y", is(1.0)))
                .andExpect(jsonPath("$[1].correctanswer", is("juist")))
                .andExpect(jsonPath("$[1].answertwo", is("fout")))
                .andExpect(jsonPath("$[1].answerthree", is("fout")))
                .andExpect(jsonPath("$[1].objectName", is("DEF_RoundShield")))
                .andExpect(jsonPath("$[1].scoreDefensive", is(5)))
                .andExpect(jsonPath("$[1].scoreOffensive", is(0)));
    }

    @Test
    public void givenObjectName_whengetQuestionByObjectName_thenReturnUsersJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + gameServiceBaseUrl + "/question/EXTRA_PotionVial")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(g1))
                );
        mockMvc.perform(get("/question/{objectname}", "EXTRA_PotionVial"))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId", is(1)))
                .andExpect(jsonPath("$.question", is("Vraag 1")))
                .andExpect(jsonPath("$.level", is(1)))
                .andExpect(jsonPath("$.x", is(1.0)))
                .andExpect(jsonPath("$.y", is(5.0)))
                .andExpect(jsonPath("$.correctanswer", is("juist")))
                .andExpect(jsonPath("$.answertwo", is("fout")))
                .andExpect(jsonPath("$.answerthree", is("fout")))
                .andExpect(jsonPath("$.objectName", is("EXTRA_PotionVial")))
                .andExpect(jsonPath("$.scoreDefensive", is(10)))
                .andExpect(jsonPath("$.scoreOffensive", is(5)));
    }
    @Test
    public void givenLevel_whengetAllQuestionsByLevel_thenReturnUsersJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + gameServiceBaseUrl + "/questionsbylevel/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allQuestions))
                );
        mockMvc.perform(get("/questionsbylevel/{level}", 1))


                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameId", is(1)))
                .andExpect(jsonPath("$[0].question", is("Vraag 1")))
                .andExpect(jsonPath("$[0].level", is(1)))
                .andExpect(jsonPath("$[0].x", is(1.0)))
                .andExpect(jsonPath("$[0].y", is(5.0)))
                .andExpect(jsonPath("$[0].correctanswer", is("juist")))
                .andExpect(jsonPath("$[0].answertwo", is("fout")))
                .andExpect(jsonPath("$[0].answerthree", is("fout")))
                .andExpect(jsonPath("$[0].objectName", is("EXTRA_PotionVial")))
                .andExpect(jsonPath("$[0].scoreDefensive", is(10)))
                .andExpect(jsonPath("$[0].scoreOffensive", is(5)))

                .andExpect(jsonPath("$[1].gameId", is(2)))
                .andExpect(jsonPath("$[1].question", is("Vraag 2")))
                .andExpect(jsonPath("$[1].level", is(1)))
                .andExpect(jsonPath("$[1].x", is(4.0)))
                .andExpect(jsonPath("$[1].y", is(1.0)))
                .andExpect(jsonPath("$[1].correctanswer", is("juist")))
                .andExpect(jsonPath("$[1].answertwo", is("fout")))
                .andExpect(jsonPath("$[1].answerthree", is("fout")))
                .andExpect(jsonPath("$[1].objectName", is("DEF_RoundShield")))
                .andExpect(jsonPath("$[1].scoreDefensive", is(5)))
                .andExpect(jsonPath("$[1].scoreOffensive", is(0)));
    }

    @Test
    public void givenUserID_whengetUserById_thenReturnUsersJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + userServiceBaseUrl + "/user/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(u1))
                );
        mockMvc.perform(get("/user/{userID}", 1))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID",is(1)))
                .andExpect(jsonPath("$.name",is("Harry")))
                .andExpect(jsonPath("$.email",is("harry@test1.com")))
                .andExpect(jsonPath("$.avatarID",is(1)))
                .andExpect(jsonPath("$.score",is(0)));
    }
    @Test
    public void whengetTop5HighScoresAsc_thenReturnScoresJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + userServiceBaseUrl + "/scores")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allUsers))
                );
        mockMvc.perform(get("/scores", 1))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID",is(1)))
                .andExpect(jsonPath("$[0].name",is("Harry")))
                .andExpect(jsonPath("$[0].email",is("harry@test1.com")))
                .andExpect(jsonPath("$[0].avatarID",is(1)))
                .andExpect(jsonPath("$[0].score",is(0)))
                            .andExpect(jsonPath("$[1].userID",is(2)))
                .andExpect(jsonPath("$[1].name",is("Meghan")))
                .andExpect(jsonPath("$[1].email",is("meghan@test1.com")))
                .andExpect(jsonPath("$[1].avatarID",is(2)))
                .andExpect(jsonPath("$[1].score",is(5)))
                .andExpect(jsonPath("$[2].userID",is(3)))
                .andExpect(jsonPath("$[2].name",is("Kate")))
                .andExpect(jsonPath("$[2].email",is("kate@test1.com")))
                .andExpect(jsonPath("$[2].avatarID",is(3)))
                .andExpect(jsonPath("$[2].score",is(20)));
    }
    @Test
    public void whenAddUser_thenReturnStatusOk() throws Exception {
        User user5 = new User(10, "Charles", "charles@king.com", 5, 150);
        //GET ALL USERS
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + userServiceBaseUrl + "/users")))
                        .andExpect(method(HttpMethod.GET))
                            .andRespond(withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mapper.writeValueAsString(allUsers)));

        //POST USER IN USER SERVICE
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + userServiceBaseUrl + "/user")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(user5))
                );


        mockMvc.perform(post("/user")
                .param("username" , user5.getName())
                .param("avatarID" , Integer.toString(user5.getAvatarID()))
                .content(mapper.writeValueAsString(user5))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Charles")))
                .andExpect(jsonPath("$.avatarID", is(5)))
                .andExpect(jsonPath("$.email", is("charles@king.com")))
                .andExpect(jsonPath("$.score", is(150)))
                .andExpect(jsonPath("$.userID", is(10)));
    }

    @Test
    public void whenDeleteUser_thenReturnStatusOk()throws Exception{
        mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://" + userServiceBaseUrl + "/user/1")))
            .andExpect(method(HttpMethod.DELETE))
            .andRespond(withStatus(HttpStatus.OK)
            );

        mockMvc.perform(delete("/user/{userID}", 1))
            .andExpect(status().isOk());
    }

    @Test
    public void whenUpdatingScore_thenReturnStatusOk() throws Exception{
        User updatedUser = new User(10, "Charles", "charles@king.com", 5, 250);

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + userServiceBaseUrl + "/user")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedUser)));

        mockMvc.perform(put("/user")
                .content(mapper.writeValueAsString(updatedUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}