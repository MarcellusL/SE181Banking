package banking;

public class PassTimeCommandProcessor implements CommandProcessorBase {
	private final Bank bank;

	public PassTimeCommandProcessor(Bank bank) {

		this.bank = bank;
	}

	@Override
	public void process(String[] commandParts) {
		if (commandParts.length != 2 || !commandParts[0].equalsIgnoreCase("pass")) {
			throw new IllegalArgumentException("Invalid pass time command. Usage: pass <months>");
		}

		int months;
		try {
			months = Integer.parseInt(commandParts[1]);
			if (months < 1 || months > 60) {
				throw new IllegalArgumentException("Invalid number of months. Please pass between 1 and 60 months.");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid number of months. Please pass between 1 and 60 months.");
		}

		// Execute pass time logic
		ValidatePassTimeCommand passTimeValidator = new ValidatePassTimeCommand(bank);
		if (passTimeValidator.validate(commandParts)) {
			passTimeValidator.executePassTime(months);
			System.out.println(months + " months passed.");
		}
	}
}
