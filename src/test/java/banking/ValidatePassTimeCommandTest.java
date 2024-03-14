package banking;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatePassTimeCommandTest {
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	SavingsAccount savingsAccount;
	CheckingAccount checkingAccount;
	CertificateDeposit certificateDeposit;
	private Bank bank;
	private ValidatePassTimeCommand validatePassTimeCommand;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		checkingAccount = new CheckingAccount("47873001", 3.0);
		savingsAccount = new SavingsAccount("87437100", 3.0);
		certificateDeposit = new CertificateDeposit("98745632", 2.1, 2000);
		validatePassTimeCommand = new ValidatePassTimeCommand(bank);
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	void valid_pass_time_command() {
		String[] commandParts = { "pass", "1" };
		assertTrue(validatePassTimeCommand.validate(commandParts));
	}

	@Test
	void invalid_pass_time_command_month_below_minimum() {
		String[] commandParts = { "pass", "-10" };
		assertFalse(validatePassTimeCommand.validate(commandParts));
	}

	@Test
	void invalid_pass_time_command_month_above_max() {
		String[] commandParts = { "pass", "72" };
		assertFalse(validatePassTimeCommand.validate(commandParts));
	}

	@Test
	void invalid_pass_time_command_missing_parameter() {
		String[] commandParts = { "", "1" };
		assertFalse(validatePassTimeCommand.validate(commandParts));

		String printedMessage = outputStream.toString().trim();
		System.setOut(originalOut);
		assertEquals("Invalid command. Usage: pass <months>", printedMessage);
	}

	@Test
	void invalid_pass_time_command_missing_parameters() {
		String[] commandParts = { "", "" };
		assertFalse(validatePassTimeCommand.validate(commandParts));
	}

	@Test
	void invalid_pass_time_command_invalid_input_using_emoji() {
		String[] commandParts = { "😂", "1" };
		assertFalse(validatePassTimeCommand.validate(commandParts));

		String printedMessage = outputStream.toString().trim();
		System.setOut(originalOut);
		assertEquals("Invalid command. Usage: pass <months>", printedMessage);
	}

	@Test
	void invalid_pass_time_command_extra_parameter() {
		String[] commandParts = { "pass", "0" };
		assertFalse(validatePassTimeCommand.validate(commandParts));
	}

	@Test
	void invalid_pass_time_invalid_month() {
		String[] commandParts = { "pass", "100" };
		assertFalse(validatePassTimeCommand.validate(commandParts));

		String printedMessage = outputStream.toString().trim();
		System.setOut(originalOut);
		assertEquals("Invalid number of months. Please pass between 1 and 60 months.", printedMessage);
	}

	@Test
	void invalid_pass_non_integer_for_months() {
		String[] commandParts = { "pass", "invalidMonth" };
		assertFalse(validatePassTimeCommand.validate(commandParts));

		String printedMessage = outputStream.toString().trim();
		System.setOut(originalOut);
		assertEquals("Invalid number of months. Please pass between 1 and 60 months.", printedMessage);
	}

	@Test
	void invalid_pass_time_command_bank_not_initialized() {
		ValidatePassTimeCommand validatePassTimeCommand = new ValidatePassTimeCommand(null);
		String[] commandParts = { "pass", "12" };
		assertTrue(validatePassTimeCommand.validate(commandParts));
		assertThrows(IllegalStateException.class, () -> validatePassTimeCommand.executePassTime(12));
	}

	@Test
	void invalid_pass_time_month_is_float() {
		String[] commandParts = { "pass", "12.5" };
		assertFalse(validatePassTimeCommand.validate(commandParts));

	}

}

// @Test
// void valid_pass_time_for_certificate_deposit() {
// bank.addAccount(certificateDeposit);
// PassTime.initialize(bank);
// PassTime.execute(12);
// assertEquals(2014.036792893758, certificateDeposit.getBalance());
// }
