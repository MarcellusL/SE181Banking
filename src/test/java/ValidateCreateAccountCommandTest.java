import static org.junit.jupiter.api.Assertions.assertFalse;
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
	void valid_savings_account_command() {
		String[] commandWords = { "createAccount", "savings", "12345678", "0.6" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void valid_checking_account_command() {
		String[] commandWords = { "createAccount", "checking", "22355679", "0.6" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void valid_certificate_deposits_account_command() {
		String[] commandWords = { "createAccount", "cd", "39504303", "0.4", "2000" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_missing_account_id() {
		String[] commandWords = { "createAccount", "savings", "", "2.0" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_apr_out_of_range() {
		String[] commandWords = { "createAccount", "checking", "15" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_savings_account_missing_apr() {
		String[] commandWords = { "createAccount", "savings", "12345678" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_apr_below_0() {
		String[] commandWords = { "createAccount", "checking", "20496047", "-1.0" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_cd_account_initial_balance_below_1000() {
		String[] commandWords = { "createAccount", "cd", "30549684", "5.6", "500" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_id_more_than_8() {
		String[] commandWords = { "createAccount", "checking", "120495023", "3.0" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_savings_account_id_less_than_8() {
		String[] commandWords = { "createAccount", "savings", "12369" };
		assertFalse(createAccountValidator.validate(commandWords));
	}
}
