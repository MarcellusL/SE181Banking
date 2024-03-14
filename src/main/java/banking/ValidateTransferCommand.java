package banking;

public class ValidateTransferCommand extends CommandValidator {

	public ValidateTransferCommand(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] commandParts) {
		if (!commandParts[0].equalsIgnoreCase("transfer")) {
			System.out.println("Invalid transfer command. Usage: transfer <from ID> <to ID> <amount>");
			return false;
		}

		if (commandParts.length != 4) {
			System.out.println("Invalid transfer command. Usage: transfer <from ID> <to ID> <amount>");
			return false;
		}

		String fromAccountId = commandParts[1];
		String toAccountId = commandParts[2];
		double transferAmount;

		try {
			transferAmount = Double.parseDouble(commandParts[3]);
		} catch (NumberFormatException e) {
			return false;
		}

		Account fromAccount = getBank().getAccountUsingId(fromAccountId);
		Account toAccount = getBank().getAccountUsingId(toAccountId);

		if (fromAccount == null || toAccount == null) {
			System.out.println("Account not found for transfer");
			return false;
		}

		if (transferAmount < 0) {
			return false; // Cannot transfer a negative amount
		}

		// Check for if transfer amount exceeds balance
		if (transferAmount > fromAccount.getBalance()) {
			// If the transfer amount exceeds the balance, set it to zero
			transferAmount = fromAccount.getBalance();
		}

		if (!isValidAccountType(fromAccount, toAccount, transferAmount)) {
			return false;
		}

		return true;
	}

	private boolean isValidAccountType(Account fromAccount, Account toAccount, double amount) {
		if (fromAccount instanceof CertificateDeposit || toAccount instanceof CertificateDeposit) {
			return false; // CD accounts cannot be part of a transfer
		}

		if (fromAccount instanceof CheckingAccount && toAccount instanceof CheckingAccount) {
			return true;
		}

		if (fromAccount instanceof SavingsAccount && toAccount instanceof SavingsAccount) {
			return true;
		}

		if (fromAccount instanceof CheckingAccount && toAccount instanceof SavingsAccount) {
			return true;
		}

		return fromAccount instanceof SavingsAccount && toAccount instanceof CheckingAccount;
	}
}
