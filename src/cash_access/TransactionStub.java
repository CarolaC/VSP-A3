package cash_access;

public class TransactionStub extends TransactionImplBase {

	@Override
	public void deposit(String accountID, double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdraw(String accountID, double amount)
			throws OverdraftException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getBalance(String accountID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
