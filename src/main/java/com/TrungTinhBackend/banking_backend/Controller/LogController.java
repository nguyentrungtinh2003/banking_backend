package com.TrungTinhBackend.banking_backend.Controller;

import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Log.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("employee/log/page")
    public ResponseEntity<APIResponse> getLogByPage(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "6") int size,
                                                    HttpServletRequest request,
                                                    Authentication authentication) {
        return ResponseEntity.ok(logService.getLogByPage(page,size,request,authentication));
    }
}
