package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeTest {
	Bank bank = new Bank();
	SavingsAccount savingsAccount;
	CheckingAccount checkingAccount;
	CertificateDeposit certificateDeposit;

	public static void printBalances(Bank bank) {
		for (Account account : bank.getAccountHashMap().values()) {
			System.out.println("Account ID: " + account.getAccountID() + ", Balance: $" + account.getBalance());
		}
	}

	@BeforeEach
	void setUp() {
		checkingAccount = new CheckingAccount("47873001", 3.0);
		savingsAccount = new SavingsAccount("87437100", 3.0);
		certificateDeposit = new CertificateDeposit("98745632", 2.1, 2000);
	}

	@Test
	void valid_pass_time_for_checking() {
		bank.addAccount(checkingAccount);
		bank.depositById("47873001", 1000);
		PassTime.initialize(bank);
		PassTime.execute(1);

		assertEquals(1002.50, checkingAccount.getBalance());
	}

	@Test
	void valid_pass_time_for_savings() {
		bank.addAccount(savingsAccount);
		bank.depositById("87437100", 1000);
		PassTime.initialize(bank);
		PassTime.execute(1);
		assertEquals(1002.50, savingsAccount.getBalance());
	}

	// @Test
	// void valid_pass_time_for_certificate_deposit() {
	// bank.addAccount(certificateDeposit);
	// PassTime.initialize(bank);
	// PassTime.execute(12);
	// assertEquals(2014.036792893758, certificateDeposit.getBalance());
	// }

}
