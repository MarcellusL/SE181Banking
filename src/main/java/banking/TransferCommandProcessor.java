package banking;

public class TransferCommandProcessor implements CommandProcessorBase {
	private final Bank bank;
	private final ValidateTransferCommand validateTransferCommand;

	public TransferCommandProcessor(Bank bank) {
		this.bank = bank;
		this.validateTransferCommand = new ValidateTransferCommand(bank);
	}

	@Override
	public void process(String[] commandParts) {
		if (!validateTransferCommand.validate(commandParts)) {
			throw new IllegalArgumentException("Invalid transfer command. Usage: transfer <from ID> <to ID> <amount>");
		}

		String fromAccountId = commandParts[1];
		String toAccountId = commandParts[2];
		double transferAmount = Double.parseDouble(commandParts[3]);

		Account fromAccount = bank.getAccountUsingId(fromAccountId);
		Account toAccount = bank.getAccountUsingId(toAccountId);

		if (fromAccount == null || toAccount == null) {
			throw new NullPointerException("Account not found");
		}

		if (transferAmount < 0) {
			// Cannot transfer a negative amount
			throw new IllegalArgumentException("Transfer amount must be positive");
		}
		// Check for if transfer amount exceeds balance
		if (transferAmount > fromAccount.getBalance()) {
			// If the transfer amount exceeds the balance, set it to zero
			transferAmount = fromAccount.getBalance();
		}

		fromAccount.withdraw(transferAmount);
		toAccount.deposit(transferAmount);
	}
}
