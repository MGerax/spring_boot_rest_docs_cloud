package com.and;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CityControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CityRepository cityRepository;

    private MockMvc mockMvc;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");
    private RestDocumentationResultHandler document;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void testListCity() throws Exception {
        createSampleCity("City1", "descr1");
        createSampleCity("City2", "descr2");
        document = MockMvcRestDocumentation.document("city-list", responseFields(
                fieldWithPath("[].id").description("The cities' ID"),
                fieldWithPath("[].name").description("The cities' name"),
                fieldWithPath("[].description").description("The cities' description"))
        );
        mockMvc.perform(
                get("/city").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(document)
                .andExpect(content().json("[{\"name\":\"City1\", \"description\":\"descr1\"}," +
                        "{\"name\":\"City2\", \"description\":\"descr2\"}," +
                        "{\"name\":\"Dublin\", \"description\":\"The best city\"}]"));
    }

    @Test
    public void testGetCity() throws Exception {
        document = MockMvcRestDocumentation.document("city-get", responseFields(
                fieldWithPath("id").description("The city' ID"),
                fieldWithPath("name").description("The city' name"),
                fieldWithPath("description").description("The city' description"))
        );
        mockMvc.perform(
                get("/city/1").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(document)
                .andExpect(content().json("{\"name\":\"Dublin\", \"description\":\"The best city\"}"));
    }

    @Test
    public void testCreateCity() throws Exception {
        document = MockMvcRestDocumentation.document("city-create", requestFields(
                fieldWithPath("name").description("The city' name"),
                fieldWithPath("description").description("The city' description"))
        );
        mockMvc.perform(post("/city")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Dublin\", \"description\":\"The best city\"}"))
                .andDo(document)
                .andExpect(status().isCreated());
        mockMvc.perform(
                get("/city/1").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Dublin\", \"description\":\"The best city\"}"));
    }

    private City createSampleCity(String name, String description) {
        return cityRepository.save(new City(name, description));
    }
}
