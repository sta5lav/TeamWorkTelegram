package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.DogShelterClient;
import com.example.shelterforpets.service.DogShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dogShelterClients")
public class DogShelterController {

    private DogShelterService dogShelterService;

    public DogShelterController(DogShelterService dogShelterService) {
        this.dogShelterService = dogShelterService;
    }

    @Operation(
            summary = "Поиск клиента приюта для собак по идентификатору",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Найденный клиент",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Клиент отсутствует в базе данных"
                    )},
            tags = "Сервис для работы с клиентами приюта для собак"
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<DogShelterClient> findClientFromDogShelter(@PathVariable("id") long userId) {
        try {
            return ResponseEntity.ok(dogShelterService.findClientFromDogShelter(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Добавление клиента в базу данных клиентов приюта для собак",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленый клиент",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Добавленый клиент",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Сервис для работы с клиентами приюта для собак"
    )
    @PostMapping(value = "/{id}")
    public ResponseEntity<DogShelterClient> createClientFromDogShelter(@PathVariable("id") long userId,
                                                                       @RequestBody DogShelterClient dogShelterClient) {
        return ResponseEntity.ok(dogShelterService.postClientFromDogShelter(userId, dogShelterClient));
    }

    @Operation(
            summary = "Редактирование клиента в базе данных клиентов приюта для собак",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый клиент",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Редактируемый клиент",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Сервис для работы с клиентами приюта для собак"
    )
    @PutMapping(value = "/{id}")
    public DogShelterClient updateClientFromDogShelter(@PathVariable("id") long userId,
                                                       @RequestBody DogShelterClient dogShelterClient) {
        return dogShelterService.putClientFromDogShelter(userId, dogShelterClient);
    }

    @Operation(
            summary = "Удаление клиента из базы данных клиентов приюта для собак",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Удаляемый клиент",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            tags = "Сервис для работы с клиентами приюта для собак"
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteClientFromDogShelter(@PathVariable("id") long userId) {
        try {
            dogShelterService.deleteClientFromDogShelter(userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
