import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandProcessorTest {

	DepositCommandProcessor depositCommandProcessor;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		depositCommandProcessor = new DepositCommandProcessor(bank);
	}

	@Test
	void valid_deposit_command() {
		Account account = new CheckingAccount("12345678", 0.01);
		bank.addAccount(account);

		String[] commandParts = { "deposit", "12345678", "100" };
		depositCommandProcessor.process(commandParts);

		assertEquals(100, account.getBalance(), "Account should have a balance of $100");
	}

	@Test
	void invalid_deposit_command_invalid_amount_format() {
		Account account = new CheckingAccount("12345678", 0.01);
		bank.addAccount(account);

		String[] commandParts = { "deposit", "12345678", "invalidAmount" };
		assertThrows(NumberFormatException.class, () -> depositCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_deposit_command_account_not_found() {
		DepositCommandProcessor processor = new DepositCommandProcessor(bank);
		String[] commandParts = { "deposit", "12345678", "100" };

		assertThrows(NullPointerException.class, () -> processor.process(commandParts));
	}

}
