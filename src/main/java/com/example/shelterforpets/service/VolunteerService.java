package com.example.shelterforpets.service;


import com.example.shelterforpets.entity.Client;
import com.example.shelterforpets.entity.Volunteer;
import com.example.shelterforpets.exceptions.ClientNotFoundException;
import com.example.shelterforpets.exceptions.VolunteerNotFoundException;
import com.example.shelterforpets.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import static com.example.shelterforpets.constants.Constants.CLIENT_WITH_OVERDUE_DATE_OF_REPORTS;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final NotificationService notificationService;

    public VolunteerService(VolunteerRepository volunteerRepository,
                            NotificationService notificationService) {
        this.volunteerRepository = volunteerRepository;
        this.notificationService = notificationService;
    }

    public void sendMessageFromUserWithOverdueDateOfReports(Long chatId) {
        notificationService.sendNotification(chatId, CLIENT_WITH_OVERDUE_DATE_OF_REPORTS);
    }

    /**
     * Find volunteer by id from volunteer repository
     * @param userId The ID of the user ID in repository
     * @return Object Volunteer
     */
    public Volunteer findVolunteer(long userId) {
        return volunteerRepository
                .findByUserId(userId)
                .orElseThrow(() -> new VolunteerNotFoundException("Волонтера нет в базе данных!"));
    }

    /**
     * Post volunteer by id from volunteer repository
     * @param userId The ID of the user ID in repository
     * @return Object Volunteer
     */
    public Volunteer postVolunteer(long userId, Volunteer volunteer) {
        if (!volunteerRepository.existsByUserId(userId)) {
            volunteer.setUserId(userId);
            return volunteerRepository.save(volunteer);
        } return null;
    }


    /**
     * Edit volunteer by id in volunteer repository
     * @param volunteer The volunteer
     * @return Object Volunteer
     */
    public Volunteer putVolunteer(long userId, Volunteer volunteer) {
        if (volunteerRepository.existsByUserId(userId)) {
            volunteer.setUserId(userId);
            return volunteerRepository.save(volunteer);
        } return null;
    }

    /**
     * Delete volunteer by id in volunteer repository
     * @param userId The ID of the user ID in repository
     */
    public void deleteVolunteer(long userId) {
        if(volunteerRepository.existsByUserId(userId)) {
            volunteerRepository.deleteById(userId);
        }
    }

}
