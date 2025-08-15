package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("employee/log")
    public ResponseEntity<APIResponse> filterLog(@RequestParam(required = false)LogAction action,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(logService.filterLog(action,page,size));
    }
}
