package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ValidCommandStorageTest {
	ValidCommandStorage commandStorage = new ValidCommandStorage();

	@Test
	void test_valid_create_command() {
		commandStorage.addValidCommand("Create savings 12345678 0.6");
		assertEquals(1, commandStorage.getValidCommands().size());
	}

	@Test
	void test_valid_deposit_command() {
		commandStorage.addValidCommand("Create savings 12345678 0.6");
		commandStorage.addValidCommand("Deposit 12345678 700");
		assertEquals(2, commandStorage.getValidCommands().size());
	}

	@Test
	void test_valid_withdraw_command() {
		commandStorage.addValidCommand("Create savings 12345678 0.6");
		commandStorage.addValidCommand("Deposit 12345678 700");
		commandStorage.addValidCommand("Withdraw 12345678 500");

		assertEquals(3, commandStorage.getValidCommands().size());
	}

}
