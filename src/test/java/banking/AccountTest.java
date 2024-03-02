package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	CheckingAccount checkingAccount;
	SavingsAccount savingsAccount;
	CertificateDeposit certificateDeposit;

	@BeforeEach
	void setUp() {
		checkingAccount = new CheckingAccount("12345678", 7.9);
		savingsAccount = new SavingsAccount("0000001", 5.7);
		certificateDeposit = new CertificateDeposit("1000000", .15, 1000);
	}

	@Test
	public void check_checking_account_apr_value() {
		double actual = checkingAccount.getAPR();
		assertEquals(7.9, actual);

	}

	@Test
	public void deposit_money_into_certificate_of_deposits() {
		certificateDeposit.deposit(700);
		double actual = certificateDeposit.getBalance();
		assertEquals(1700, actual);
	}

	@Test
	public void withdraw_money_from_certificate_of_deposits() {
		certificateDeposit.withdraw(300);
		double actual = certificateDeposit.getBalance();
		assertEquals(700, actual);
	}

	@Test
	public void savings_account_balance_cannot_go_below_zero() {
		savingsAccount.deposit(500);
		savingsAccount.withdraw(1599);
		double actual = savingsAccount.getBalance();
		assertEquals(0, actual);

	}
}
