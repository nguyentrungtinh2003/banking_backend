package com.TrungTinhBackend.banking_backend.Service.Account;

import com.TrungTinhBackend.banking_backend.Entity.Account;
import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import com.TrungTinhBackend.banking_backend.Exception.NotFoundException;
import com.TrungTinhBackend.banking_backend.Repository.AccountRepository;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.RequestDTO.AccountDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.LogDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Log.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogService logService;

    @Override
    public APIResponse addAccount(AccountDTO accountDTO, HttpServletRequest request, Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        User user = userRepository.findByCitizenId(accountDTO.getCitizenId());

        Account account = new Account();
        account.setBalance(0D);
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setDeleted(false);
        account.setUser(user);

        accountRepository.save(account);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Add account success");
        apiResponse.setTimestamp(LocalDateTime.now());

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.ADD,
                "Add account citizenId = "+accountDTO.getCitizenId(),
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        return apiResponse;
    }

    @Override
    public APIResponse getAccountByPage(int page, int size) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Account> accountPage = accountRepository.findAll(pageable);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get account page "+page+" size "+size+" success");
        apiResponse.setData(accountPage);
        apiResponse.setTimestamp(LocalDateTime.now());

        return apiResponse;
    }

    @Override
    public APIResponse getAccountById(Long id, Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByCitizenId(userDetails.getUsername());
        List<Account> accounts = authUser.getAccounts();

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Account not found")
        );
        if(!accounts.contains(account) && !(authUser.getRole().equals(Role.ADMIN) || authUser.getRole().equals(Role.EMPLOYEE))) {
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get account id "+id+" success");
        apiResponse.setData(account);
        apiResponse.setTimestamp(LocalDateTime.now());

        return apiResponse;
    }

    @Override
    public APIResponse updateAccount(Long id, AccountDTO accountDTO, HttpServletRequest request,Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByCitizenId(userDetails.getUsername());

        List<Account> accounts = authUser.getAccounts();
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Account not found")
        );

        if(!accounts.contains(account) && !(authUser.getRole().equals(Role.ADMIN) || authUser.getRole().equals(Role.EMPLOYEE))) {
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }

        if(accountDTO.getBalance() != null && !accountDTO.getBalance().isInfinite()) {
            account.setBalance(accountDTO.getBalance());
        }
        if(accountDTO.getAccountNumber() != null && !accountDTO.getAccountNumber().isEmpty()) {
            account.setAccountNumber(accountDTO.getAccountNumber());
        }
        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.UPDATE,
                "Update account id = "+id,
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Update account success");
        apiResponse.setTimestamp(LocalDateTime.now());

        return apiResponse;
    }

    @Override
    public APIResponse deleteAccount(Long id, HttpServletRequest request,Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByCitizenId(userDetails.getUsername());

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Account not found")
        );
        if(!(authUser.getRole().equals(Role.ADMIN) || authUser.getRole().equals(Role.EMPLOYEE))) {
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }

        if(!account.isDeleted()) {
            account.setDeleted(true);
            accountRepository.save(account);
            apiResponse.setMessage("Delete account id "+id+" success");
        }else {
            apiResponse.setMessage("Tài khoản id "+id+" đã xoá trước đó");
        }

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.DELETE,
                "Delete account id = "+id,
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setTimestamp(LocalDateTime.now());

        return apiResponse;
    }

    @Override
    public APIResponse restoreAccount(Long id, HttpServletRequest request,Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByCitizenId(userDetails.getUsername());

        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Account not found")
        );
        if(!(authUser.getRole().equals(Role.ADMIN) || authUser.getRole().equals(Role.EMPLOYEE))) {
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }

        if(account.isDeleted()) {
            account.setDeleted(false);
            accountRepository.save(account);
            apiResponse.setMessage("Restore account id "+id+" success");
        }else {
            apiResponse.setMessage("Tài khoản id "+id+" chưa bị xoá");
        }

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.RESTORE,
                "Restore account id = "+id,
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setTimestamp(LocalDateTime.now());

        return apiResponse;
    }
}
