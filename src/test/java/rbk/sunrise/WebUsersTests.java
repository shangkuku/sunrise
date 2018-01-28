package rbk.sunrise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rbk.sunrise.entity.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SunriseApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class WebUsersTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void POST_OnUsersWithNoBodyShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/user"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void POST_OnUsersWithFullDataShouldReturnIdAndCreatedStatus() throws Exception {
        mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                User.builder().name("Stefan").build()
                        ))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/user/1"))
                .andExpect(content().string("1"));
    }

    @Test
    public void POST_OnUsersWithSetIdShouldReteurnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                User.builder().name("Stefan").build()
                        ))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void POST_OnUsersWithFullDataAndAgeBelow18ShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                User.builder().name("Stefan").build()
                        ))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User must be at least 18 years old"));
    }

    @Test
    public void POST_OnUsersWithNoFirstNameShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(
                                User.builder().build()
                        ))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("name is required"));

    }

    @Test
    public void GET_OnUsersWithIdShouldReturnUser() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Stefan"))
                .andExpect(jsonPath("$.lastName").value("Stefanowsky"))
                .andExpect(jsonPath("$.age").value(32))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void GET_OnUsersWithWrongIdShouldReturnNotFound404() throws Exception {
        mockMvc.perform(get("/user/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void GET_OnUsersWithStringAsIdShouldReturnBadRequest400() throws Exception {
        mockMvc.perform(get("/user/stefan"))
                .andExpect(status().isBadRequest());

    }

    private String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
