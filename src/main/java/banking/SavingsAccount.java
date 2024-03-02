package banking;

public class SavingsAccount extends Account {

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
	public double maxDepositAmount() {
		return 2500;
	}
}
