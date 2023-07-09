package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.entity.Volunteer;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.example.shelterforpets.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

    private VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * Find client by id from cat shelter repository
     * @param userId The ID of the user ID in repository
     * @return String
     */
    @GetMapping(value = "{id}")
    public String findVolunteer(@RequestParam("id") long userId) {
        return volunteerService.findVolunteer(userId).toString();
    }

    /**
     * Save new client in cat shelter repository
     * @param userId The ID of the user ID in repository
     * @param name The name of client
     * @param phoneNumber The phone number of client
     * @return String
     */
    @PostMapping(value = "/post/{id}+{name}+{phoneNumber}")
    public String postVolunteer(@RequestParam("id") long userId,
                                           @RequestParam("name") String name,
                                           @RequestParam("phoneNumber") String phoneNumber) {
        return volunteerService.postVolunteer(userId, name, phoneNumber);
    }


    /**
     * Edit volunteer by id in volunteer repository
     * @param volunteer The volunteer from volunteer shelter
     * @return String
     */
    @PutMapping(value = "/put/")
    public String putVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.putVolunteer(volunteer);
    }

    /**
     * Delete volunteer by id in volunteer repository
     * @param userId The ID of the user ID in repository
     * @return String
     */
    @DeleteMapping(value = "/delete/{id}")
    public String deleteVolunteer(@RequestParam("id") long userId) {
        return volunteerService.deleteVolunteer(userId);
    }


}
