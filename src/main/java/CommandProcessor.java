public class CommandProcessor {
	private Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String commandInfo) {
		String[] commandParts = commandInfo.split(" ");
		String action = commandParts[0].toLowerCase();

		switch (action) {

		case "create":
			new CreateAccountCommandProcessor(bank).process(commandParts);
			break;

		case "deposit":
			new DepositCommandProcessor(bank).process(commandParts);
			break;

		default:
			throw new UnsupportedOperationException("Unsupported Command: " + action);
		}
	}
}
