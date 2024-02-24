public class ValidateCreateAccountCommand extends CommandValidator {

	// private Bank bank;

	public ValidateCreateAccountCommand(Bank bank) {

		super(bank);
	}

	public boolean validate(String[] commandWords) {
		if (commandWords.length < 4) {
			return false;
		}

		String accountType = commandWords[1].toLowerCase();

		switch (accountType) {
		case "checking":
		case "savings":
			return validateCheckingOrSavings(commandWords);

		case "cd":
			return validateCertificateDeposit(commandWords);

		default:
			return false;
		}
	}

	private boolean validateCheckingOrSavings(String[] commandWords) {
		if (commandWords.length != 4) {
			return false;
		}

		String accountId = commandWords[2];
		double apr;

		try {
			apr = Double.parseDouble(commandWords[3]);
		} catch (NumberFormatException e) {
			return false;
		}

		return getBank().getAccountUsingId(accountId) == null && isValidAccountId(accountId) && isValidAPR(apr);
	}

	private boolean validateCertificateDeposit(String[] commandWords) {
		if (commandWords.length != 5) {
			return false;
		}
		String accountId = commandWords[2];
		double apr;
		double initialAmount;

		try {
			apr = Double.parseDouble(commandWords[3]);
			initialAmount = Double.parseDouble(commandWords[4]);
		} catch (NumberFormatException e) {
			return false;
		}

		return getBank().getAccountUsingId(accountId) == null && isValidAccountId(accountId) && isValidAPR(apr)
				&& isValidCDAmount(initialAmount);
	}

	private boolean isValidAccountId(String accountId) {
		return accountId.matches("\\d{8}");
	}

	private boolean isValidAPR(double apr) {
		return apr >= 0 && apr <= 10;
	}

	private boolean isValidCDAmount(double initialAmount) {
		return initialAmount >= 1000 && initialAmount <= 10000;
	}
}
