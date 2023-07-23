package com.example.shelterforpets.controller;

import com.example.shelterforpets.entity.Client;
import com.example.shelterforpets.entity.Volunteer;
import com.example.shelterforpets.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Поиск волонтера по идентификатору",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Найденный волонтер",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Волонтер отсутствует в базе данных"
                    )},
            tags = "Сервис для работы с волонтерами"
    )
    @GetMapping(value = "{id}")
    public ResponseEntity<Volunteer> findVolunteer(@PathVariable("id") long userId) {
        try {
            return ResponseEntity.ok(volunteerService.findVolunteer(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Отправка предупреждающего сообщения",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Адресованный пользователь",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            tags = "Сервис для работы с волонтерами"
    )
    @GetMapping(value = "/sendWarningMessage/{id}")
    public void sendWarningMessageFromClient(@PathVariable("id") long userId) {
        try {
            volunteerService.sendMessageFromUserWithOverdueDateOfReports(userId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(
            summary = "Добавление волонтера в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавленый волонтер",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Добавленый волонтер",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Сервис для работы с волонтерами"
    )
    @PostMapping(value = "/{id}")
    public ResponseEntity<Volunteer> postVolunteer(@PathVariable("id") long userId,
                                                          @RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.postVolunteer(userId, volunteer));
    }

    @Operation(
            summary = "Редактирование волонтера в базе данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый волонтер",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Редактируемый волонтер",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Сервис для работы с волонтерами"
    )
    @PutMapping(value = "/{id}")
    public Volunteer putVolunteer(@PathVariable long userId,
                                         @RequestBody Volunteer volunteer) {
        return volunteerService.putVolunteer(userId, volunteer);
    }

    @Operation(
            summary = "Удаление волонтера из базы данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Удаляемый волонтер",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            tags = "Сервис для работы с волонтерами"
    )
    @DeleteMapping(value = "/{id}")
    public void deleteVolunteer(@PathVariable("id") long userId) {
        volunteerService.deleteVolunteer(userId);
    }
}
