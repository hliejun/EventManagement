package sg.edu.nus.comp.orbital.eventmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

//Manual Input
class Bill implements calculationSystem {
	protected String billID = null;
	protected String eventTitle = null;
	protected HashMap<String, User> userDatabase = null;
	protected HashMap<String, Item> itemDatabase = null;
	protected HashMap<String, Purchase> purchaseDatabase = null;
	protected HashMap<User, HashSet<Debt>> debtDatabase = null;
	protected HashMap<User, Double> userCostTable = null;
	protected ListOfPurchases purchaseList = null;
	// protected ListOfDebts debtList = null;
	protected Group userGroup = null;
	protected User payer = null;
	protected double subTotal = 0;
	protected double grandTotal = 0;
	protected double gst = 0.07;
	protected double serviceTax = 0.10;
	protected double additionalCost = 0;
	protected double discount = 0;

	// Constructor
	public Bill(Group group, User mPayer, String eventName) throws IllegalArgumentException {
		userDatabase = new HashMap<String, User>();
		itemDatabase = new HashMap<String, Item>();
		purchaseDatabase = new HashMap<String, Purchase>();
		debtDatabase = new HashMap<User, HashSet<Debt>>();
		userCostTable = new HashMap<User, Double>();

		if (group == null || mPayer == null) {
			throw new IllegalArgumentException(
					"Invalid argument provided! Cannot create bilL!");
		}

		userGroup = group;
		payer = mPayer;

		if (userGroup != null) {
			for (User user : userGroup.getUsers()) {
				userDatabase.put(user.getUserName(), user);
			}
		}

		billID = Integer.toString(this.hashCode());
		eventTitle = eventName;
	}

