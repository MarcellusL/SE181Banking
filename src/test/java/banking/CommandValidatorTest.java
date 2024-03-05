package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	CheckingAccount checkingAccount;
	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void valid_savings_accounts_command() {
		String command = "create savings 12345678 0.6";
		assertTrue(commandValidator.validate(command));
	}

	@Test
	void create_invalid_command() {
		String command = "create checking IsleOfWight";
		assertFalse(commandValidator.validate(command));
	}

	@Test
	void valid_certificate_deposit_accounts_command() {
		String command = "create cd 43214321 3.69 1069";
		assertTrue(commandValidator.validate(command));
	}

	@Test
	void valid_deposit_command() {
		checkingAccount = new CheckingAccount("12345676", 2.0);
		bank.addAccount(checkingAccount);
		String command = "deposit 12345676 100";
		assertTrue(commandValidator.validate(command));
	}

	@Test
	void invalid_deposit_command_more_than_three_inputs() {
		checkingAccount = new CheckingAccount("87654321", 1.5);
		bank.addAccount(checkingAccount);
		String command = "deposit 87654321 100 200";
		assertFalse(commandValidator.validate(command));
	}
}
