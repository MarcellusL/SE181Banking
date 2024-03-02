package banking;

public class ValidateDepositCommand extends CommandValidator {
	// private banking.Bank bank;

	public ValidateDepositCommand(Bank bank) {

		super(bank);
	}

	public boolean validate(String[] commandWords) {
		if (commandWords.length != 3) {
			return false;
		}

		String accountId = commandWords[1];
		double depositAmount;

		try {
			depositAmount = Double.parseDouble(commandWords[2]);
		} catch (NumberFormatException e) {
			return false;
		}

		Account account = getBank().getAccountUsingId(accountId);
		if (account == null) {
			return false;
		}

		if (depositAmount < 0 || (account.isCertificateDeposit()) || (depositAmount > account.maxDepositAmount())) {
			return false;
		}

		return true;
	}

}
