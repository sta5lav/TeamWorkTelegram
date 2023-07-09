package com.example.shelterforpets.service;

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

    public String findVolunteer(long userId) {
        if (volunteerRepository.findAllByUserId(userId).toString() == null) {
            return "Волонтера нет в базе данных!";
        }
        return volunteerRepository.findAllByUserId(userId).toString();
    }

    public String postVolunteer(long userId,
                                String name,
                                String phoneNumber) {
        Volunteer volunteer = new Volunteer();
        volunteer.setUserId(userId);
        volunteer.setName(name);
        volunteer.setPhoneNumber(phoneNumber);
        volunteerRepository.save(volunteer);
        return "Данные волонтера успешно добавлены в базу данных";
    }

    public String putVolunteer(Volunteer volunteer) {
        Long userId = volunteer.getUserId();
        if (volunteerRepository.existsAllByUserId(userId) != null) {
            volunteerRepository.save(volunteer);
            return "Данные Волонтера успешно изменены в базе данных";
        }
        return "Волонтер отсутствует в базе данных!";
    }

    /**
     * Delete volunteer by id in volunteer repository
     * @param userId The ID of the user ID in repository
     * @return String
     */
    public String deleteVolunteer(long userId) {
        if(volunteerRepository.existsAllByUserId(userId)) {
            volunteerRepository.deleteById(userId);
            return "Волонтер успешно удален!";
        }
        return "Волонтер отсутствует в базе данных!";
    }



}