	// Add User with Facebook support
	public boolean addUser(String userName, String facebookUID,
			String phoneNumber, double costIncurred) {
		if (userDatabase.get(userName) != null) {
			throw new RuntimeException("User with this name already exists!");
		}
		try {
			User newUser = new User(userName, facebookUID, phoneNumber);
			userDatabase.put(userName, newUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Add User without Facebook support
	public boolean addUser(String userName, String phoneNumber,
			double costIncurred) {
		if (userDatabase.get(userName) != null) {
			throw new RuntimeException("User with this name already exists!");
		}
		try {
			User newUser = new User(userName, phoneNumber);
			userDatabase.put(userName, newUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Add User given a list of strings as input
	public boolean addUser(ArrayList<String> input)
			throws IllegalArgumentException {
		if (input.size() < 3 || input.size() > 4) {
			throw new IllegalArgumentException(
					"Invalid input string! Parameter size mismatch!");
		}
		switch (input.size()) {
		case 2: {
			String userName = input.get(0);
			String phoneNumber = input.get(1);
			if (userDatabase.get(userName) != null) {
				throw new RuntimeException(
						"User with this name already exists!");
			}
			try {
				User newUser = new User(userName, phoneNumber);
				userDatabase.put(userName, newUser);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		case 3: {
			String userName = input.get(0);
			String facebookUID = input.get(1);
			String phoneNumber = input.get(2);
			if (userDatabase.get(userName) != null) {
				throw new RuntimeException(
						"User with this name already exists!");
			}
			try {
				User newUser = new User(userName, facebookUID, phoneNumber);
				userDatabase.put(userName, newUser);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		}
		return false;
	}

	// Add Item
	public boolean addItem(String name, String type, double cost) {
		if (itemDatabase.get(name) != null) {
			throw new RuntimeException("Item with this name already exists!");
		}
		try {
			Item newItem = new Item(name, type, cost);
			itemDatabase.put(name, newItem);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Add Item given a list of strings as input
	public boolean addItem(ArrayList<String> input)
			throws IllegalArgumentException {
		if (input.size() != 3) {
			throw new IllegalArgumentException(
					"Invalid input string! Parameter size mismatch!");
		}
		String name = input.get(0);
		String type = input.get(1);
		double cost = Double.parseDouble(input.get(2));
		if (itemDatabase.get(name) != null) {
			throw new RuntimeException("Item with this name already exists!");
		}
		try {
			Item newItem = new Item(name, type, cost);
			itemDatabase.put(name, newItem);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Add single-user Purchase to list
	public boolean addPurchase(String userName, String itemName, int quantity)
			throws IllegalArgumentException {
		if (userDatabase.get(userName) == null) {
			throw new IllegalArgumentException(
					"User does not exist in the database!");
		}
		if (itemDatabase.get(itemName) == null) {
			throw new IllegalArgumentException(
					"Item does not exist in the database!");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException(
					"Quantity must be a positive integer!");
		}
		User user = userDatabase.get(userName);
		Item item = itemDatabase.get(itemName);
		try {
			Purchase newPurchase = new Purchase(user, item, quantity);
			String purchaseID = Integer.toString(newPurchase.hashCode());
			purchaseDatabase.put(purchaseID, newPurchase);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Add multi-user Purchase to list
	public boolean addPurchase(ArrayList<String> userNames, String itemName,
			int quantity) throws IllegalArgumentException {
		HashSet<User> users = new HashSet<User>();
		for (String userName : userNames) {
			if (userDatabase.get(userName) == null) {
				throw new IllegalArgumentException(
						"User does not exist in the database!");
			}
			users.add(userDatabase.get(userName));
		}
		if (itemDatabase.get(itemName) == null) {
			throw new IllegalArgumentException(
					"Item does not exist in the database!");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException(
					"Quantity must be a positive integer!");
		}
		Item item = itemDatabase.get(itemName);
		try {
			Purchase newPurchase = new Purchase(users, item, quantity);
			String purchaseID = Integer.toString(newPurchase.hashCode());
			purchaseDatabase.put(purchaseID, newPurchase);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Add Purchase, given a list of strings as input, to list
	public boolean addPurchase(ArrayList<String> input)
			throws IllegalArgumentException {
		if (input.size() < 3) {
			throw new IllegalArgumentException(
					"Invalid input string! Parameter size mismatch!");
		}
		// Single User Purchase
		if (input.size() == 3) {
			String userName = input.get(0);
			String itemName = input.get(1);
			int quantity = Integer.parseInt(input.get(2));
			if (userDatabase.get(userName) == null
					|| itemDatabase.get(itemName) == null) {
				throw new RuntimeException(
						"User or Item does not exist in database! Cannot create purchase!");
			}
			if (quantity <= 0) {
				throw new IllegalArgumentException(
						"Quantity must be a positive integer!");
			}
			try {
				User user = userDatabase.get(userName);
				Item item = itemDatabase.get(itemName);
				Purchase newPurchase = new Purchase(user, item, quantity);
				String purchaseID = Integer.toString(newPurchase.hashCode());
				purchaseDatabase.put(purchaseID, newPurchase);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			// Multi User Purchase
		} else {
			try {
				HashSet<User> users = new HashSet<User>();
				for (int count = 0; count < input.size() - 2; ++count) {
					if (userDatabase.get(input.get(count)) == null) {
						throw new RuntimeException(
								"User does not exist in database! Cannot create purchase!");
					}
					users.add(userDatabase.get(input.get(count)));
				}
				String itemName = input.get(input.size() - 2);
				int quantity = Integer.parseInt(input.get(input.size() - 1));
				if (itemDatabase.get(itemName) == null) {
					throw new RuntimeException(
							"Item does not exist in database! Cannot create purchase!");
				}
				if (quantity <= 0) {
					throw new IllegalArgumentException(
							"Quantity must be a positive integer!");
				}
				Item item = itemDatabase.get(itemName);
				Purchase newPurchase = new Purchase(users, item, quantity);
				String purchaseID = Integer.toString(newPurchase.hashCode());
				purchaseDatabase.put(purchaseID, newPurchase);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	// Generate List of Purchases
	public void setList() {
		if (userDatabase == null || itemDatabase == null
				|| purchaseDatabase == null) {
			throw new RuntimeException(
					"Databases are not set! Cannot generate list of purchases!");
		}
		purchaseList = new ListOfPurchases(userDatabase, itemDatabase,
				purchaseDatabase);
	}

	// Remove User
	@SuppressWarnings("unchecked")
	public boolean removeUser(User user) {
		if (user == null || userDatabase.get(user.getUserName()) == null) {
			throw new RuntimeException(
					"User does not exist in database! Cannot remove user!");
		}
		try {
			// Obtain shallow copy of user purchases
			HashSet<Purchase> userPurchases = new HashSet<Purchase>();
			userPurchases = (HashSet<Purchase>) purchaseList
					.getUserIndexedPurchases().get(user).clone();
			if (userPurchases != null && userPurchases.size() != 0) {
				for (Purchase purchase : userPurchases) {
					if (purchase.getUser().size() == 1) {
						purchaseList.removePurchase(purchase);
						purchaseDatabase.remove(purchase.getPurchaseID());
					} else {
						purchase.removeUser(user);
					}
				}
			}
			userDatabase.remove(user.getUserName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Remove User by ID / Name
	@SuppressWarnings("unchecked")
	public boolean removeUser(String userID) {
		if (userDatabase.get(userID) == null) {
			throw new RuntimeException(
					"User does not exist in database! Cannot remove user!");
		}
		try {
			User user = userDatabase.get(userID);
			// Obtain shallow copy of user purchases
			HashSet<Purchase> userPurchases = new HashSet<Purchase>();
			userPurchases = (HashSet<Purchase>) purchaseList
					.getUserIndexedPurchases().get(user).clone();
			if (userPurchases != null && userPurchases.size() != 0) {
				for (Purchase purchase : userPurchases) {
					if (purchase.getUser().size() == 1) {
						purchaseList.removePurchase(purchase);
						purchaseDatabase.remove(purchase.getPurchaseID());
					} else {
						purchase.removeUser(user);
					}
				}
			}
			userDatabase.remove(user.getUserName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Remove Item
	@SuppressWarnings("unchecked")
	public boolean removeItem(Item item) {
		if (item == null || itemDatabase.get(item.getItemName()) == null) {
			throw new RuntimeException(
					"Item does not exist in database! Cannot remove item!");
		}
		try {
			// Obtain shallow copy of purchases of item
			HashSet<Purchase> itemPurchases = new HashSet<Purchase>();
			itemPurchases = (HashSet<Purchase>) purchaseList
					.getItemIndexedPurchases().get(item).clone();
			if (itemPurchases != null && itemPurchases.size() != 0) {
				for (Purchase purchase : itemPurchases) {
					purchaseList.removePurchase(purchase);
					purchaseDatabase.remove(purchase.getPurchaseID());
				}
			}
			itemDatabase.remove(item.getItemName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Remove Item by ID / Name
	@SuppressWarnings("unchecked")
	public boolean removeItem(String itemID) {
		if (itemDatabase.get(itemID) == null) {
			throw new RuntimeException(
					"Item does not exist in database! Cannot remove item!");
		}
		try {
			Item item = itemDatabase.get(itemID);
			// Obtain shallow copy of purchases of item
			HashSet<Purchase> itemPurchases = new HashSet<Purchase>();
			itemPurchases = (HashSet<Purchase>) purchaseList
					.getItemIndexedPurchases().get(item).clone();
			if (itemPurchases != null && itemPurchases.size() != 0) {
				for (Purchase purchase : itemPurchases) {
					purchaseList.removePurchase(purchase);
					purchaseDatabase.remove(purchase.getPurchaseID());
				}
			}
			itemDatabase.remove(item.getItemName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Remove Purchase
	public boolean removePurchase(Purchase purchase) {
		if (purchase == null
				|| purchaseDatabase.get(purchase.getPurchaseID()) == null) {
			throw new RuntimeException(
					"Purchase does not exist in database! Cannot remove purchase!");
		}
		try {
			purchaseList.removePurchase(purchase);
			purchaseDatabase.remove(purchase.getPurchaseID());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Remove Purchase by ID
	public boolean removePurchase(String purchaseID) {
		if (purchaseDatabase.get(purchaseID) == null) {
			throw new RuntimeException(
					"Purchase does not exist in database! Cannot remove purchase!");
		}
		try {
			Purchase purchase = purchaseDatabase.get(purchaseID);
			purchaseList.removePurchase(purchase);
			purchaseDatabase.remove(purchase.getPurchaseID());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Edit User with Facebook support
	public boolean editUser(String userID, String newName, String facebookUID)
			throws IllegalArgumentException {
		if (userDatabase.get(userID) == null) {
			throw new RuntimeException(
					"User does not exist! Please create as new user!");
		}
		if (newName.length() <= 0 || facebookUID.length() <= 0) {
			throw new IllegalArgumentException(
					"Invalid argument! Cannot edit user!");
		}
		try {
			User user = userDatabase.get(userID);
			user.setUserName(newName);
			user.setFacebookUID(facebookUID);
			userDatabase.remove(userID);
			userDatabase.put(newName, user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Edit User without Facebook support
	public boolean editUser(String userID, String newName)
			throws IllegalArgumentException {
		if (userDatabase.get(userID) == null) {
			throw new RuntimeException(
					"User does not exist! Please create as new user!");
		}
		if (newName.length() <= 0) {
			throw new IllegalArgumentException(
					"Invalid argument! Cannot edit user!");
		}
		try {
			User user = userDatabase.get(userID);
			user.setUserName(newName);
			userDatabase.remove(userID);
			userDatabase.put(newName, user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Edit User given a list of strings as input
	public boolean editUser(ArrayList<String> input)
			throws IllegalArgumentException {
		if (input.size() < 2 || input.size() > 3) {
			throw new IllegalArgumentException("Parameter size mismatch!");
		}
		switch (input.size()) {
		case 2: {
			String userID = input.get(0);
			String newName = input.get(1);
			if (userDatabase.get(userID) == null) {
				throw new RuntimeException(
						"User does not exist! Please create as new user!");
			}
			if (newName.length() <= 0) {
				throw new IllegalArgumentException(
						"Invalid argument! Cannot edit user!");
			}
			try {
				User user = userDatabase.get(userID);
				user.setUserName(newName);
				userDatabase.remove(userID);
				userDatabase.put(newName, user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		case 3: {
			String userID = input.get(0);
			String newName = input.get(1);
			String facebookUID = input.get(2);
			if (userDatabase.get(userID) == null) {
				throw new RuntimeException(
						"User does not exist! Please create as new user!");
			}
			if (newName.length() <= 0 || facebookUID.length() <= 0) {
				throw new IllegalArgumentException(
						"Invalid argument! Cannot edit user!");
			}
			try {
				User user = userDatabase.get(userID);
				user.setUserName(newName);
				user.setFacebookUID(facebookUID);
				userDatabase.remove(userID);
				userDatabase.put(newName, user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		}
		return false;
	}

	// Edit Item
	public boolean editItem(String itemID, String newName, String type,
			double cost) throws IllegalArgumentException {
		if (itemDatabase.get(itemID) == null) {
			throw new RuntimeException(
					"Item does not exist! Please create as new item!");
		}
		if (newName.length() <= 0 || type.length() <= 0 || cost <= 0) {
			throw new IllegalArgumentException(
					"Invalid argument! Cannot edit item!");
		}
		try {
			Item item = itemDatabase.get(itemID);
			item.setItemName(newName);
			item.setItemType(type);
			item.setItemCost(cost);
			itemDatabase.remove(itemID);
			itemDatabase.put(newName, item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Edit Item given a list of strings as input
	public boolean editItem(ArrayList<String> input)
			throws IllegalArgumentException {
		if (input.size() != 4) {
			throw new IllegalArgumentException("Parameter size mismatch!");
		}
		String itemID = input.get(0);
		String newName = input.get(1);
		String type = input.get(2);
		double cost = Double.parseDouble(input.get(3));
		if (itemDatabase.get(itemID) == null) {
			throw new RuntimeException(
					"Item does not exist! Please create as new item!");
		}
		if (newName.length() <= 0 || cost <= 0 || type.length() <= 0) {
			throw new IllegalArgumentException(
					"Invalid argument! Cannot edit item!");
		}
		try {
			Item item = itemDatabase.get(itemID);
			item.setItemName(newName);
			item.setItemType(type);
			item.setItemCost(cost);
			itemDatabase.remove(itemID);
			itemDatabase.put(newName, item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Edit single-user Purchase to list
	public boolean editPurchase(String purchaseID, User mUser, Item mItem,
			int mQuantity) throws IllegalArgumentException {
		if (purchaseDatabase.get(purchaseID) == null) {
			throw new RuntimeException(
					"Purchase does not exist! Please create as new purchase!");
		}
		if (mUser == null || mItem == null || mQuantity <= 0) {
			throw new IllegalArgumentException(
					"Invalid argument! Cannot edit purchase!");
		}
		try {
			Purchase purchase = purchaseDatabase.get(purchaseID);
			purchase.setUser(mUser);
			purchase.setItem(mItem);
			purchase.setQuantity(mQuantity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Edit multi-user Purchase to list
	public boolean editPurchase(String purchaseID, HashSet<User> mUser,
			Item mItem, int mQuantity) throws IllegalArgumentException {
		if (purchaseDatabase.get(purchaseID) == null) {
			throw new RuntimeException(
					"Purchase does not exist! Please create as new purchase!");
		}
		if (mUser == null || mUser.size() <= 0 || mItem == null
				|| mQuantity <= 0) {
			throw new IllegalArgumentException(
					"Invalid argument! Cannot edit purchase!");
		}
		try {
			Purchase purchase = purchaseDatabase.get(purchaseID);
			purchase.setUser(mUser);
			purchase.setItem(mItem);
			purchase.setQuantity(mQuantity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Edit Purchase, given a list of strings as input
	public boolean editPurchase(ArrayList<String> input)
			throws IllegalArgumentException {
		if (input.size() < 4) {
			throw new IllegalArgumentException("Parameter size mismatch!");
		}
		String purchaseID = input.get(0);
		if (purchaseDatabase.get(purchaseID) == null) {
			throw new RuntimeException(
					"Purchase does not exist! Please create as new purchase!");
		}
		Item item = itemDatabase.get(input.get(input.size() - 2));
		int quantity = Integer.parseInt(input.get(input.size() - 1));
		if (item == null || quantity <= 0) {
			throw new RuntimeException(
					"Item not found in database / Quantity given is invalid! Cannot edit this purchase with given parameters!");
		}
		if (input.size() == 4) {
			User user = userDatabase.get(input.get(input.size() - 3));
			if (user == null) {
				throw new RuntimeException(
						"User not found in database! Cannot edit this purchase with given parameters!");
			}
			try {
				Purchase purchase = purchaseDatabase.get(purchaseID);
				purchase.setItem(item);
				purchase.setUser(user);
				purchase.setQuantity(quantity);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			HashSet<User> users = new HashSet<User>();
			for (int count = 1; count < input.size() - 2; ++count) {
				if (userDatabase.get(input.get(count)) == null) {
					throw new RuntimeException(
							"User not found in database! Cannot edit this purchase with given parameters!");
				}
				users.add(userDatabase.get(input.get(count)));
			}
			try {
				Purchase purchase = purchaseDatabase.get(purchaseID);
				purchase.setItem(item);
				purchase.setUser(users);
				purchase.setQuantity(quantity);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	// Setter for GST
	// Set gst
	public void setGST(double mGst) throws IllegalArgumentException {
		if (mGst > 1 || mGst < 0) {
			throw new IllegalArgumentException("GST is out of range!");
		}
		gst = mGst;
	}

	// Set serviceTax
	// Setter for Service Tax
	public void setServiceTax(double mServiceTax)
			throws IllegalArgumentException {
		if (mServiceTax > 1 || mServiceTax < 0) {
			throw new IllegalArgumentException("Service Tax is out of range!");
		}
		serviceTax = mServiceTax;
	}

	// Set additionalCost
	// Setter for Additional Cost
	public void setAdditionalCost(double addCost)
			throws IllegalArgumentException {
		if (addCost < 0) {
			throw new IllegalArgumentException(
					"Additonal cost cannot be negative!");
		}
		additionalCost = addCost;
	}

	// Setter for Discount
	// Set discount
	public void setDiscount(double mDiscount) {
		if (mDiscount < 0) {
			throw new IllegalArgumentException("Discount cannot be negative!");
		}
		discount = mDiscount;
	}

	// Get gst
	// Getter for GST
	public double getGST() {
		return gst;
	}

	// Getter for Service Tax
	// Get serviceTax
	public double getServiceTax() {
		return serviceTax;
	}

	// Get additionalCost
	// Getter for Additional Cost
	public double getAdditionalCost() {
		return additionalCost;
	}

	// Getter for Discount
	// Get discount
	public double getDiscount() {
		return discount;
	}

	// Get user database
	// Getter for User Database
	public HashMap<String, User> getUserDatabase() {
		return userDatabase;
	}

	// Getter for Item Database
	// Get item database
	public HashMap<String, Item> getItemDatabase() {
		return itemDatabase;
	}

	// Get purchase database
	// Getter for Purchase Database
	public HashMap<String, Purchase> getPurchaseDatabase() {
		return purchaseDatabase;
	}

	// Getter for Purchase List
	// Get list of purchase
	public ListOfPurchases getListOfPurchases() {
		return purchaseList;
	}

	// Count subTotal
	// Calculate sub-total
	@Override
	public double subTotal() throws IllegalArgumentException {
		double sum = 0;
		if (purchaseList == null) {
			throw new IllegalArgumentException(
					"Subtotal cannot be calculated using a null list!");
		}
		HashSet<Purchase> purchases = purchaseList.getPurchases();
		if (purchases == null) {
			throw new IllegalArgumentException(
					"Subtotal cannot be calculated using a null list!");
		}
		for (Purchase purchase : purchases) {
			if (purchase != null) {
				sum += purchase.getItem().getItemCost()
						* purchase.getQuantity();
			}
		}
		return sum;
	}

	// Count grandTotal
	// Calculate grand-total
	@Override
	public double grandTotal() throws IllegalArgumentException {
		if (subTotal < 0 || gst > 1 || gst < 0 || serviceTax > 1
				|| serviceTax < 0 || additionalCost < 0 || discount < 0) {
			throw new IllegalArgumentException(
					"Invalid tax / cost field provided!");
		}
		double grandTotal = subTotal * (1 + gst + serviceTax) + additionalCost
				- discount;
		if (grandTotal < 0) {
			throw new RuntimeException(
					"Bad total calculation! Grand total cannot be negative!");
		}
		return grandTotal;
	}

	// Split cost evenly across all users with purchases
	// Split bill evenly
	@Override
	public double evenSplit() {
		if (userDatabase == null || userDatabase.size() == 0) {
			throw new RuntimeException(
					"Invalid split call! Bad / Null database!");
		}
		return grandTotal / purchaseList.getUserIndexedPurchases().size();
	}

	// Calculate and set cost incurred for every user with purchases
	@Override
	public void costCalc() {
		HashMap<User, HashSet<Purchase>> userIndexedPurchaseList = purchaseList
				.getUserIndexedPurchases();
		if (userIndexedPurchaseList == null) {
			throw new RuntimeException("Invalid User-Indexed Table!");
		}
		double sum = 0;
		int shareRatio = 0;
		for (User user : userIndexedPurchaseList.keySet()) {
			HashSet<Purchase> purchases = userIndexedPurchaseList.get(user);
			sum = 0;
			if (purchases != null) {
				for (Purchase purchase : purchases) {
					shareRatio = purchase.getUser().size();
					if (shareRatio == 0) {
						throw new RuntimeException(
								"Cannot have 0 buyers in a purchase!");
					}
					sum += purchase.getQuantity()
							* purchase.getItem().getItemCost() / shareRatio;
				}
			}
			sum *= gst + serviceTax + 1;
			sum += additionalCost - discount;
			// rounding off ...
			sum *= 20;
			sum = Math.floor(sum);
			sum /= 20;
			// userCostTable that tracks payment due for each user
			userCostTable.put(user, sum);
		}
	}

	// Create a new Debt and add to database
	public void debtMaking(User debtor, double paidAmt)
			throws IllegalArgumentException {
		if (debtor == null || paidAmt <= 0) {
			throw new IllegalArgumentException(
					"Invalid Arguments! Cannot create debt!");
		}
		Double costIncurred = userCostTable.get(debtor);
		if (costIncurred == null) {
			throw new RuntimeException(
					"User does not have a cost incurred yet! Cannot create debt!");
		}
		try {
			if (costIncurred - paidAmt != 0) {
				Debt newDebt = new Debt(this, payer, debtor, costIncurred,
						paidAmt);
				if(debtDatabase.get(newDebt.getDebtor()) == null) {
					HashSet<Debt> debts = new HashSet<Debt>();
					debts.add(newDebt);
					debtDatabase.put(newDebt.getDebtor(), debts);
				} else {
					HashSet<Debt> debts = debtDatabase.get(newDebt.getDebtor());
					debts.add(newDebt);
					debtDatabase.put(newDebt.getDebtor(), debts);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Remove Debt
	public void removeDebt(User debtor) throws IllegalArgumentException {
		if (debtor == null || debtDatabase.get(debtor) == null) {
			throw new IllegalArgumentException(
					"Invalid debtor! Cannot remove Debt!");
		}
		debtDatabase.remove(debtor);
	}

	// Get Event Title
	public String getEventTitle() {
		return eventTitle;
	}

	// Set Event Title
	public void setEventTitle(String title) {
		if(title.length() > 0) {
			eventTitle = title;
		}
	}

	// Get User Cost Table
	public HashMap<User, Double> getUserCostTable() {
		return userCostTable;
	}

	// Getter for Debt Database
	public HashMap<User, HashSet<Debt>> getDebtDatabase() {
		return debtDatabase;
	}

}