package com.capgemini.bankapp.service.impl;

import java.util.List;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.AccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

	BankAccountDao bankAccountDao;
	
	public BankAccountServiceImpl()
	{
		bankAccountDao = new BankAccountDaoImpl();
	}
	
	
	@Override
	public double checkBalance(long accountId) {
		return bankAccountDao.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException {
		double balance = bankAccountDao.getBalance(accountId);
		if(balance-amount>=0)
		{
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
		}
		else
			throw new LowBalanceException("You have insufficient fund...");
		return balance;
	}

	@Override
	public double deposit(long accountId, double amount) {
		double balance = bankAccountDao.getBalance(accountId);
		balance = balance + amount;
		bankAccountDao.updateBalance(accountId, balance);
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		if(bankAccountDao.deleteBankAccount(accountId))
			return true;
		return false;
	}


	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException {
		double balance = withdraw(fromAccount, amount);
		deposit(toAccount, amount);
		return balance;
	}


	@Override
	public boolean addNewBankAccount(BankAccount account) {
		return bankAccountDao.addNewBankAccount(account);
	}


	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankAccountDao.findAllBankAccounts();
	}


	@Override
	public BankAccount searchAccount(long accountId) throws AccountNotFoundException {
		return bankAccountDao.searchAccount(accountId);
	}


	@Override
	public boolean updateAccountHolderName(long accountId, String accountHolderName) {
		return bankAccountDao.updateAccountHolderName(accountId, accountHolderName);
		
	}


	@Override
	public boolean updateAccountType(long accountId, String accountType) {
		return bankAccountDao.updateAccountType(accountId, accountType);
		
	}

}
