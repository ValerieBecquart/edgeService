package fact.it.edge_service;

import fact.it.edge_service.controller.EdgeServiceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EdgeServiceApplicationTests {

    @Autowired
    private EdgeServiceController edgeServiceController;

    @Test
    void contextLoads() throws Exception{
        assertThat(edgeServiceController).isNotNull();
    }

}
