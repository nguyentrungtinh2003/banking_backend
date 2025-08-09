package com.TrungTinhBackend.banking_backend.Service.Transaction;

import com.TrungTinhBackend.banking_backend.Entity.Account;
import com.TrungTinhBackend.banking_backend.Entity.Transaction;
import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.LogAction;
import com.TrungTinhBackend.banking_backend.Enum.Role;
import com.TrungTinhBackend.banking_backend.Enum.TransactionStatus;
import com.TrungTinhBackend.banking_backend.Enum.TransactionType;
import com.TrungTinhBackend.banking_backend.Exception.NotFoundException;
import com.TrungTinhBackend.banking_backend.Repository.AccountRepository;
import com.TrungTinhBackend.banking_backend.Repository.TransactionRepository;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.RequestDTO.LogDTO;
import com.TrungTinhBackend.banking_backend.RequestDTO.TransactionDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import com.TrungTinhBackend.banking_backend.Service.Log.LogService;
import com.TrungTinhBackend.banking_backend.Service.Specification.Transaction.TransactionSpecification;
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
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogService logService;

    @Override
    @Transactional
    public APIResponse deposit(TransactionDTO transactionDTO, HttpServletRequest request, Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        Account account = accountRepository.findById(transactionDTO.getToAccountId()).orElseThrow(
                () -> new NotFoundException("Account not found")
        );

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        account.setBalance(account.getBalance() + transactionDTO.getAmount());
        accountRepository.save(account);

        transaction.setToAccount(account);

        transactionRepository.save(transaction);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.DEPOSIT,
                "UserId =  "+transactionDTO.getToAccountId()+ " increase amount = "+transactionDTO.getAmount(),
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Deposit success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @Transactional
    public APIResponse withdraw(TransactionDTO transactionDTO,HttpServletRequest request,Authentication authentication) {
        APIResponse apiResponse = new APIResponse();

        Account account = accountRepository.findById(transactionDTO.getToAccountId()).orElseThrow(
                () -> new NotFoundException("Account not found")
        );

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

       if(account.getBalance() < transactionDTO.getAmount()) {
           throw new RuntimeException("Bạn không đư số dư để rút");
       }
        account.setBalance(account.getBalance() - transactionDTO.getAmount());
        accountRepository.save(account);

        transaction.setToAccount(account);

        transactionRepository.save(transaction);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.WITHDRAW,
                "UserId =  "+transactionDTO.getFromAccountId()+ " decrease amount = "+transactionDTO.getAmount(),
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Withdraw success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @Transactional
    public APIResponse transfer(TransactionDTO transactionDTO, HttpServletRequest request,Authentication authentication) throws AccessDeniedException, IllegalAccessException {
        APIResponse apiResponse = new APIResponse();

        Account fromAccount = accountRepository.findById(transactionDTO.getFromAccountId()).orElseThrow(
                () -> new NotFoundException("Account not found")
        );

        Account toAccount = accountRepository.findById(transactionDTO.getToAccountId()).orElseThrow(
                () -> new NotFoundException("Account not found")
        );

        if(transactionDTO.getFromAccountId().equals(transactionDTO.getToAccountId())) {
            throw new IllegalAccessException("Bạn không thể chuyển tieền cho chính mình");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByCitizenId(userDetails.getUsername());
        List<Account> accounts = authUser.getAccounts();


        if(fromAccount.getBalance() < transactionDTO.getAmount()) {
            throw new RuntimeException("Bạn không đư số dư để chuyển");
        }
        fromAccount.setBalance(fromAccount.getBalance() - transactionDTO.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transactionDTO.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        LogDTO logDTO = new LogDTO(null,
                null,
                null,
                null,
                null,
                LogAction.TRANSFER,
                "From accountId = "+transactionDTO.getFromAccountId() + " to accountId = "+transactionDTO.getToAccountId()+" amount = "+transactionDTO.getAmount(),
                null,
                null,
                null,
                null);
        logService.addLog(logDTO,request,authentication);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Transfer success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse getTransactionByPage(int page, int size) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Transaction> transactions = transactionRepository.findAll(pageable);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get transaction by page "+page+" size "+size+" success");
        apiResponse.setData(transactions);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse filterTransaction(TransactionType type, TransactionStatus status, int page, int size) {
        APIResponse apiResponse = new APIResponse();

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Specification<Transaction> specification = TransactionSpecification.filterTransaction(type,status);
        Page<Transaction> transactions = transactionRepository.findAll(specification,pageable);

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Filter transaction success");
        apiResponse.setData(transactions);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse getTransactionById(Long id, Authentication authentication) throws AccessDeniedException {
        APIResponse apiResponse = new APIResponse();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authUser = userRepository.findByCitizenId(userDetails.getUsername());
        if(!authUser.getRole().equals(Role.ADMIN) && !authUser.getRole().equals(Role.EMPLOYEE)) {
            throw new AccessDeniedException("Bạn không có quyền truy cập");
        }

        Transaction transactions = transactionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Transaction not found")
        );

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Get transaction by id "+id+" success");
        apiResponse.setData(transactions);
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }
}
