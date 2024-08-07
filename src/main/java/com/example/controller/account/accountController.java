package com.example.controller.account;

import com.example.constant.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.request.FundAccountDTO;
import com.example.dtos.response.AccountDTO;
import com.example.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account/")
@Validated
@Slf4j
public class accountController {

    private final AccountService accountService;

    @Operation(
            summary = "Get account by user ID",
            description = "Retrieves the account details associated with the given user ID.",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user whose account is to be retrieved",
                            required = true,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = Message.ACCOUNT_RETRIEVED_SUCCESSFULLY,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(
            path = "{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<AccountDTO> getAccountByUserId(
            @PathVariable
            @NotNull(message = Message.ID_IS_REQUIRED)
            long userId
    ) {
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.ACCOUNT_RETRIEVED_SUCCESSFULLY,
                accountService.getAccountByUserId(userId)
        );
    }

    @Operation(
            summary = "Fund account",
            description = "Funds the account associated with the given user ID with the specified amount.",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user whose account is to be funded",
                            required = true,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing the amount to fund the account",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FundAccountDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = Message.ACCOUNT_FUNDED_SUCCESSFULLY,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping(
            path = "{userId}",
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<?> fundAccount(
            @PathVariable
            @NotNull(message = Message.ID_IS_REQUIRED)
            long userId,
            @RequestBody @Valid
            FundAccountDTO request
    ) {
        accountService.fundAccount(userId, request);
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.ACCOUNT_FUNDED_SUCCESSFULLY
        );
    }
}
