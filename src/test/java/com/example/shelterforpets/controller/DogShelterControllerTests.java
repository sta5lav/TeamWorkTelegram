package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.exceptions.ClientNotFoundException;
import com.example.shelterforpets.repository.DogShelterClientRepository;
import com.example.shelterforpets.service.DogShelterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DogShelterControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DogShelterService dogShelterService;
    @MockBean
    private DogShelterClientRepository dogShelterClientRepository;
    @InjectMocks
    private DogShelterController dogShelterController;

    @Test
    public void testFindClientFromDogShelter_Success() throws Exception {
        Long id = 1L;
        String name = "Bob";
        String phoneNumber = "88005553535";

        DogShelterClient client = new DogShelterClient();
        client.setId(id);
        client.setName(name);
        client.setPhoneNumber(phoneNumber);

        when(dogShelterService.findClientFromDogShelter(id)).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dogShelterClients/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber));
    }

    @Test
    public void testFindClientFromDogShelter_NotFound() throws Exception {
        long id = 1234L;
        when(dogShelterService.findClientFromDogShelter(id)).thenThrow(new ClientNotFoundException());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/dogShelterClients/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), ((MvcResult) result).getResponse().getStatus());
    }

    @Test
    public void testCreateClientFromDogShelter_Success() throws Exception {
        long userId = 1234L;
        DogShelterClient client = new DogShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(dogShelterService.postClientFromDogShelter(userId, client)).thenReturn(client);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/dogShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        DogShelterClient responseClient = new ObjectMapper().readValue(responseBody, DogShelterClient.class);
        assertEquals(userId, responseClient.getUserId());
        assertEquals("John", responseClient.getName());
        assertEquals("123456789", responseClient.getPhoneNumber());
    }

    @Test
    public void testCreateClientFromDogShelter_Failure() throws Exception {
        long userId = 1234L;
        DogShelterClient client = new DogShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(dogShelterService.postClientFromDogShelter(userId, client)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/dogShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString()); // Empty body for a failure response
    }

    @Test
    public void testUpdateClientFromDogShelter_Success() throws Exception {
        long userId = 1234L;
        DogShelterClient client = new DogShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(dogShelterService.putClientFromDogShelter(userId, client)).thenReturn(client);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/dogShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        DogShelterClient responseClient = new ObjectMapper().readValue(responseBody, DogShelterClient.class);
        assertEquals(userId, responseClient.getUserId());
        assertEquals("John", responseClient.getName());
        assertEquals("123456789", responseClient.getPhoneNumber());
    }

    @Test
    public void testUpdateClientFromDogShelter_Failure() throws Exception {
        long userId = 1234L;
        DogShelterClient client = new DogShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(dogShelterService.putClientFromDogShelter(userId, client)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/dogShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString()); // Empty body for a failure response
    }

    @Test
    public void testDeleteClientFromDogShelter_Success() throws Exception {
        long userId = 1234L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/dogShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        verify(dogShelterService, times(1)).deleteClientFromDogShelter(userId);
    }

    @Test
    public void testDeleteClientFromDogShelter_NotFound() throws Exception {
        long userId = 1234L;

        doThrow(new ClientNotFoundException("Client not found"))
                .when(dogShelterService).deleteClientFromDogShelter(userId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/dogShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}
