package sg.edu.nus.comp.orbital.eventmanagement;

import java.util.HashSet;

public class Debt {
	protected String debtID = null;
	protected Bill bill = null;
	protected User loanshark = null;
	protected User debtor = null;
	protected HashSet<Purchase> purchases = null;
	protected double costTotal = 0;
	protected double paidAmt = 0;
	protected double debtAmt = 0;

	// Constructor
	public Debt(Bill mBill, User mLoanshark, User mDebtor, double total,
			double paid) throws IllegalArgumentException {
		if (mBill == null || mLoanshark == null || mDebtor == null) {
			throw new IllegalArgumentException(
					"Invalid argument! Debt cannot be created!");
		}
		if (paid == total || total == 0) {
			throw new IllegalArgumentException(
					"Cost cannot be 0 or same as paid amount for debt creation!");
		}
		bill = mBill;
		costTotal = total;
		paidAmt = paid;
		if (costTotal > paidAmt) {
			loanshark = mLoanshark;
			debtor = mDebtor;
			debtAmt = costTotal - paidAmt;
		} else {
			loanshark = mDebtor;
			debtor = mLoanshark;
			debtAmt = paidAmt - costTotal;
		}
		purchases = bill.purchaseList.getUserIndexedPurchases().get(mDebtor);
		if (purchases == null) {
			throw new RuntimeException(
					"No purchases found! Debt cannot be created!");
		}
		debtID = Integer.toString(this.hashCode());
	}

	// Loanshark getter
	public User getLoaner() {
		return loanshark;
	}
	
	// Debtor getter
	public User getDebtor() {
		return debtor;
	}

	// Bill getter
	public Bill getBill() {
		return bill;
	}

	// Debt amount getter
	public double getDebtAmt() {
		return debtAmt;
	}

	// Purchase set getter
	public HashSet<Purchase> getPurchaseSet() {
		return purchases;
	}

	// Debt ID getter
	public String getDebtID() {
		return debtID;
	}
	
	// Get information on debt (through bill)
	// ...

}
