import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateDepositCommandTest {
	CheckingAccount checkingAccount;
	SavingsAccount savingsAccount;
	CertificateDeposit certificateDeposit;
	private Bank bank;
	private ValidateDepositCommand validateDepositCommand;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		validateDepositCommand = new ValidateDepositCommand(bank);
		checkingAccount = new CheckingAccount("12049502", 2.0);
	}

	@Test
	void valid_deposit_into_checking() {
		checkingAccount = new CheckingAccount("12049502", 5);
		bank.addAccount(checkingAccount);
		String[] commandWords = { "deposit", "12049502", "500" };
		assertTrue(validateDepositCommand.validate(commandWords));
	}

	@Test
	void valid_deposit_into_savings() {
		savingsAccount = new SavingsAccount("20496045", 1.0);
		bank.addAccount(savingsAccount);
		String[] commandWords = { "deposit", "20496045", "650" };
		assertTrue(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_deposit_into_checking_miss_type_9_digit_id() {
		checkingAccount = new CheckingAccount("12049502", 2.0);
		bank.addAccount(checkingAccount);
		String[] commandWords = { "deposit", "120495023", "800" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_deposit_into_savings_over_max_deposit_limit() {
		savingsAccount = new SavingsAccount("20496045", 1.0);
		bank.addAccount(savingsAccount);
		String[] commandWords = { "deposit", "20496045", "3000" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_deposit_into_checking_over_max_deposit_limit() {
		checkingAccount = new CheckingAccount("12049502", 2.0);
		bank.addAccount(checkingAccount);
		String[] commandWords = { "deposit", "12049502", "1200" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_certificate_deposits_cannot_be_deposited_into() {
		certificateDeposit = new CertificateDeposit("30549684", 2.0, 1200);
		bank.addAccount(certificateDeposit);
		String[] commandWords = { "deposit", "30549684", "1000" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_no_checking_bank_id_given() {
		checkingAccount = new CheckingAccount("12049502", 2.0);
		bank.addAccount(checkingAccount);
		String[] commandWords = { "deposit", "", "500" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_no_amount_to_deposit_into_savings_given() {
		savingsAccount = new SavingsAccount("20496045", 1.0);
		bank.addAccount(savingsAccount);
		String[] commandWords = { "deposit", "20496045", "" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}
}
