package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.entity.Volunteer;
import com.example.shelterforpets.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService {

    private VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Find volunteer by id from volunteer repository
     * @param userId The ID of the user ID in repository
     * @return Object Volunteer
     */
    public Volunteer findVolunteer(long userId) {
        return volunteerRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Клиента нет в базе данных!"));
    }

    /**
     * Post volunteer by id from volunteer repository
     * @param userId The ID of the user ID in repository
     * @return Object Volunteer
     */
    public Volunteer postVolunteer(long userId, Volunteer volunteer) {
        if (volunteerRepository.existsAllByUserId(userId) == null) {
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
        if (volunteerRepository.existsAllByUserId(userId) != null) {
            volunteer.setUserId(userId);
            return volunteerRepository.save(volunteer);
        } return null;
    }

    /**
     * Delete volunteer by id in volunteer repository
     * @param userId The ID of the user ID in repository
     */
    public void deleteVolunteer(long userId) {
        if(volunteerRepository.existsAllByUserId(userId)) {
            volunteerRepository.deleteById(userId);
        }
    }

}
