package banking;

public class CertificateDeposit extends Account {
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
	// false is return because for CD has a special rule for withdrawals that will
	// need a
	// pass time function implementation.
	public boolean accountTypeWithdrawalAmount(double amount) {
		return false;
	}

	@Override
	public double maxDepositAmount() {
		throw new UnsupportedOperationException("maxDepositAmount not supported for CertificateDeposit accounts.");
	}
}
