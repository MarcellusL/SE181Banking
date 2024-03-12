package banking;

import java.util.HashMap;

public class PassTime {
	private static Bank bank;

	public static void initialize(Bank bankInstance) {
		bank = bankInstance;
	}

	public static void execute(int months) {
		if (bank == null) {
			throw new IllegalStateException("Bank instance has not been initialized.");
		}

		HashMap<String, Account> accountHashMap = bank.getAccountHashMap();

		if (months < 1 || months > 60) {
			System.out.println("Invalid number of months. Please pass between 1 and 60 months.");
			return;
		}

		for (int i = 0; i < months; i++) {
			for (Account account : accountHashMap.values()) {
				if (account.getBalance() == 0) {
					accountHashMap.remove(account.getAccountID());
				} else if (account.getBalance() < 100) {
					account.withdraw(25);
				}
				account.accrueAPR();
			}
		}
	}
}
