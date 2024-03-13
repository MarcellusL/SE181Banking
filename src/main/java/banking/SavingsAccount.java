package banking;

public class SavingsAccount extends Account {
	private static final int MAX_WITHDRAWALS_PER_MONTH = 1;
	private int withdrawalsThisMonth;

	public SavingsAccount(String accountID, double APR) {
		super(accountID, APR);
	}

	@Override
	public double getBalance() {
		return super.getBalance();
	}

	@Override
	public void deposit(double amount) {
		super.deposit(amount);
	}

	@Override
	public void withdraw(double amount) {
		super.withdraw(amount);
	}

	@Override
	public boolean isCertificateDeposit() {
		return false;
	}

	@Override
	public boolean accountTypeWithdrawalAmount(double amount) {

		return amount <= 1000 && withdrawalsThisMonth < MAX_WITHDRAWALS_PER_MONTH;
	}

	@Override
	public double maxDepositAmount() {
		return 1000;
	}

	@Override
	public void passTime(int months) {
		// Nothing atm 🫤
	}
}
