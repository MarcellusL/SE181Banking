package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidateTransferCommandTest {
	public static final String CHECKING_ACCOUNT_ID = "43216612";
	public static final String CHECKING_ACCOUNT_ID2 = "12345678";
	public static final String SAVINGS_ACCOUNT_ID = "66620199";
	public static final String SAVINGS_ACCOUNT_ID2 = "99102666";
	public static final String CD_ACCOUNT_ID = "12345983";
	public static final double DEPOSIT_AMOUNT = 1000;
	public static final double APR = 0.4;

	CheckingAccount checkingAccount;
	CheckingAccount checkingAccount2;
	SavingsAccount savingsAccount;
	SavingsAccount savingsAccount2;
	CertificateDeposit certificateDeposit;

	private Bank bank;
	private ValidateTransferCommand validateTransferCommand;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validateTransferCommand = new ValidateTransferCommand(bank);
		checkingAccount = new CheckingAccount(CHECKING_ACCOUNT_ID, APR);
		checkingAccount2 = new CheckingAccount(CHECKING_ACCOUNT_ID2, APR);
		savingsAccount = new SavingsAccount(SAVINGS_ACCOUNT_ID, APR);
		savingsAccount2 = new SavingsAccount(SAVINGS_ACCOUNT_ID2, APR);
		certificateDeposit = new CertificateDeposit(CD_ACCOUNT_ID, APR, DEPOSIT_AMOUNT);
		bank.addAccount(checkingAccount);
		bank.addAccount(checkingAccount2);
		bank.addAccount(savingsAccount);
		bank.addAccount(savingsAccount2);
		bank.addAccount(certificateDeposit);
	}

	@Test
	void valid_transfer_between_checking_accounts() {
		bank.depositById(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, CHECKING_ACCOUNT_ID2, "500" };
		assertTrue(validateTransferCommand.validate(commandParts));
	}

	@Test
	void valid_transfer_between_savings_accounts() {
		bank.depositById(SAVINGS_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID, SAVINGS_ACCOUNT_ID2, "300" };
		assertTrue(validateTransferCommand.validate(commandParts));
	}

	@Test
	void valid_transfer_from_checking_to_savings() {
		bank.depositById(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, SAVINGS_ACCOUNT_ID, "200" };
		assertTrue(validateTransferCommand.validate(commandParts));
	}

	@Test
	void valid_transfer_from_savings_to_checking() {
		bank.depositById(SAVINGS_ACCOUNT_ID, DEPOSIT_AMOUNT);

		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID, CHECKING_ACCOUNT_ID, "400" };
		assertTrue(validateTransferCommand.validate(commandParts));
	}

	@Test
	void valid_transfer_from_checking_to_savings_transfer_over_initial_balance() {
		bank.depositById(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, SAVINGS_ACCOUNT_ID, "2000" };
		assertTrue(validateTransferCommand.validate(commandParts));

	}

	@Test
	void valid_transfer_from_savings_to_checking_zero_amount_transfer() {
		bank.depositById(SAVINGS_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID, CHECKING_ACCOUNT_ID, "0" };
		assertTrue(validateTransferCommand.validate(commandParts));

	}

	@Test
	void invalid_transfer_from_cd_to_checking() {
		String[] commandParts = { "transfer", CD_ACCOUNT_ID, CHECKING_ACCOUNT_ID, "200" };
		assertFalse(validateTransferCommand.validate(commandParts));
	}

	@Test
	void invalid_transfer_from_checking_to_cd() {
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, CD_ACCOUNT_ID, "300" };
		assertFalse(validateTransferCommand.validate(commandParts));
	}

	@Test
	void invalid_transfer_from_checking_to_savings_negative_transfer() {
		bank.depositById(CHECKING_ACCOUNT_ID2, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID2, SAVINGS_ACCOUNT_ID, "-500" };
		assertFalse(validateTransferCommand.validate(commandParts));

	}

	@Test
	void invalid_transfer_command_incorrect_spelling_arg() {
		String[] commandParts = { "trangjfid", CHECKING_ACCOUNT_ID, SAVINGS_ACCOUNT_ID, "200" };
		assertFalse(validateTransferCommand.validate(commandParts));
	}

	@Test
	void invalid_transfer_command_non_existing_accounts_id() {
		String[] commandParts = { "transfer", "84583742", "87843758", "200" };
		assertFalse(validateTransferCommand.validate(commandParts));
	}

	@Test
	void invalid_transfer_command_existing_account_transfers_to_non_existing_account() {
		bank.depositById(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, "95435832", "500" };
		assertFalse(validateTransferCommand.validate(commandParts));
	}

	@Test
	void invalid_transfer_command_emoji_used_instead_of_transfer() {
		bank.depositById(CHECKING_ACCOUNT_ID2, DEPOSIT_AMOUNT);
		String[] commandParts = { "➡➡➡➡➡➡➡➡➡", CHECKING_ACCOUNT_ID2, CHECKING_ACCOUNT_ID, "238.50" };
		assertFalse(validateTransferCommand.validate(commandParts));
	}

	@Test
	void invalid_transfer_command_transfer_amount_left_blank() {
		bank.depositById(SAVINGS_ACCOUNT_ID2, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID2, SAVINGS_ACCOUNT_ID, "" };
		assertFalse(validateTransferCommand.validate(commandParts));
	}

}
