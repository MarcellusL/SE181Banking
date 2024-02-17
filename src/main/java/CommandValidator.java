public class CommandValidator {
	private ValidateCreateAccountCommand validateCreateAccountCommand;
	private ValidateDepositCommand validateDepositCommand;

	public CommandValidator(Bank bank) {
		validateCreateAccountCommand = new ValidateCreateAccountCommand(bank);
		validateDepositCommand = new ValidateDepositCommand(bank);
	}

	public boolean validate(String command) {
		String[] words = command.split(" ");
		String action = words[0].toLowerCase();

		return validateCommand(action, words);
	}

	public boolean validateCommand(String action, String[] words) {
		switch (action) {
		case "create":
			return validateCreateAccountCommand.validate(words);

		case "deposit":
			return validateDepositCommand.validate(words);
		}
		return false;
	}
}
