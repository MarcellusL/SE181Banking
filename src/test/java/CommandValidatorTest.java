import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void create_savings_accounts_command() {
		String command = "create savings 12345678 0.6";
		assertTrue(commandValidator.validate(command));
	}

	@Test
	void create_invalid_command() {
		String command = "create checking IsleOfWight";
		assertFalse(commandValidator.validate(command));
	}
}
