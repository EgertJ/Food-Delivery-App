package com.egertj.fooddeliveryfeeapplication.businessrules;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraFeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void testAddingAndGettingExtraFee() throws Exception {

        mockMvc.perform(post("/extra-fees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{    \"condition\": \"ATEF\",\n" +
                                "        \"vehicleType\": \"Bike\",\n" +
                                "        \"temperature_lower_bound\": null,\n" +
                                "        \"temperature_upper_bound\": -10.0,\n" +
                                "        \"wind_speed_lower_bound\": null,\n" +
                                "        \"wind_speed_upper_bound\": null,\n" +
                                "        \"phenomenon\": null,\n" +
                                "        \"fee\": 1.0}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());




        mockMvc.perform(get("/extra-fees/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{    \"condition\": \"ATEF\",\n" +
                        "        \"vehicleType\": \"Bike\",\n" +
                        "        \"temperature_lower_bound\": null,\n" +
                        "        \"temperature_upper_bound\": -10.0,\n" +
                        "        \"wind_speed_lower_bound\": null,\n" +
                        "        \"wind_speed_upper_bound\": null,\n" +
                        "        \"phenomenon\": null,\n" +
                        "        \"fee\": 1.0}"));
    }

    @Order(3)
    @Test
    public void testDeletingExtraFee() throws Exception {
        mockMvc.perform(post("/extra-fees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{    \"condition\": \"ATEF\",\n" +
                                "        \"vehicleType\": \"Bike\",\n" +
                                "        \"temperature_lower_bound\": null,\n" +
                                "        \"temperature_upper_bound\": -10.0,\n" +
                                "        \"wind_speed_lower_bound\": null,\n" +
                                "        \"wind_speed_upper_bound\": null,\n" +
                                "        \"phenomenon\": null,\n" +
                                "        \"fee\": 1.0}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/extra-fees/1"))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void testUpdatingExtraFee() throws Exception {
        mockMvc.perform(post("/extra-fees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{    \"condition\": \"ATEF\",\n" +
                                "        \"vehicleType\": \"Bike\",\n" +
                                "        \"temperature_lower_bound\": null,\n" +
                                "        \"temperature_upper_bound\": -10.0,\n" +
                                "        \"wind_speed_lower_bound\": null,\n" +
                                "        \"wind_speed_upper_bound\": null,\n" +
                                "        \"phenomenon\": null,\n" +
                                "        \"fee\": 1.0}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/extra-fees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{    \"condition\": \"ATEF\",\n" +
                                "        \"vehicleType\": \"Scooter\",\n" +
                                "        \"temperature_lower_bound\": -10.0,\n" +
                                "        \"temperature_upper_bound\": 0,\n" +
                                "        \"wind_speed_lower_bound\": null,\n" +
                                "        \"wind_speed_upper_bound\": null,\n" +
                                "        \"phenomenon\": null,\n" +
                                "        \"fee\": 1.0}")).andExpect(status().isOk());

        mockMvc.perform(get("/extra-fees/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{    \"condition\": \"ATEF\",\n" +
                        "        \"vehicleType\": \"Scooter\",\n" +
                        "        \"temperature_lower_bound\": -10.0,\n" +
                        "        \"temperature_upper_bound\": 0.0,\n" +
                        "        \"wind_speed_lower_bound\": null,\n" +
                        "        \"wind_speed_upper_bound\": null,\n" +
                        "        \"phenomenon\": null,\n" +
                        "        \"fee\": 1.0}"));

    }

}
