package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateWithdrawCommandTest {
	CheckingAccount checkingAccount;
	SavingsAccount savingsAccount;
	private Bank bank;
	private ValidateWithdrawCommand validateWithdrawCommand;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		validateWithdrawCommand = new ValidateWithdrawCommand(bank);
		checkingAccount = new CheckingAccount("66699922", 3.5);
		savingsAccount = new SavingsAccount("22999666", 5.3);
	}

	@Test
	void valid_withdraw_from_checking_within_limit() {
		bank.addAccount(checkingAccount);
		checkingAccount.deposit(500);

		String[] commandWords = { "withdraw", "66699922", "200" };
		assertTrue(validateWithdrawCommand.validate(commandWords));
	}

	@Test
	void valid_withdraw_zero_from_checking() {
		bank.addAccount(checkingAccount);
		checkingAccount.deposit(666);
		String[] commandWords = { "withdraw", "66699922", "0" };
		assertTrue(validateWithdrawCommand.validate(commandWords));
	}

	@Test
	void valid_wtihdraw_zero_from_savings() {
		bank.addAccount(savingsAccount);
		savingsAccount.deposit(800);
		String[] commandWords = { "withdraw", "22999666", "0" };
		assertTrue(validateWithdrawCommand.validate(commandWords));
	}

	@Test
	void valid_withdraw_from_savings_within_limit() {
		bank.addAccount(savingsAccount);
		savingsAccount.deposit(1050);
		String[] commandWords = { "withdraw", "22999666", "350" };
		assertTrue(validateWithdrawCommand.validate(commandWords));
	}

	@Test
	void temporary_invalid_for_cd_since_pass_time_functionality_never_implemented() {
		CertificateDeposit certificateDeposit = new CertificateDeposit("77777778", 1.0, 1200);
		bank.addAccount(certificateDeposit);
		String[] commandWords = { "withdraw", "77777778", "400" };
		assertFalse(validateWithdrawCommand.validate(commandWords));
	}

	@Test
	void invalid_withdraw_from_savings_above_limit() {
		bank.addAccount(savingsAccount);
		savingsAccount.deposit(1500);
		String[] commandWords = { "withdraw", "22999666", "1200.99" };
		assertFalse(validateWithdrawCommand.validate(commandWords));
	}

	@Test
	void invalid_withdraw_from_checking_above_limit() {
		bank.addAccount(checkingAccount);
		checkingAccount.deposit(800);
		String[] commandWords = { "withdraw", "66699922", "500" };
		assertFalse(validateWithdrawCommand.validate(commandWords));
	}

	@Test
	void invalid_withdraw_negative_amount() {
		String[] commandWords = { "withdraw", "12345678", "-50" };
		assertFalse(validateWithdrawCommand.validate(commandWords));
	}

}
