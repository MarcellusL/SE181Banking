public class CertificateDeposit extends Account {
	public CertificateDeposit(String accountID, double APR, double balance) {
		super(accountID, APR);
		this.balance = 1000;

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
}
