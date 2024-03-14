package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {
	public static final String CHECKING_ACCOUNT_ID = "58345822";
	public static final String CHECKING_ACCOUNT_ID2 = "53954932";
	public static final String SAVINGS_ACCOUNT_ID = "43112349";
	public static final String SAVINGS_ACCOUNT_ID2 = "90385922";
	public static final String CD_ACCOUNT_ID = "87654321";
	public static final double DEPOSIT_AMOUNT = 1000;
	public static final double APR = 0.4;

	CheckingAccount checkingAccount;
	CheckingAccount checkingAccount2;
	SavingsAccount savingsAccount;
	SavingsAccount savingsAccount2;
	CertificateDeposit certificateDeposit;

	private TransferCommandProcessor transferCommandProcessor;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		transferCommandProcessor = new TransferCommandProcessor(bank);
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
	void valid_transfer_command_checking_to_checking() {
		bank.depositById(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, CHECKING_ACCOUNT_ID2, "500" };
		transferCommandProcessor.process(commandParts);

		assertEquals(500, checkingAccount.getBalance());
		assertEquals(500, checkingAccount2.getBalance());
	}

	@Test
	void valid_transfer_command_savings_to_savings() {
		bank.depositById(SAVINGS_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID, SAVINGS_ACCOUNT_ID2, "635.99" };
		transferCommandProcessor.process(commandParts);

		assertEquals(364.01, savingsAccount.getBalance());
		assertEquals(635.99, savingsAccount2.getBalance());

	}

	@Test
	void valid_transfer_command_checking_to_savings() {
		bank.depositById(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, SAVINGS_ACCOUNT_ID, "425.00" };
		transferCommandProcessor.process(commandParts);

		assertEquals(575, checkingAccount.getBalance());
		assertEquals(425, savingsAccount.getBalance());
	}

	@Test
	void valid_transfer_command_savings_to_checking() {
		bank.depositById(SAVINGS_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID, CHECKING_ACCOUNT_ID, "600" };
		transferCommandProcessor.process(commandParts);

		assertEquals(400, savingsAccount.getBalance());
		assertEquals(600, checkingAccount.getBalance());

	}

	@Test
	void valid_transfer_transfering_zero() {
		bank.depositById(CHECKING_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", CHECKING_ACCOUNT_ID, CHECKING_ACCOUNT_ID2, "0" };
		transferCommandProcessor.process(commandParts);

		assertEquals(1000, checkingAccount.getBalance());
		assertEquals(0, checkingAccount2.getBalance());
	}

	@Test
	void invalid_transfer_certificate_deposits_to_checking() {
		String[] commandParts = { "transfer", CD_ACCOUNT_ID, CHECKING_ACCOUNT_ID, "600" };
		assertThrows(IllegalArgumentException.class, () -> transferCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_transfer_negative_transfer_amount() {
		bank.depositById(SAVINGS_ACCOUNT_ID2, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID2, CHECKING_ACCOUNT_ID, "-250" };
		assertThrows(IllegalArgumentException.class, () -> transferCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_transfer_command_transfer_missspelled() {
		bank.depositById(CHECKING_ACCOUNT_ID2, DEPOSIT_AMOUNT);
		String[] commandParts = { "trnjnfer", SAVINGS_ACCOUNT_ID2, CHECKING_ACCOUNT_ID, "250" };
		assertThrows(IllegalArgumentException.class, () -> transferCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_transfer_command_emoji() {
		bank.depositById(SAVINGS_ACCOUNT_ID, DEPOSIT_AMOUNT);
		String[] commandParts = { "transfer", SAVINGS_ACCOUNT_ID2, CHECKING_ACCOUNT_ID, "💵💵💵💵💵💵💵💵" };
		assertThrows(IllegalArgumentException.class, () -> transferCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_transfer_command_blank_space() {
		bank.depositById(CHECKING_ACCOUNT_ID2, DEPOSIT_AMOUNT);
		String[] commandParts = { "", CHECKING_ACCOUNT_ID2, CHECKING_ACCOUNT_ID, "" };
		assertThrows(IllegalArgumentException.class, () -> transferCommandProcessor.process(commandParts));
	}

	@Test
	void invalid_transfer_accounts_do_not_exist() {
		String[] commandParts = { "transfer", "58743857", "12340003", "250" };
		assertThrows(IllegalArgumentException.class, () -> transferCommandProcessor.process(commandParts));
	}
}
