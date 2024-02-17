import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateCreateAccountCommandTest {
	private ValidateCreateAccountCommand createAccountValidator;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createAccountValidator = new ValidateCreateAccountCommand(bank);
	}

	@Test
	public void create_savings_account_command_is_valid() {
		String[] commandWords = { "createAccount", "savings", "12345678", "0.6" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	public void create_checking_account_is_valid() {
		String[] commandWords = { "createAccount", "checking", "22355679", "0.6" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void create_certificate_deposits_account_is_valid() {
		String[] commandWords = { "createAccount", "cd", "39504303", "0.4", "2000" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

}
