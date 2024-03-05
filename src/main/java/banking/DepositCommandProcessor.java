package banking;

public class DepositCommandProcessor implements CommandProcessorBase {
	private final Bank bank;

	public DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void process(String[] commandParts) {
		if (commandParts.length != 3) {
			throw new NumberFormatException("Invalid deposit command. Usage: deposit <accountID> <amount>");
		}

		String accountId = commandParts[1];
		double depositAmount;

		try {
			depositAmount = Double.parseDouble(commandParts[2]);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid deposit amount format.");
		}
		Account account = bank.getAccountUsingId(accountId);
		if (account == null) {
			throw new NullPointerException("banking.Account not found for deposit");
		}
		account.deposit(depositAmount);

		// System.out.println("Deposit successful. New Balance: " +
		// account.getBalance());
	}
}
