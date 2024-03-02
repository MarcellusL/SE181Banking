package banking;

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
		savingsAccount = new SavingsAccount("20496045", 1.0);
	}

	@Test
	void valid_deposit_into_checking() {
		bank.addAccount(checkingAccount);
		String[] commandWords = { "deposit", "12049502", "500" };
		assertTrue(validateDepositCommand.validate(commandWords));
	}

	@Test
	void valid_deposit_into_savings() {
		bank.addAccount(savingsAccount);
		String[] commandWords = { "deposit", "20496045", "650" };
		assertTrue(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_deposit_into_checking_miss_type_9_digit_id() {
		bank.addAccount(checkingAccount);
		String[] commandWords = { "deposit", "120495023", "800" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_deposit_into_savings_over_max_deposit_limit() {
		bank.addAccount(savingsAccount);
		String[] commandWords = { "deposit", "20496045", "3000" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_deposit_into_checking_over_max_deposit_limit() {
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
		bank.addAccount(checkingAccount);
		String[] commandWords = { "deposit", "", "500" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}

	@Test
	void invalid_no_amount_to_deposit_into_savings_given() {
		bank.addAccount(savingsAccount);
		String[] commandWords = { "deposit", "20496045", "" };
		assertFalse(validateDepositCommand.validate(commandWords));
	}
}
