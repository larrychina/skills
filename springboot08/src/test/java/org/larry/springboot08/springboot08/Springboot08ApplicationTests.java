package org.larry.springboot08.springboot08;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.paint.Color;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.larry.springboot08.springboot08.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

@JsonTest
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot08ApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerialization() throws JsonProcessingException {
        User user = new User(Color.ALICEBLUE);
        String json = objectMapper.writeValueAsString(user);

        assertEquals("{\"favoriteColor\":\"#f0f8ff\"}", json);
    }

    @Test
    public void testDeSerialization() throws IOException {

        String json = "{\"favoriteColor\":\"#f0f1ff\"}";

        User user = objectMapper.readValue(json, User.class);

        assertEquals(Color.ALICEBLUE, user.getFavoriteColor());
    }


}
