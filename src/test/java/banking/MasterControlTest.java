package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	Bank bank;

	private ArrayList<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 100", actual);

	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

	@Test
	void invalid_to_create_acocunts_with_same_ID() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create checking 12345678 1.0", actual);
	}

	@Test
	void deposit_into_cd_is_invalid() {
		input.add("create cd 12345678 1.2 2000");
		input.add("deposit 12345678 500");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("deposit 12345678 500", actual.get(0));
	}

	@Test
	void negative_deposit_into_checking_is_invalid() {
		input.add("create checking 12345678 1.0");
		input.add("deposit 12345678 -100");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("deposit 12345678 -100", actual.get(0));
	}

	@Test
	void negative_deposit_into_savings_is_invalid() {
		input.add("create savings 22345678 1.0");
		input.add("deposit 22345678 -100");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("deposit 22345678 -100", actual.get(0));

	}

	@Test
	void create_account_invalid_account_type() {
		input.add("create IRA 87654321 1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create IRA 87654321 1.0", actual);
	}

	@Test
	void create_checking_with_negative_apr_is_invalid() {
		input.add("create checking 22345679 -1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create checking 22345679 -1.0", actual);
	}

	@Test
	void create_savings_with_negative_apr_is_invalid() {
		input.add("create savings 12345675 -1.0");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create savings 12345675 -1.0", actual);
	}

	@Test
	void create_cd_with_negative_apr_is_invalid() {
		input.add("create cd 23456789 -2.0 1100");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create cd 23456789 -2.0 1100", actual);
	}

	@Test
	void create_command_with_emoji_is_invalid() {
		input.add("create savings 12345678 1.0 🚫");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("create savings 12345678 1.0 🚫", actual);
	}

	@Test
	void deposit_command_into_cd_with_emoji_is_invalid() {
		input.add("create cd 12345678 1.0 2000");
		input.add("deposit 12345678 500 🚫");

		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("deposit 12345678 500 🚫", actual.get(0));
	}

}
