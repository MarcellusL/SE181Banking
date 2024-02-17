public class ValidateDepositCommand {
	private Bank bank;

	public ValidateDepositCommand(Bank bank) {
		this.bank = bank;
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

		Account account = bank.getAccountUsingId(accountId);
		if (account == null) {
			return false;
		}

		if (depositAmount < 0 || (account.isCertificateDeposit()) || (depositAmount > account.maxDepositAmount())) {
			return false;
		}

		return true;
	}

}
