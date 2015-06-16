package com.profi.spring.boot.starter.camunda;

import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.profi.spring.boot.starter.camunda.test.TestActuatorApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestActuatorApplication.class })
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@DirtiesContext
public class CamundaBpmRestConfigurationIT {

    @Value("${local.server.port}")
    private int port;

    @Value("${security.user.password}")
    private String password;

    @Autowired
    private CamundaBpmProperties camundaBpmProperties;

    @Test
    public void processDefinitionTest() {
        ResponseEntity<ProcessDefinitionDto[]> entity = new TestRestTemplate("user",
                password).getForEntity(
                "http://localhost:{port}/engine/{engineName}/process-definition",
                ProcessDefinitionDto[].class, port,
                camundaBpmProperties.getProcessEngineName());
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("TestProcess", entity.getBody()[0].getKey());
    }
}
