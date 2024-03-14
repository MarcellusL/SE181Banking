package banking;

public class CertificateDeposit extends Account {
	private static final int MONTH_WAITING_PERIOD = 12;
	private boolean withdrawalAllowed;

	public CertificateDeposit(String accountID, double APR, double balance) {
		super(accountID, APR);
		this.balance = balance;

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
	public double getAPR() {
		return super.getAPR();
	}

	@Override
	public boolean isCertificateDeposit() {
		return true;
	}

	@Override
	public boolean accountTypeWithdrawalAmount(double amount) {
		return withdrawalAllowed && amount >= balance;
	}

	@Override
	public double maxDepositAmount() {
		throw new UnsupportedOperationException("maxDepositAmount not supported for CertificateDeposit accounts.");
	}

	@Override
	public void passTime(int months) {
		if (months >= MONTH_WAITING_PERIOD) {
			withdrawalAllowed = true;
		}
	}

	// Created to test the pass time in CDTest
	public int getMonthWaitingPeriod() {
		return MONTH_WAITING_PERIOD;
	}
}
