// KIV: Not neccessary!

package sg.edu.nus.comp.orbital.eventmanagement;

import java.util.HashMap;
import java.util.HashSet;

public class ListOfDebts {
	protected HashSet<Debt> debts = null;
	protected HashMap<User, HashSet<Purchase>> loansharkAsKey = null;
	protected HashMap<Item, HashSet<Purchase>> itemAsKey = null;
	protected HashMap<String, User> userDatabase = null;
	protected HashMap<String, Item> itemDatabase = null;
	protected HashMap<String, Purchase> purchaseDatabase = null;
	
	// Construct with debtDatabase
	public ListOfDebts(HashMap<String, User> userData,
			HashMap<String, Debt> debtData) {
		
	}
	// Get by payer

	// Get by payee

	// Add Debt
	public void addDebt(Debt newDebt) {
		// TODO Auto-generated method stub
		
	}

	// Edit Debt
	
	// Remove Debt

	// Getter for debt set

	// Getter for debt set (Indexed by payer)

	// Getter for debt set (Indexed by payee)

	// Setter for debt amount

	// Payer to string

	// Payee to string

	// Debt to string

	// String to payer

	// String to payee

	// String to Debt

}