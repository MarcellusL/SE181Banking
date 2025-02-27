package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateAccountCommandProcessorTest {

	CreateAccountCommandProcessor createAccountCommandProcessor;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		createAccountCommandProcessor = new CreateAccountCommandProcessor(bank);
	}

	@Test
	void create_checking_account_is_valid() {
		String[] commandParts = { "create", "checking", "12345678", "1.0" };
		createAccountCommandProcessor.process(commandParts);

		Account account = bank.getAccountUsingId("12345678");
		assertTrue(account instanceof CheckingAccount);
	}

	@Test
	void create_savings_account_is_valid() {
		String[] commandParts = { "create", "savings", "98765432", "0.5" };
		createAccountCommandProcessor.process(commandParts);

		Account account = bank.getAccountUsingId("98765432");
		assertTrue(account instanceof SavingsAccount);
	}

	@Test
	void create_cd_account_is_valid() {
		String[] commandParts = { "create", "cd", "11112222", "2.0", "5000" };
		createAccountCommandProcessor.process(commandParts);

		Account account = bank.getAccountUsingId("11112222");
		assertTrue(account instanceof CertificateDeposit);
	}

	@Test
	void create_command_missing_parameters_is_invalid() {
		String[] commandParts = { "create", "checking", "12345678" };

		// Account account = bank.getAccountUsingId("12345678");
		assertThrows(NumberFormatException.class, () -> createAccountCommandProcessor.process(commandParts));
	}

	@Test
	void create_command_invalid_apr_format_is_invalid() {
		String[] commandParts = { "create", "savings", "98765432", "invalidAPR" };

		// Account account = bank.getAccountUsingId("98765432");
		assertThrows(NumberFormatException.class, () -> createAccountCommandProcessor.process(commandParts));
	}

	@Test
	void create_command_invalid_initial_amount_for_cd_is_invalid() {
		String[] commandParts = { "create", "cd", "11112222", "2.0", "500" };

		// Account account = bank.getAccountUsingId("11112222");
		assertThrows(NumberFormatException.class, () -> createAccountCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_account_type_throws_exception() {
		String[] commandParts = { "create", "MARCUS", "11112202", "2.0", "500" };
		assertThrows(NumberFormatException.class, () -> createAccountCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_cd_amount() {
		assertFalse(createAccountCommandProcessor.isValidCDAmount(12500));
	}

	@Test
	void valid_cd_amount() {
		assertTrue(createAccountCommandProcessor.isValidCDAmount(5000));
	}

	@Test
	void valid_cd_amount_equal_to_1000() {
		assertTrue(createAccountCommandProcessor.isValidCDAmount(1000));
	}
}
