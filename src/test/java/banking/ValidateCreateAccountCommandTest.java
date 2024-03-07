package banking;

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
		String[] commandWords = { "create", "savings", "12345678", "0.6" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void valid_checking_account_command() {
		String[] commandWords = { "create", "checking", "22355679", "0.6" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void valid_certificate_deposits_account_command() {
		String[] commandWords = { "create", "cd", "39504303", "0.4", "2000" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void valid_cd_account_min_amount() {
		String[] commandWords = { "create", "cd", "39514324", "0.4", "1000" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void valid_cd_account_max_amount() {
		String[] commandWords = { "create", "cd", "48504313", "0.4", "10000" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_missing_account_id() {
		String[] commandWords = { "create", "savings", "", "2.0" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_apr_out_of_range() {
		String[] commandWords = { "create", "checking", "15" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_savings_account_missing_apr() {
		String[] commandWords = { "create", "savings", "12345678" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_apr_below_0() {
		String[] commandWords = { "create", "checking", "20496047", "-1.0" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_cd_account_initial_balance_below_1000() {
		String[] commandWords = { "create", "cd", "30549684", "5.6", "500" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_id_more_than_8_digits() {
		String[] commandWords = { "create", "checking", "120495023", "3.0" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_savings_account_id_less_than_8_digits() {
		String[] commandWords = { "create", "savings", "12369" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_apr_above_max_limit() {
		String[] commandWords = { "create", "checking", "19496092", "21.6" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_cd_amount_above_max_limit() {
		String[] commandWords = { "create", "cd", "21949684", "5.6", "10001" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_cd_apr_and_amount() {
		String[] commandWords = { "create", "cd", "62123592", "0.001", "10.1" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_checking_account_invalid_apr() {
		String[] commandWords = { "create", "checking", "22355619", "invalid" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_cd_account_invalid_apr() {
		String[] commandWords = { "create", "cd", "39504301", "invalid", "2000" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_cd_account_invalid_initial_amount() {
		String[] commandWords = { "create", "cd", "29514303", "0.4", "invalid" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void valid_checking_account_max_apr() {
		String[] commandWords = { "create", "checking", "22389679", "10" };
		assertTrue(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_apr_above_max_limit_in_checking() {
		String[] commandWords = { "create", "checking", "17862678", "15.0" };
		assertFalse(createAccountValidator.validate(commandWords));
	}

	@Test
	void invalid_apr_above_max_limit_in_certificate_deposit() {
		String[] commandWords = { "create", "cd", "17862679", "15.0", "2000" };
		assertFalse(createAccountValidator.validate(commandWords));
	}
//
}
