package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ACCOUNT_ID = "22384543";
	public static final String ACCOUNT_ID2 = "65153828";
	public static final String ACCOUNT_ID3 = "89456185";

	public static final double APR = 2.5;

	CheckingAccount checkingAccount1;
	CheckingAccount checkingAccount2;
	SavingsAccount savingsAccount1;

	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		checkingAccount1 = new CheckingAccount(ACCOUNT_ID, APR);
		checkingAccount2 = new CheckingAccount(ACCOUNT_ID2, APR);
		savingsAccount1 = new SavingsAccount(ACCOUNT_ID3, APR);
	}

	@Test
	void bank_initialized_with_no_accounts() {
		assertTrue(bank.getAccountHashMap().isEmpty());
	}

	@Test
	void add_account_to_the_bank() {
		bank.addAccount(checkingAccount1);
		assertEquals(1, bank.getAccountHashMap().size());

	}

	@Test
	void add_two_accounts_to_the_bank() {
		bank.addAccount(checkingAccount1);
		bank.addAccount(savingsAccount1);
		assertEquals(2, bank.getAccountHashMap().size());
	}

	@Test
	void retrieve_one_account_from_bank() {

		bank.addAccount(savingsAccount1);
		HashMap<String, Account> retrievedAccounts = bank.getAccountHashMap();
		assertEquals(savingsAccount1, retrievedAccounts.get(ACCOUNT_ID3));

	}

	@Test
	void deposit_money_into_account_by_id() {
		bank.addAccount(savingsAccount1);
		bank.depositById(ACCOUNT_ID3, 499);
		assertEquals(499, savingsAccount1.getBalance());
	}

	@Test
	void withdraw_money_from_account_by_id() {
		bank.addAccount(savingsAccount1);
		bank.depositById(ACCOUNT_ID3, 500);
		bank.withdrawById(ACCOUNT_ID3, 250);
		assertEquals(250, savingsAccount1.getBalance());
	}

	@Test
	void deposit_money_into_checking_account_twice_using_accound_id() {
		bank.addAccount(checkingAccount2);
		bank.depositById(ACCOUNT_ID2, 500);
		bank.depositById(ACCOUNT_ID2, 500);
		assertEquals(1000, checkingAccount2.getBalance());
	}

	@Test
	void withdraw_money_from_savings_account_twice_using_account_id() {
		bank.addAccount(savingsAccount1);
		bank.depositById(ACCOUNT_ID3, 666);
		bank.withdrawById(ACCOUNT_ID3, 333);
		bank.withdrawById(ACCOUNT_ID3, 1);
		assertEquals(332, savingsAccount1.getBalance());
	}

	// MISC: Checking if account ID getter is working
	@Test
	void testing_get_account_id() {
		bank.addAccount(checkingAccount1);
		Account retrievedAccounts = bank.getAccountUsingId(ACCOUNT_ID);
		assertEquals(checkingAccount1.getAccountID(), retrievedAccounts.getAccountID());

	}
}
