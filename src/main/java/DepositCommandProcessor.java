public class DepositCommandProcessor implements CommandProcessorBase {
	private final Bank bank;

	public DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void process(String[] commandParts) {
		if (commandParts.length != 3) {
			System.out.println("Invalid deposit command. Usage: deposit <accountID> <amount>");
			return;
		}

		String accountId = commandParts[1];
		double depositAmount;

		try {
			depositAmount = Double.parseDouble(commandParts[2]);
		} catch (NumberFormatException e) {
			System.out.println("Invalid deposit amount format.");
			return;
		}
		Account account = bank.getAccountUsingId(accountId);
		if (account == null) {
			System.out.println("Account not found for deposit");
			return;
		}
		account.deposit(depositAmount);

		System.out.println("Deposit successful. New Balance: " + account.getBalance());
	}
}
