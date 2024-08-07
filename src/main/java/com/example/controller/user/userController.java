package com.example.controller.user;

import com.example.constant.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.response.UserDTO;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/")
@Validated
public class userController {

    private final UserService userService;

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a user by their unique ID.",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user to be retrieved",
                            required = true,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = Message.USER_RETRIEVED_SUCCESSFULLY,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(
            path = "{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<UserDTO> getUserById(
            @PathVariable
            @NotNull(message = Message.ID_IS_REQUIRED)
            long userId
    ) {
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.USER_RETRIEVED_SUCCESSFULLY,
                userService.getUserById(userId)
        );
    }
}
