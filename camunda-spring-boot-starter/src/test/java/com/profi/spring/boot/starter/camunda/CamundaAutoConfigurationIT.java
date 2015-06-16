package com.profi.spring.boot.starter.camunda;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.profi.spring.boot.starter.camunda.test.TestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestApplication.class })
@Transactional
public class CamundaAutoConfigurationIT extends AbstractCamundaAutoConfigurationIT {

    @Test
    public void autoDeploymentTest() {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().processDefinitionName("TestProcess")
                .singleResult();
        assertNotNull(processDefinition);
    }

    @Test
    public void jobConfigurationTest() {
        assertTrue(jobExecutor.isActive());
    }

}
