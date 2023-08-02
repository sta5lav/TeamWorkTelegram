package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.Volunteer;
import com.example.shelterforpets.exceptions.VolunteerNotFoundException;
import com.example.shelterforpets.repository.VolunteerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VolunteerServiceTests {
    private VolunteerService volunteerService;
    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private NotificationService notificationService;

    @BeforeEach
    public void init() {
        volunteerService = new VolunteerService(volunteerRepository, notificationService);
    }

    @Test
    public void testFindVolunteer_ExistingVolunteer() {
        long userId = 123456789L;
        Volunteer expectedVolunteer = new Volunteer();
        expectedVolunteer.setUserId(userId);

        when(volunteerRepository.findByUserId(userId)).thenReturn(Optional.of(expectedVolunteer));

        Volunteer actualVolunteer = volunteerService.findVolunteer(userId);

        verify(volunteerRepository).findByUserId(userId);
        assertEquals(expectedVolunteer, actualVolunteer);
    }

    @Test
    public void testFindVolunteer_NonExistingVolunteer() {
        long userId = 123456789L;

        when(volunteerRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class, () -> volunteerService.findVolunteer(userId));
        verify(volunteerRepository).findByUserId(userId);
    }

    @Test
    public void testPostVolunteer_NewVolunteer() {
        long userId = 123456789L;
        Volunteer volunteerToSave = new Volunteer();
        volunteerToSave.setUserId(userId);

        when(volunteerRepository.existsByUserId(userId)).thenReturn(false);
        when(volunteerRepository.save(volunteerToSave)).thenReturn(volunteerToSave);

        Volunteer actualVolunteer = volunteerService.postVolunteer(userId, volunteerToSave);

        verify(volunteerRepository).existsByUserId(userId);
        verify(volunteerRepository).save(volunteerToSave);
        assertEquals(volunteerToSave, actualVolunteer);
    }

    @Test
    public void testPostVolunteer_ExistingVolunteer() {
        long userId = 123456789L;
        Volunteer existingVolunteer = new Volunteer();
        existingVolunteer.setUserId(userId);

        when(volunteerRepository.existsByUserId(userId)).thenReturn(true);

        Volunteer result = volunteerService.postVolunteer(userId, existingVolunteer);

        verify(volunteerRepository).existsByUserId(userId);
        assertNull(result);
    }

    @Test
    public void testPutVolunteer_ExistingVolunteer() {
        long userId = 123456789L;
        Volunteer existingVolunteer = new Volunteer();
        existingVolunteer.setUserId(userId);

        when(volunteerRepository.existsByUserId(userId)).thenReturn(true);
        when(volunteerRepository.save(existingVolunteer)).thenReturn(existingVolunteer);

        Volunteer actualVolunteer = volunteerService.putVolunteer(userId, existingVolunteer);

        verify(volunteerRepository).existsByUserId(userId);
        verify(volunteerRepository).save(existingVolunteer);
        assertEquals(existingVolunteer, actualVolunteer);
    }

    @Test
    public void testPutVolunteer_NonExistingVolunteer() {
        long userId = 123456789L;
        Volunteer nonExistingVolunteer = new Volunteer();
        nonExistingVolunteer.setUserId(userId);

        when(volunteerRepository.existsByUserId(userId)).thenReturn(false);

        Volunteer result = volunteerService.putVolunteer(userId, nonExistingVolunteer);

        verify(volunteerRepository).existsByUserId(userId);
        assertNull(result);
    }

    @Test
    public void testDeleteVolunteer_ExistingVolunteer() {
        long userId = 123456789L;

        when(volunteerRepository.existsByUserId(userId)).thenReturn(true);

        volunteerService.deleteVolunteer(userId);

        verify(volunteerRepository).existsByUserId(userId);
        verify(volunteerRepository).deleteById(userId);
    }

    @Test
    public void testDeleteVolunteer_NonExistingVolunteer() {
        long userId = 123456789L;

        when(volunteerRepository.existsByUserId(userId)).thenReturn(false);

        volunteerService.deleteVolunteer(userId);

        verify(volunteerRepository).existsByUserId(userId);
        verify(volunteerRepository, never()).deleteById(userId);
    }
}
