package banking;

public class ValidateWithdrawCommand extends CommandValidator {
	public ValidateWithdrawCommand(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] commandWords) {
		if (commandWords.length != 3) {
			return false;
		}

		String accountId = commandWords[1];
		double withdrawAmount;

		try {
			withdrawAmount = Double.parseDouble(commandWords[2]);
		} catch (NumberFormatException e) {
			return false;
		}

		Account account = getBank().getAccountUsingId(accountId);
		if (account == null) {
			return false;
		}
		if (!account.accountTypeWithdrawalAmount(withdrawAmount)) {
			return false;
		}
		return true;
	}

}
