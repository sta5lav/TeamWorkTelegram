package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.exceptions.ClientNotFoundException;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.service.CatShelterService;
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
public class CatShelterControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CatShelterService catShelterService;
    @MockBean
    private CatShelterClientRepository catShelterClientRepository;
    @InjectMocks
    private CatShelterController catShelterController;

    @Test
    public void testFindClientFromCatShelter_Success() throws Exception {
        Long id = 1L;
        String name = "Bob";
        String phoneNumber = "88005553535";

        CatShelterClient client = new CatShelterClient();
        client.setId(id);
        client.setName(name);
        client.setPhoneNumber(phoneNumber);

        when(catShelterService.findClientFromCatShelter(id)).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/catShelterClients/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phoneNumber").value(phoneNumber));
    }

    @Test
    public void testFindClientFromCatShelter_NotFound() throws Exception {
        long id = 1234L;
        when(catShelterService.findClientFromCatShelter(id)).thenThrow(new ClientNotFoundException());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/catShelterClients/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), ((MvcResult) result).getResponse().getStatus());
    }

    @Test
    public void testCreateClientFromCatShelter_Success() throws Exception {
        long userId = 1234L;
        CatShelterClient client = new CatShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(catShelterService.postClientFromCatShelter(userId, client)).thenReturn(client);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/catShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        CatShelterClient responseClient = new ObjectMapper().readValue(responseBody, CatShelterClient.class);
        assertEquals(userId, responseClient.getUserId());
        assertEquals("John", responseClient.getName());
        assertEquals("123456789", responseClient.getPhoneNumber());
    }

    @Test
    public void testCreateClientFromCatShelter_Failure() throws Exception {
        long userId = 1234L;
        CatShelterClient client = new CatShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(catShelterService.postClientFromCatShelter(userId, client)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/catShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString()); // Empty body for a failure response
    }

    @Test
    public void testUpdateClientFromCatShelter_Success() throws Exception {
        long userId = 1234L;
        CatShelterClient client = new CatShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(catShelterService.putClientFromCatShelter(userId, client)).thenReturn(client);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/catShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        CatShelterClient responseClient = new ObjectMapper().readValue(responseBody, CatShelterClient.class);
        assertEquals(userId, responseClient.getUserId());
        assertEquals("John", responseClient.getName());
        assertEquals("123456789", responseClient.getPhoneNumber());
    }

    @Test
    public void testUpdateClientFromCatShelter_Failure() throws Exception {
        long userId = 1234L;
        CatShelterClient client = new CatShelterClient();
        client.setUserId(userId);
        client.setName("John");
        client.setPhoneNumber("123456789");

        when(catShelterService.putClientFromCatShelter(userId, client)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/catShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString()); // Empty body for a failure response
    }

    @Test
    public void testDeleteClientFromCatShelter_Success() throws Exception {
        long userId = 1234L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/catShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        verify(catShelterService, times(1)).deleteClientFromCatShelter(userId);
    }

    @Test
    public void testDeleteClientFromCatShelter_NotFound() throws Exception {
        long userId = 1234L;

        doThrow(new ClientNotFoundException("Client not found"))
                .when(catShelterService).deleteClientFromCatShelter(userId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/catShelterClients/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}
