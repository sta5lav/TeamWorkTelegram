package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.service.CatShelterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catShelterClients")
public class CatShelterController {


    private CatShelterService catShelterService;

    public CatShelterController(CatShelterService catShelterService) {
        this.catShelterService = catShelterService;
    }

    /**
     * Find client by id from cat shelter repository
     * @param userId The ID of the user ID in repository
     * @return String
     */
    @GetMapping(value = "{id}")
    public String findClientFromCatShelter(@RequestParam("id") long userId) {
        return catShelterService.findClientFromCatShelter(userId);
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
    public String postClientFromCatShelter(@RequestParam("id") long userId,
                                           @RequestParam("name") String name,
                                           @RequestParam("phoneNumber") String phoneNumber,
                                           @RequestParam("nickNamePet") String nickNamePet) {
        return catShelterService.postClientFromCatShelter(userId, name, phoneNumber, nickNamePet);
    }


    /**
     * Edit client by id in cat shelter repository
     * @param catShelterClient The client from cat shelter
     * @return String
     */
    @PutMapping(value = "/put/")
    public String putClientFromCatShelter(@RequestBody CatShelterClient catShelterClient) {
        return catShelterService.putClientFromCatShelter(catShelterClient);
    }

    /**
     * Delete client by id in cat shelter repository
     * @param userId The ID of the user ID in repository
     * @return String
     */
    @DeleteMapping(value = "/delete/{id}")
    public String deleteClientFromCatShelter(@RequestParam("id") long userId) {
        return catShelterService.deleteClientFromCatShelter(userId);
    }

}
