import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {
	CertificateDeposit certificateDeposit;

	@BeforeEach
	void setUp() {
		certificateDeposit = new CertificateDeposit("10000000", .15, 1000);
	}

	@Test
	public void certificate_of_deposit_starting_balance() {
		// Balance will alawys initialize at zero for certificate of deposits
		assertEquals(1000, certificateDeposit.getBalance());
	}

	@Test
	public void deposit_money_into_certificate_of_deposit_twice() {
		certificateDeposit.deposit(500);
		certificateDeposit.deposit(500);
		double actual = certificateDeposit.getBalance();
		assertEquals(2000, actual);
	}
}
