public class CheckingAccount extends Account {

	public CheckingAccount(String accountID, double APR) {
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
	public double getAPR() {

		return super.getAPR();
	}

	@Override
	public boolean isCertificateDeposit() {
		return false;
	}

	@Override
	public double maxDepositAmount() {
		return 1000;
	}
}
