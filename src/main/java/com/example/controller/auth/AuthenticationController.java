package com.example.controller.auth;

import com.example.constant.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.request.SignInRequestDTO;
import com.example.dtos.request.SignUpDTO;
import com.example.dtos.response.SignInResponseDTO;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Validated
@Slf4j
public class AuthenticationController {

    private final UserService authenticationService;


    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with the provided details.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = Message.ACCOUNT_SUCCESSFULLY_CREATED,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping(
            path = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<?> register(
            @RequestBody @Valid
            SignUpDTO request
    ) {
        authenticationService.signUp(request);
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.ACCOUNT_SUCCESSFULLY_CREATED
        );
    }

    @Operation(
            summary = "Login a user",
            description = "Authenticates a user and returns a JWT token for further requests.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = Message.LOGIN_SUCCESSFULLY,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid username or password",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping(
            path = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<SignInResponseDTO> login(
            @RequestBody @Valid
            SignInRequestDTO request
    ) {
        SignInResponseDTO response = authenticationService.signIn(request);
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.LOGIN_SUCCESSFULLY,
                response
        );
    }
}
