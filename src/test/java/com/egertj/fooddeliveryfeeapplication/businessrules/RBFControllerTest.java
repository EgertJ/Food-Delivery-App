package com.egertj.fooddeliveryfeeapplication.businessrules;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class RBFControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddingAndGettingRBFFee() throws Exception {

        mockMvc.perform(post("/rbf-fees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"city\": \"Tallinn\",\n" +
                        "    \"vehicleType\": \"Car\",\n" +
                        "    \"fee\": 4.0\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON)
        )   .andExpect(status().isCreated());

        mockMvc.perform(get("/rbf-fees/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"city\": \"Tallinn\",\n" +
                        "    \"vehicleType\": \"Car\",\n" +
                        "    \"fee\": 4.0\n" +
                        "}"));
    }

    @Test
    public void testDeletingExtraFee() throws Exception {
        mockMvc.perform(post("/rbf-fees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"city\": \"Tartu\",\n" +
                        "    \"vehicleType\": \"Bike\",\n" +
                        "    \"fee\": 3.0\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON)
        )   .andExpect(status().isCreated());

        mockMvc.perform(delete("/rbf-fees/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestUpdatingRBFFee() throws Exception {
        mockMvc.perform(post("/rbf-fees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"city\": \"Tallinn\",\n" +
                        "    \"vehicleType\": \"Bike\",\n" +
                        "    \"fee\": 3.0\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON)
        )   .andExpect(status().isCreated());

        mockMvc.perform(put("/rbf-fees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"city\": \"Tartu\",\n" +
                        "    \"vehicleType\": \"Car\",\n" +
                        "    \"fee\": 2.5\n" +
                        "}")
        ).andExpect(status().isOk());

        mockMvc.perform(get("/rbf-fees/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"city\": \"Tartu\",\n" +
                        "    \"vehicleType\": \"Car\",\n" +
                        "    \"fee\": 2.5\n" +
                        "}"));
    }
}
