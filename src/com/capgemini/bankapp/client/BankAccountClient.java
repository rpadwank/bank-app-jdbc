package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankapp.exception.AccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountClient {

	public static void main(String[] args) {
		int choice;
		long accountId;
		String accountHolderName;
		String accountType;
		double accountBalance;
		BankAccountService bankService = new BankAccountServiceImpl();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("1. Add New Bank Account\n2. Withdraw\n3. Deposit\n4. Fund Transfer");
				System.out.println("5. Delete Bank Account\n6. Display All BankAccount Details");
				System.out.println("7. Search Bank Account\n8. Check Balance");
				System.out.println("9. Update Bank Account details\n10. Exit\n");

				System.out.print("Please enter your choice = ");
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
				
				case 1: System.out.println("Enter account holder name:");
						accountHolderName = reader.readLine();
						System.out.println("Enter account type");
						accountType = reader.readLine();
						System.out.println("Enter account balance");
						accountBalance = Double.parseDouble(reader.readLine());
						
						BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);
						
						if(bankService.addNewBankAccount(account))
							System.out.println("Bank Account successfully created.");
						else
							System.out.println("Bank Account creation failed");
						break;
						
				case 2: System.out.println("Enter account id");
						accountId = Long.parseLong(reader.readLine()); 
					
						System.out.println("Enter amount: ");
						double amount = Double.parseDouble(reader.readLine());
						
						accountBalance = bankService.withdraw(accountId, amount);
						System.out.println("Balance : "+accountBalance);
						break;
						
				case 3: System.out.println("Enter account id");
						accountId = Long.parseLong(reader.readLine()); 
				
						System.out.println("Enter amount: ");
						amount = Double.parseDouble(reader.readLine());
				
						accountBalance = bankService.deposit(accountId, amount);
						System.out.println("Balance : "+accountBalance);
						break;
						
				case 4: System.out.println("Enter account id of sender: ");
						long fromAccountId = Long.parseLong(reader.readLine()); 	
						
						System.out.println("Enter account id of recepient: ");
						long toAccountId = Long.parseLong(reader.readLine()); 
		
						System.out.println("Enter amount: ");
						amount = Double.parseDouble(reader.readLine());
						
						accountBalance = bankService.fundTransfer(fromAccountId, toAccountId, amount);
						System.out.println("Balance : "+accountBalance);
						break;
						
				case 5: System.out.println("Enter account id: ");
						accountId = Long.parseLong(reader.readLine()); 
						if(bankService.deleteBankAccount(accountId))
							System.out.println("Account deletion successful");
						else
							System.out.println("Account deletion failed");
						break;
						
				case 6: List<BankAccount> accounts = new ArrayList<>();
						accounts = bankService.findAllBankAccounts();
						for (BankAccount bankAccount : accounts) {
							System.out.println(bankAccount);
						}
						break;
						
				case 7: System.out.println("Enter account id: ");
						accountId = Long.parseLong(reader.readLine());
						BankAccount bankAccount = bankService.searchAccount(accountId);
						System.out.println(bankAccount);
						break;
						
				case 8: System.out.println("Enter account id: ");
						accountId = Long.parseLong(reader.readLine());
						accountBalance = bankService.checkBalance(accountId);
						System.out.println(accountBalance);
						break;
				
				case 9: System.out.println("1. Account holder name\n2.Account type");
						choice = Integer.parseInt(reader.readLine());
						if(choice == 1)
						{
							System.out.println("Enter account id:");
							accountId = Long.parseLong(reader.readLine());
							System.out.println("Enter new name");
							accountHolderName = reader.readLine();
							if(bankService.updateAccountHolderName(accountId, accountHolderName))
								System.out.println("Name updated successfully");
							else
								System.out.println("Name updation failed");
						}
						else if(choice == 2)
						{
							System.out.println("Enter account id:");
							accountId = Long.parseLong(reader.readLine());
							System.out.println("Enter new type");
							accountType = reader.readLine();
							if(bankService.updateAccountType(accountId, accountType))
								System.out.println("Account type updated successfully");
							else
								System.out.println("Account type updation failed");
						}
						break;
				case 10: System.out.println("Thanks for banking with us.");
						System.exit(0);
						
				}
			}
		}
		
		catch(IOException e) {
			e.printStackTrace();
		} catch (LowBalanceException e) {
			System.out.println(e.getMessage());
		} catch (AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}
