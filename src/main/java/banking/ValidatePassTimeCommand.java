package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidatePassTimeCommand extends CommandValidator {

	public ValidatePassTimeCommand(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] words) {
		if (words.length != 2 || !words[0].equalsIgnoreCase("pass")) {
			System.out.println("Invalid command. Usage: pass <months>");
			return false;
		}

		try {
			int months = Integer.parseInt(words[1]);
			if (months < 1 || months > 60) {
				System.out.println("Invalid number of months. Please pass between 1 and 60 months.");
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Invalid number of months. Please pass between 1 and 60 months.");
			return false;
		}
	}

	public void executePassTime(int months) {
		if (getBank() == null) {
			throw new IllegalStateException("Bank instance has not been initialized.");
		}

		HashMap<String, Account> accountHashMap = getBank().getAccountHashMap();
		List<String> accountsToRemove = new ArrayList<>();

		for (int i = 0; i < months; i++) {
			for (Account account : accountHashMap.values()) {
				if (account.getBalance() == 0) {
					accountsToRemove.add(account.getAccountID());
				} else if (account.getBalance() < 100) {
					account.withdraw(25);
				}
				account.accrueAPR();
			}
		}

		// Remove accounts with zero balance
		for (String accountId : accountsToRemove) {
			accountHashMap.remove(accountId);
		}
	}
}
