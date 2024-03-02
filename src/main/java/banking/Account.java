package banking;

public abstract class Account {
	protected double balance;
	private double apr;
	private String accountID;

	public Account(String accountID, double APR) {
		this.accountID = accountID;
		this.apr = APR;
		this.balance = 0;
	}

	public void deposit(double amount) {

		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount > balance) {
			balance = 0.00;
		} else {
			balance -= amount;
		}
	}

	public double getBalance() {
		return balance;

	}

	protected double getAPR() {
		return apr;
	}

	public String getAccountID() {
		return accountID;
	}

	public abstract boolean isCertificateDeposit();

	public abstract double maxDepositAmount();
}
