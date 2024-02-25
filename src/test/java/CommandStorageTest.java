import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	CommandStorage commandStorage = new CommandStorage();

	@Test
	void create_one_invalid_command() {
		commandStorage.addInvalidCommand("create 12049432 1.0");
		assertEquals(1, commandStorage.getInvalidCommands().size());
	}

	@Test
	void create_two_invalid_commands() {
		commandStorage.addInvalidCommand("crata checking 12345678 2.0");
		commandStorage.addInvalidCommand("create ceckng 94832321 1.5");

		assertEquals(2, commandStorage.getInvalidCommands().size());
	}

	@Test
	void empty_strings_in_command() {
		commandStorage.addInvalidCommand("create savings 1.0");
		commandStorage.addInvalidCommand("deposit 12345678");
		commandStorage.addInvalidCommand("create cd 87654321");
		commandStorage.addInvalidCommand("deposit 12345678");
		commandStorage.addInvalidCommand("deposit 100");
		assertEquals(5, commandStorage.getInvalidCommands().size());
	}

	@Test
	void invalid_create_account_commands() {
		commandStorage.addInvalidCommand("crata checking 12345678 1.0");
		commandStorage.addInvalidCommand("create ceckng 12345678 1.0");
		commandStorage.addInvalidCommand("create checking 1234 1.0");
		commandStorage.addInvalidCommand("create checking 12345678 -90.0");
		commandStorage.addInvalidCommand("create savn 87654321 1.0");
		commandStorage.addInvalidCommand("create savings 6666 2.5");
		commandStorage.addInvalidCommand("create certificate deposit 12366991 2.0 1000");
		commandStorage.addInvalidCommand("create cd 43214321 1.9");
		assertEquals(8, commandStorage.getInvalidCommands().size());
	}

	@Test
	void invalid_deposit_commands() {
		commandStorage.addInvalidCommand("deposit 100");
		commandStorage.addInvalidCommand("deposit 12345678");
		commandStorage.addInvalidCommand("deposit 12345678 -100");
		commandStorage.addInvalidCommand("deposit 123456789 100");
		assertEquals(4, commandStorage.getInvalidCommands().size());

	}
}
