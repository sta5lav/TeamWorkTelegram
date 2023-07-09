package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.service.DogShelterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dogShelterClients")
public class DogShelterController {

    private DogShelterService dogShelterService;

    public DogShelterController(DogShelterService dogShelterService) {
        this.dogShelterService = dogShelterService;
    }

    /**
     * Find client by id from cat shelter repository
     * @param userId The ID of the user ID in repository
     * @return String
     */
    @GetMapping(value = "{id}")
    public String findClientFromDogShelter(@RequestParam("id") long userId) {
        return dogShelterService.findClientFromDogShelter(userId);
    }

    /**
     * Save new client in cat shelter repository
     * @param userId The ID of the user ID in repository
     * @param name The name of client
     * @param phoneNumber The phone number of client
     * @param nickNamePet The nickname pet of client
     * @return String
     */
    @PostMapping(value = "/post/{id}+{name}+{phoneNumber}+{nickNamePet}")
    public String postClientFromDogShelter(@RequestParam("id") long userId,
                                           @RequestParam("name") String name,
                                           @RequestParam("phoneNumber") String phoneNumber,
                                           @RequestParam("nickNamePet") String nickNamePet) {
        return dogShelterService.postClientFromDogShelter(userId, name, phoneNumber, nickNamePet);
    }


    /**
     * Edit client by id in cat shelter repository
     * @param dogShelterClient The client from dog shelter
     * @return String
     */
    @PutMapping(value = "/put/")
    public String putClientFromDogShelter(@RequestBody DogShelterClient dogShelterClient) {
        return dogShelterService.putClientFromDogShelter(dogShelterClient);
    }

    /**
     * Delete client by id in cat shelter repository
     * @param userId The ID of the user ID in repository
     * @return String
     */
    @DeleteMapping(value = "/delete/{id}")
    public String deleteClientFromDogShelter(@RequestParam("id") long userId) {
        return dogShelterService.deleteClientFromDogShelter(userId);
    }




}
