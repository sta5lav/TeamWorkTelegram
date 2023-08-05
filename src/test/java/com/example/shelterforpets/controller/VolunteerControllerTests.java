package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.Volunteer;
import com.example.shelterforpets.exceptions.ClientNotFoundException;
import com.example.shelterforpets.exceptions.VolunteerNotFoundException;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.example.shelterforpets.service.VolunteerService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VolunteerControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VolunteerService volunteerService;
    @MockBean
    private VolunteerRepository volunteerRepository;
    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    public void testFindVolunteer_Success() throws Exception {
        // Prepare the data for the request
        long userId = 1234L;
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(userId);
        volunteer.setName("John Doe");
        volunteer.setPhoneNumber("1234567890");

        // Mock the service method to return the volunteer when finding by userId
        when(volunteerService.findVolunteer(userId)).thenReturn(volunteer);

        // Perform the HTTP GET request
        MvcResult result = mockMvc.perform(get("/volunteer/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Check the response
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        String content = result.getResponse().getContentAsString();
        Volunteer actualVolunteer = new ObjectMapper().readValue(content, Volunteer.class);
        assertEquals(volunteer.getUserId(), actualVolunteer.getUserId());
        assertEquals(volunteer.getName(), actualVolunteer.getName());
        assertEquals(volunteer.getPhoneNumber(), actualVolunteer.getPhoneNumber());

        // Verify that the findVolunteer method in the service was called once with the correct userId
        verify(volunteerService, times(1)).findVolunteer(userId);
    }

    @Test
    public void testFindVolunteer_NotFound() throws Exception {
        long id = 1234L;
        when(volunteerService.findVolunteer(id)).thenThrow(new VolunteerNotFoundException());

        MvcResult result = mockMvc.perform(get("/volunteer/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), ((MvcResult) result).getResponse().getStatus());
    }

    @Test
    public void testSendWarningMessageFromClient_ValidUserId_SuccessfulInvocation() throws Exception {
        long userId = 1L;

        mockMvc.perform(get("/volunteer/sendWarningMessage/" + userId))
                .andExpect(status().isOk());

        // Проверяем, что метод volunteerService.sendMessageFromUserWithOverdueDateOfReports(userId) был вызван один раз с верным userId
        verify(volunteerService, times(1)).sendMessageFromUserWithOverdueDateOfReports(userId);
    }

    @Test
    public void testCreateVolunteer_Success() throws Exception {
        long userId = 1234L;
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(userId);
        volunteer.setName("John Doe");
        volunteer.setPhoneNumber("1234567890");

        when(volunteerService.postVolunteer(userId, volunteer)).thenReturn(volunteer);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/volunteer/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(volunteer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Volunteer responseClient = new ObjectMapper().readValue(responseBody, Volunteer.class);
        assertEquals(userId, responseClient.getUserId());
        assertEquals("John Doe", responseClient.getName());
        assertEquals("1234567890", responseClient.getPhoneNumber());
    }

    @Test
    public void testCreateVolunteer_Failure() throws Exception {
        long userId = 1234L;
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(userId);
        volunteer.setName("John Doe");
        volunteer.setPhoneNumber("1234567890");

        when(volunteerService.postVolunteer(userId, volunteer)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/volunteer/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(volunteer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString()); // Empty body for a failure response
    }

    @Test
    public void testUpdateVolunteer_Success() throws Exception {
        long userId = 1234L;
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(userId);
        volunteer.setName("John");
        volunteer.setPhoneNumber("123456789");

        when(volunteerService.putVolunteer(userId, volunteer)).thenReturn(volunteer);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/volunteer/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(volunteer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Volunteer responseClient = new ObjectMapper().readValue(responseBody, Volunteer.class);
        assertEquals(userId, responseClient.getUserId());
        assertEquals("John", responseClient.getName());
        assertEquals("123456789", responseClient.getPhoneNumber());
    }

    @Test
    public void testUpdateVolunteer_Failure() throws Exception {
        long userId = 1234L;
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(userId);
        volunteer.setName("John");
        volunteer.setPhoneNumber("123456789");

        when(volunteerService.putVolunteer(userId, volunteer)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/volunteer/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(volunteer)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString()); // Empty body for a failure response
    }

    @Test
    public void testDeleteVolunteer_Success() throws Exception {
        long userId = 1234L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/volunteer/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        verify(volunteerService, times(1)).deleteVolunteer(userId);
    }

    @Test
    public void testDeleteVolunteer_NotFound() throws Exception {
        long userId = 1234L;

        doThrow(new ClientNotFoundException("Client not found"))
                .when(volunteerService).deleteVolunteer(userId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/volunteer/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}
