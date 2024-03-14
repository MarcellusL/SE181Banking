package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatePassTimeCommandTest {
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
	}

	@Test
	void valid_pass_time_command() {
		String[] commandWords = { "pass", "1" };
		assertTrue(validatePassTimeCommand.validate(commandWords));
	}

	@Test
	void invalid_pass_time_command_month_below_minimum() {
		String[] commandWords = { "pass", "-10" };
		assertFalse(validatePassTimeCommand.validate(commandWords));
	}

	@Test
	void invalid_pass_time_command_month_above_max() {
		String[] commandWords = { "pass", "72" };
		assertFalse(validatePassTimeCommand.validate(commandWords));
	}

	@Test
	void invalid_pass_time_command_missing_parameter() {
		String[] commandWords = { "", "1" };
		assertFalse(validatePassTimeCommand.validate(commandWords));
	}

	@Test
	void invalid_pass_time_command_missing_parameters() {
		String[] commandWords = { "", "" };
		assertFalse(validatePassTimeCommand.validate(commandWords));
	}

	@Test
	void invalid_pass_time_command_invalid_input_using_emoji() {
		String[] commandWords = { "😂", "1" };
		assertFalse(validatePassTimeCommand.validate(commandWords));
	}

	@Test
	void invalid_pass_time_command_extra_parameter() {
		String[] invalidMonths1 = { "pass", "0" };
		assertFalse(validatePassTimeCommand.validate(invalidMonths1));
	}

	@Test
	void invalid_pass_time_invalid_month() {
		String[] invalidMonths2 = { "pass", "100" };
		assertFalse(validatePassTimeCommand.validate(invalidMonths2));
	}

	@Test
	void invalid_pass_non_integer_for_months() {
		String[] invalidMonths3 = { "pass", "invalidMonth" };
		assertFalse(validatePassTimeCommand.validate(invalidMonths3));
	}

	@Test
	void invalid_pass_time_command_bank_not_initialized() {
		ValidatePassTimeCommand validatePassTimeCommand = new ValidatePassTimeCommand(null);
		String[] commandWords = { "pass", "12" };
		assertTrue(validatePassTimeCommand.validate(commandWords));
		assertThrows(IllegalStateException.class, () -> validatePassTimeCommand.executePassTime(12));
	}

}

// @Test
// void valid_pass_time_for_certificate_deposit() {
// bank.addAccount(certificateDeposit);
// PassTime.initialize(bank);
// PassTime.execute(12);
// assertEquals(2014.036792893758, certificateDeposit.getBalance());
// }
