package banking;

public class CreateAccountCommandProcessor implements CommandProcessorBase {
	private final Bank bank;

	public CreateAccountCommandProcessor(Bank bank) {

		this.bank = bank;
	}

	@Override
	public void process(String[] commandParts) {

		if (commandParts.length != 4 && commandParts.length != 5) {
			throw new NumberFormatException(
					"Invalid create command. Usage: create <accountType> <accountID> <APR> [initialAmount]");
		}

		String accountType = commandParts[1].toLowerCase();
		String accountId = commandParts[2];
		double apr;

		try {
			apr = Double.parseDouble(commandParts[3]);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid APR format.");
		}

		if (commandParts.length == 5) {
			double initialAmount;

			try {
				initialAmount = Double.parseDouble(commandParts[4]);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid initialAmount format.");
			}

			if (!isValidCDAmount(initialAmount)) {
				throw new NumberFormatException(
						"Invalid initialAmount. Should be between 1000 and 10000 for CD accounts.");
			}
		}

		Account account;
		switch (accountType) {
		case "checking":
			account = new CheckingAccount(accountId, apr);
			break;
		case "savings":
			account = new SavingsAccount(accountId, apr);
			break;
		case "cd":
			double initialAmount = Double.parseDouble(commandParts[4]);
			account = new CertificateDeposit(accountId, apr, initialAmount);
			break;
		default:
			throw new NullPointerException("Invalid account type: " + accountType);
		}

		bank.addAccount(account);
	}

	private boolean isValidCDAmount(double initialAmount) {
		return initialAmount >= 1000 && initialAmount <= 10000;
	}
}
