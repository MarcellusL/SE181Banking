package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}

// @Test
// void valid_pass_time_for_certificate_deposit() {
// bank.addAccount(certificateDeposit);
// PassTime.initialize(bank);
// PassTime.execute(12);
// assertEquals(2014.036792893758, certificateDeposit.getBalance());
// }
