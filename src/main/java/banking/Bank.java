package banking;

import java.util.HashMap;

public class Bank {

	private HashMap<String, Account> accountHashMap = new HashMap<>();

	public void addAccount(Account account) {

		accountHashMap.put(account.getAccountID(), account);
	}

	public HashMap<String, Account> getAccountHashMap() {
		return this.accountHashMap;
	}

	public Account getAccountUsingId(String accountID) {
		return accountHashMap.get(accountID);
	}

	public void depositById(String accountId, double depositAmount) {
		Account account = getAccountUsingId(accountId);
		if (account != null) {
			account.deposit(depositAmount);
		} else {
		}
	}

	public void withdrawById(String accountId, double withdrawAmount) {
		Account account = getAccountUsingId(accountId);
		if (account != null) {
			account.withdraw(withdrawAmount);
		}
	}
}
