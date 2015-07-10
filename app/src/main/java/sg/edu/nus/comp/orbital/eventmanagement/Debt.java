package sg.edu.nus.comp.orbital.eventmanagement;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashSet;

/*** Debt class ***/

public class Debt implements Parcelable{
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

	// Parcelable Constructor
	protected Debt(Parcel in) {
		try {
			debtID = in.readString();
			bill = in.readParcelable(Bill.class.getClassLoader());
			loanshark = in.readParcelable(User.class.getClassLoader());
			debtor = in.readParcelable(User.class.getClassLoader());
			Purchase[] purchaseArray = in.createTypedArray(Purchase.CREATOR);
			purchases = new HashSet<Purchase>();
			for (Purchase purchase : purchaseArray) {
				purchases.add(purchase);
			}
			costTotal = in.readDouble();
			paidAmt = in.readDouble();
			debtAmt = in.readDouble();
		} catch (Exception e) {
			Log.e("DEBT PARCEL ERROR", e.toString());
			e.printStackTrace();
		}
	}

	// Implementation of Parcelable Creator
	public static final Creator<Debt> CREATOR = new Creator<Debt>() {
		@Override
		public Debt createFromParcel(Parcel in) {
			return new Debt(in);
		}

		@Override
		public Debt[] newArray(int size) {
			return new Debt[size];
		}
	};

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

	// Parcelable function
	@Override
	public int describeContents() {
		return 0;
	}

	// Construct Parcel
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(debtID);
		out.writeParcelable(bill, flags);
		out.writeParcelable(loanshark, flags);
		out.writeParcelable(debtor, flags);
		Purchase[] purchaseArray = new Purchase[purchases.size()];
		out.writeTypedArray(purchases.toArray(purchaseArray), 0);
		out.writeDouble(costTotal);
		out.writeDouble(paidAmt);
		out.writeDouble(debtAmt);
	}

}
