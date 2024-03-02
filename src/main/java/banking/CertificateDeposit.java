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
	public double maxDepositAmount() {
		return 0;
	}
}
