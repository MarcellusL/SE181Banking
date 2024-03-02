package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsAccountsTest {

	SavingsAccount savingsAccount;

	@BeforeEach
	void setUp() {
		savingsAccount = new SavingsAccount("00000001", 5.7);
	}

	@Test
	void savings_account_starts_with_0() {
		double actual = savingsAccount.getBalance();
		assertEquals(0, actual);

	}

	@Test
	void deposit_money_into_savings_account() {
		savingsAccount.deposit(100);
		assertEquals(100, savingsAccount.getBalance());
	}

	@Test
	void deposit_money_into_savings_account_twice() {
		savingsAccount.deposit(300);
		savingsAccount.deposit(300);
		assertEquals(600, savingsAccount.getBalance());
	}

	@Test
	void withdraw_money_from_savings_account() {
		savingsAccount.deposit(50);
		savingsAccount.withdraw(25);
		assertEquals(25, savingsAccount.getBalance());

	}

	@Test
	void withdraw_money_from_checking_account_twice() {
		savingsAccount.deposit(666);
		savingsAccount.withdraw(333);
		savingsAccount.withdraw(1);
		assertEquals(332, savingsAccount.getBalance());

	}

}
