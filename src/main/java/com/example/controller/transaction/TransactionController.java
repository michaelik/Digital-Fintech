package com.example.controller.transaction;

import com.example.constant.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.request.TransferFundDTO;
import com.example.dtos.response.TransactionDTO;
import com.example.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;


    @Operation(
            summary = "Transfer funds between accounts",
            description = "Transfers funds from the authenticated user's account to another account.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing transfer details",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferFundDTO.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = Message.TRANSFER_COMPLETED,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping(
            path = "/transferFund"
    )
    public BaseResponse<?> transferFund(
            @RequestBody @Valid
            TransferFundDTO transfer
    ) {
        log.info("TransferFundDTO-> {}", transfer.amount());
        transactionService.transferFund(transfer);
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.TRANSFER_COMPLETED
        );
    }

    @Operation(
            summary = "Get transaction history",
            description = "Retrieves a paginated list of transactions for the authenticated user.",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "The page number to retrieve",
                            schema = @Schema(type = "integer", defaultValue = "0")
                    ),
                    @Parameter(
                            name = "size",
                            description = "The number of transactions per page",
                            schema = @Schema(type = "integer", defaultValue = "10")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = Message.TRANSACTION_HISTORY_RETRIEVED_SUCCESSFULLY,
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No transactions found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<Page<TransactionDTO>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.TRANSACTION_HISTORY_RETRIEVED_SUCCESSFULLY,
                transactionService.getTransactions(page, size)
        );
    }
}
