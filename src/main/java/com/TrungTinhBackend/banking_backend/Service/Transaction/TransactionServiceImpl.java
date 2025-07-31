package com.TrungTinhBackend.banking_backend.Service.Transaction;

import com.TrungTinhBackend.banking_backend.Entity.Account;
import com.TrungTinhBackend.banking_backend.Entity.Transaction;
import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Enum.TransactionStatus;
import com.TrungTinhBackend.banking_backend.Enum.TransactionType;
import com.TrungTinhBackend.banking_backend.Exception.NotFoundException;
import com.TrungTinhBackend.banking_backend.Repository.AccountRepository;
import com.TrungTinhBackend.banking_backend.Repository.TransactionRepository;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import com.TrungTinhBackend.banking_backend.RequestDTO.TransactionDTO;
import com.TrungTinhBackend.banking_backend.ResponseDTO.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public APIResponse deposit(TransactionDTO transactionDTO) {
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

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Deposit success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @Transactional
    public APIResponse withdraw(TransactionDTO transactionDTO) {
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

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Withdraw success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    @Transactional
    public APIResponse transfer(TransactionDTO transactionDTO, Authentication authentication) throws AccessDeniedException, IllegalAccessException {
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

        if(!accounts.contains(fromAccount)) {
            throw new AccessDeniedException("Bạn kho có quyền truy cập");
        }

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

        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Transfer success");
        apiResponse.setTimestamp(LocalDateTime.now());
        return apiResponse;
    }

    @Override
    public APIResponse getTransactionByPage(int page, int size) {
        return null;
    }

    @Override
    public APIResponse getTransactionById(Long id, Authentication authentication) {
        return null;
    }
}
