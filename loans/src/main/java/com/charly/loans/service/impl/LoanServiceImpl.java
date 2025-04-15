package com.charly.loans.service.impl;

import com.charly.loans.constant.LoanConstant;
import com.charly.loans.dto.LoanDto;
import com.charly.loans.entity.Loan;
import com.charly.loans.exception.LoanAlreadyExistsException;
import com.charly.loans.exception.ResourceNotFoundException;
import com.charly.loans.mapper.LoanMapper;
import com.charly.loans.repository.LoanRepository;
import com.charly.loans.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoans = loanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstant.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstant.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstant.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoanDto getLoan(String mobileNumber) {
        Loan loans = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        return LoanMapper.mapToLoansDto(loans, new LoanDto());
    }

    @Override
    public boolean updateLoan(LoanDto loansDto) {
        Loan loans = loanRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(() -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoanMapper.mapToLoans(loansDto, loans);
        loanRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loans = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        loanRepository.deleteById(loans.getLoanId());
        return true;
    }
}
