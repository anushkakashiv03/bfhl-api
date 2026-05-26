package com.bfhl.api.controller;

import com.bfhl.api.dto.BfhlRequest;
import com.bfhl.api.dto.BfhlResponse;
import com.bfhl.api.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    @Autowired
    private BfhlService bfhlService;

    @PostMapping
    public ResponseEntity<BfhlResponse> processBfhl(@RequestBody BfhlRequest request) {
        try {
            if (request == null || request.getData() == null) {
                BfhlResponse errorResponse = new BfhlResponse();
                errorResponse.setIs_success(false);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            BfhlResponse response = bfhlService.processRequest(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BfhlResponse errorResponse = new BfhlResponse();
            errorResponse.setIs_success(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<String> getOperationCode() {
        return ResponseEntity.ok("{\"operation_code\":1}");
    }
}
