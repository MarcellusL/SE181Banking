import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {
	CheckingAccount checkingAccount;

	@BeforeEach
	void setUp() {
		checkingAccount = new CheckingAccount("12345678", 7.9);
	}

	@Test
	void checking_account_starts_with_0() {
		double actual = checkingAccount.getBalance();

		assertEquals(0, actual);

	}

	@Test
	void deposit_money_into_checking_account() {
		checkingAccount.deposit(500);
		assertEquals(500, checkingAccount.getBalance());

	}

	@Test
	void account_wont_go_below_zero() {
		checkingAccount.deposit(100);
		checkingAccount.withdraw(200);
		assertEquals(0, checkingAccount.getBalance());
	}

	@Test
	void deposit_money_into_checking_account_twice() {
		checkingAccount.deposit(200);
		checkingAccount.deposit(150);
		assertEquals(350, checkingAccount.getBalance());
	}

	@Test
	void withdraw_money_from_checking_account() {
		checkingAccount.deposit(250);
		checkingAccount.withdraw(300);
		assertEquals(0.00, checkingAccount.getBalance());
	}

	@Test
	void withdraw_money_from_checking_twice() {
		checkingAccount.deposit(500);
		checkingAccount.withdraw(250);
		checkingAccount.withdraw(100);
		assertEquals(150, checkingAccount.getBalance());
	}

	@Test
	void supplied_apr_value() {

		assertEquals(7.9, checkingAccount.getAPR());
	}

}