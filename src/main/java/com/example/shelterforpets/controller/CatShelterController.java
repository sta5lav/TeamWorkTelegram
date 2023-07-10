package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.service.CatShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catShelterClients")
public class CatShelterController {


    private CatShelterService catShelterService;

    public CatShelterController(CatShelterService catShelterService) {
        this.catShelterService = catShelterService;
    }


    @Operation(
            summary = "Поиск клиента приюта для кошек по идентификатору",
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
            tags = "Сервис для работы с клиентами приюта для кошек"
    )
    @GetMapping(value = "{id}")
    public ResponseEntity<CatShelterClient> findClientFromCatShelter(@PathVariable("id") long userId) {
        try {
            return ResponseEntity.ok(catShelterService.findClientFromCatShelter(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Добавление клиента в базу данных клиентов приюта для кошек",
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
            tags = "Сервис для работы с клиентами приюта для кошек"
    )
    @PostMapping(value = "/{id}")
    public ResponseEntity<CatShelterClient> postClientFromCatShelter(@PathVariable("id") long userId,
                                                                     @RequestBody CatShelterClient catShelterClient) {
        return ResponseEntity.ok(catShelterService.postClientFromCatShelter(userId, catShelterClient));
    }


    @Operation(
            summary = "Редактирование клиента в базе данных клиентов приюта для кошек",
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
            tags = "Сервис для работы с клиентами приюта для кошек"
    )
    @PutMapping(value = "/{id}")
    public CatShelterClient putClientFromCatShelter(@PathVariable long userId,
                                                    @RequestBody CatShelterClient catShelterClient) {
        return catShelterService.putClientFromCatShelter(userId, catShelterClient);
    }

    @Operation(
            summary = "Удаление клиента из базы данных клиентов приюта для кошек",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Удаляемый клиент",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            tags = "Сервис для работы с клиентами приюта для кошек"
    )
    @DeleteMapping(value = "/{id}")
    public void deleteClientFromCatShelter(@PathVariable("id") long userId) {
        catShelterService.deleteClientFromCatShelter(userId);
    }

}
