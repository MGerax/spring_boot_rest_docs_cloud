package com.and;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class CityControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CityRepository cityRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();
    }

    @Test
    public void listCity() throws Exception {
        createSampleCity("City1", "descr1");
        createSampleCity("City2", "descr2");
        mockMvc.perform(
                get("/city").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"City1\", \"description\":\"descr1\"}," +
                        "{\"name\":\"City2\", \"description\":\"descr2\"}]"));
    }

    private City createSampleCity(String name, String description) {
        return cityRepository.save(new City(name, description));
    }
}
