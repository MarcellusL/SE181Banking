package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private Bank bank;
	private CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);

	}

	@Test
	void create_checking_account() {
		commandProcessor.process("create checking 12345678 1.0");
		Account checkingAccount = bank.getAccountUsingId("12345678");
		assertEquals("12345678", checkingAccount.getAccountID());
		assertEquals(1.0, checkingAccount.getAPR());
	}

	@Test
	void create_savings_account() {
		commandProcessor.process("create savings 12345678 2.0");
		Account savingsAcc = bank.getAccountUsingId("12345678");
		assertEquals("12345678", savingsAcc.getAccountID());
		assertEquals(2.0, savingsAcc.getAPR());
	}

	@Test
	void create_certificate_deposits_account() {
		commandProcessor.process("create cd 87654321 2.0 1200");
		Account cdAcc = bank.getAccountUsingId("87654321");
		assertEquals("87654321", cdAcc.getAccountID());
		assertEquals(2.0, cdAcc.getAPR());
		assertEquals(1200.0, cdAcc.getBalance());
	}

	@Test
	void deposit_to_existing_account() {
		Account existingAccount = new SavingsAccount("98765432", 2.0);
		bank.addAccount(existingAccount);
		commandProcessor.process("deposit 98765432 100");
		assertEquals(100.0, existingAccount.getBalance());
	}

	@Test
	void deposit_into_savings_account() {
		commandProcessor.process("create savings 87654321 3.5");
		commandProcessor.process("deposit 87654321 200");

		Account savingsAcc = bank.getAccountUsingId("87654321");
		assertNotNull(savingsAcc);
		assertEquals(200.0, savingsAcc.getBalance());
	}

	@Test
	void deposit_into_checking_account() {
		commandProcessor.process("create checking 43214321 2.5");
		commandProcessor.process("deposit 43214321 150");

		Account checkingAcc = bank.getAccountUsingId("43214321");
		assertNotNull(checkingAcc);
		assertEquals(150.0, checkingAcc.getBalance());
	}

	@Test
	void deposit_into_checking_account_twice() {
		commandProcessor.process("create checking 43214321 1.5");
		commandProcessor.process("deposit 43214321 199");
		commandProcessor.process("deposit 43214321 100");

		Account checkingAcc = bank.getAccountUsingId("43214321");
		assertNotNull(checkingAcc);
		assertEquals(299, checkingAcc.getBalance());

	}

	@Test
	void deposit_into_savings_account_twice() {
		commandProcessor.process("create savings 12345678 0.50");
		commandProcessor.process("deposit 12345678 299");
		commandProcessor.process("deposit 12345678 100");

		Account savingsAcc = bank.getAccountUsingId("12345678");
		assertNotNull(savingsAcc);
		assertEquals(399, savingsAcc.getBalance());
	}

}
