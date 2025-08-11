package com.TrungTinhBackend.banking_backend.Service.Log;

import com.TrungTinhBackend.banking_backend.Entity.Log;
import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.Repository.LogRepository;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.RequestDTO.LogDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Specification.Log.LogSpecification;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService{

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public APIResponse addLog(LogDTO logDTO, HttpServletRequest request, Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        Log log = new Log();

        if(authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User authUser = userRepository.findByCitizenId(userDetails.getUsername());
            log.setUser(authUser);
        }else {
            log.setUser(null);
        }

        log.setAction(logDTO.getAction());
        log.setDetails(logDTO.getDetails());
        log.setIpAddress(request.getRemoteAddr());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setCreatedAt(LocalDateTime.now());
        log.setUpdatedAt(LocalDateTime.now());

        logRepository.save(log);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Add log success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse getLogByPage(int page, int size, HttpServletRequest request, Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Log> logs = logRepository.findAll(pageable);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get log page "+page+" size "+size+" success");
        apiResponse.setData(logs);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse filterLog(LogAction action,int page, int size) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Specification<Log> specification = LogSpecification.filterLog(action);
        Page<Log> logs = logRepository.findAll(specification,pageable);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Filter log success");
        apiResponse.setData(logs);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }
}
