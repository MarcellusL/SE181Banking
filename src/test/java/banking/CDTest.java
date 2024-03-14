package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {
	CertificateDeposit certificateDeposit;
	CheckingAccount checkingAccount;

	@BeforeEach
	void setUp() {
		certificateDeposit = new CertificateDeposit("10000000", .15, 1000);
	}

	@Test
	public void certificate_of_deposit_starting_balance() {
		// Balance will alawys initialize at zero for certificate of deposits
		assertEquals(1000, certificateDeposit.getBalance());
	}

	@Test
	public void deposit_money_into_certificate_of_deposit_twice() {
		certificateDeposit.deposit(500);
		certificateDeposit.deposit(500);
		double actual = certificateDeposit.getBalance();
		assertEquals(2000, actual);
	}

	@Test
	public void valid_cd_account() {
		CertificateDeposit certificateDeposit = new CertificateDeposit("54219999", 10, 21020);
		boolean booleanOfCD = certificateDeposit.isCertificateDeposit();
		assertTrue(booleanOfCD);
	}

	@Test
	public void waiting_period_expiration() {
		CertificateDeposit certificateDeposit = new CertificateDeposit("58432502", 0.15, 1000);
		certificateDeposit.passTime(certificateDeposit.getMonthWaitingPeriod());
		assertTrue(certificateDeposit.accountTypeWithdrawalAmount(1000));
	}

	@Test
	public void valid_withdrawal_after_waiting_period() {
		CertificateDeposit certificateDeposit = new CertificateDeposit("12345678", 0.15, 1000);
		certificateDeposit.passTime(certificateDeposit.getMonthWaitingPeriod() + 1);
		assertTrue(certificateDeposit.accountTypeWithdrawalAmount(1000));
	}

	@Test
	public void invalid_cd_account() {
		CheckingAccount checkingAccount = new CheckingAccount("50437777", 10);
		boolean booleanOfChecking = checkingAccount.isCertificateDeposit();
		assertFalse(booleanOfChecking);
	}

	@Test
	public void invalid_withdrawal_before_waiting_period() {
		CertificateDeposit certificateDeposit = new CertificateDeposit("12345678", 0.15, 1000);
		assertFalse(certificateDeposit.accountTypeWithdrawalAmount(1000)); // Withdrawal should not be allowed
	}

	@Test
	public void invalid_deposit_negative_amount() {
		CertificateDeposit certificateDeposit = new CertificateDeposit("10001000", 0.15, 1000);
		certificateDeposit.deposit(-500);
		assertEquals(1000, certificateDeposit.getBalance()); // Balance should remain unchanged
	}

	@Test
	public void invalid_withdrawal_negative_amount() {
		CertificateDeposit certificateDeposit = new CertificateDeposit("87654321", 0.15, 1000);
		assertFalse(certificateDeposit.accountTypeWithdrawalAmount(-500)); // Withdrawal should not be allowed
	}
}
