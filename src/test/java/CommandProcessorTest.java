import org.junit.jupiter.api.Assertions;
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
		Assertions.assertEquals("12345678", checkingAccount.getAccountID());
		Assertions.assertEquals(1.0, checkingAccount.getAPR());
	}

	@Test
	void deposit_to_existing_account() {
		Account existingAccount = new SavingsAccount("98765432", 2.0);
		bank.addAccount(existingAccount);
		commandProcessor.process("deposit 98765432 100");
		Assertions.assertEquals(100.0, existingAccount.getBalance());
	}

	@Test
	void deposit_to_newly_created_account() {
		commandProcessor.process("create savings 87654321 3.5");
		commandProcessor.process("deposit 87654321 200");

		Account newlyCreatedAccount = bank.getAccountUsingId("87654321");
		Assertions.assertNotNull(newlyCreatedAccount);
		Assertions.assertEquals(200.0, newlyCreatedAccount.getBalance());
	}
}
