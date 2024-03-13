package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	private Bank bank;
	private PassTimeCommandProcessor passTimeCommandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
	}

	@Test
	void valid_pass_time_command_for_savings() {
		bank.addAccount(new SavingsAccount("12345678", 3.0));
		bank.depositById("12345678", 1000);
		String[] commandParts = { "pass", "1" };
		assertDoesNotThrow(() -> passTimeCommandProcessor.process(commandParts));
		assertEquals(1002.5, bank.getAccountUsingId("12345678").getBalance());

	}

	@Test
	void valid_pass_time_command_for_checking() {
		bank.addAccount(new CheckingAccount("94012910", 3.0));
		bank.depositById("94012910", 1000);
		String[] commandParts = { "pass", "1" };
		assertDoesNotThrow(() -> passTimeCommandProcessor.process(commandParts));
		assertEquals(1002.5, bank.getAccountUsingId("94012910").getBalance());
	}

	@Test
	void valid_pass_time_command_for_cd() {
		bank.addAccount(new CertificateDeposit("98765432", 2.1, 2000));
		String[] commandParts = { "pass", "4" };
		assertDoesNotThrow(() -> passTimeCommandProcessor.process(commandParts));
		assertEquals(2014.036792893758, bank.getAccountUsingId("98765432").getBalance(), 0.0001);
	}

	@Test
	void invalid_pass_time_command_negative_months() {
		String[] commandParts = { "pass", "-5" };
		assertThrows(IllegalArgumentException.class, () -> passTimeCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_pass_time_command_zero_months() {
		String[] commandParts = { "pass", "0" };
		assertThrows(IllegalArgumentException.class, () -> passTimeCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_pass_time_command_over_max_months() {
		String[] commandParts = { "pass", "70" };
		assertThrows(IllegalArgumentException.class, () -> passTimeCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_pass_time_command_invalid_command() {
		String[] commandParts = { "passtime", "12" };
		assertThrows(IllegalArgumentException.class, () -> passTimeCommandProcessor.process(commandParts));
	}
}
