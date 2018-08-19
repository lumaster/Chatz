package io.github.jhipster.application.config;

import io.github.jhipster.application.ChatzApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatzApp.class)
public class ResourcesTest {

    @Autowired
    private APIProperties APIProperties;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private Environment env;

    @Test
    public void assertThatAPIResourcesHaveValue() {
        System.out.println(APIProperties.toString());
        assertThat(APIProperties.getFacebook()).isNotEmpty();
    }

    @Test
    public void assertThatHaveValuesOnProperties() {
        System.out.println(applicationProperties.toString());
        assertThat(applicationProperties.getUrl()).isNotEmpty();
    }
}
