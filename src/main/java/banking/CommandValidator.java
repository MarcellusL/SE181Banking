package banking;

public class CommandValidator {
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public Bank getBank() {
		return bank;
	}

	public boolean validate(String command) {
		String[] words = command.split(" ");
		String action = words[0].toLowerCase();

		switch (action) {
		case "create":
			return new ValidateCreateAccountCommand(bank).validate(words);

		case "deposit":
			return new ValidateDepositCommand(bank).validate(words);

		case "withdraw":
			return new ValidateWithdrawCommand(bank).validate(words);

		case "pass":
			return new ValidatePassTimeCommand(bank).validate(words);

		default:
			return false;
		}
	}
}