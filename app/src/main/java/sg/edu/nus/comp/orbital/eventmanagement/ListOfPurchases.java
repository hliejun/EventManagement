package sg.edu.nus.comp.orbital.eventmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
 * Class ListOfPurchases
 * 
 * This class represents a set of purchases, along with the capability to index them either by user or item.
 * 
 * The user and item database passed into the object upon construction is assumed to be correct.
 * This has to be checked and maintained by external code.
 * 
 * @author Huang Lie Jun
 */
public class ListOfPurchases {
	protected HashSet<Purchase> purchases = null;
	protected HashMap<User, HashSet<Purchase>> userAsKey = null;
	protected HashMap<Item, HashSet<Purchase>> itemAsKey = null;
	protected HashMap<String, User> userDatabase = null;
	protected HashMap<String, Item> itemDatabase = null;
	protected HashMap<String, Purchase> purchaseDatabase = null;

	/*
	 * Constructor
	 * 
	 * @param HashMap<String, User> userData, HashMap<String, Item> itemData,
	 * HashMap<String, Purchase> purchaseData
	 * 
	 * Creates a set of purchases with option to index by user or by item
	 */
	public ListOfPurchases(HashMap<String, User> userData,
			HashMap<String, Item> itemData,
			HashMap<String, Purchase> purchaseData)
			throws IllegalArgumentException {
		// Check for invalid arguments
		if (userData == null || itemData == null || purchaseData == null) {
			throw new IllegalArgumentException("Invalid Database supplied!");
		}
		userDatabase = userData;
		itemDatabase = itemData;
		purchaseDatabase = purchaseData;
		purchases = new HashSet<Purchase>();
		userAsKey = new HashMap<User, HashSet<Purchase>>();
		itemAsKey = new HashMap<Item, HashSet<Purchase>>();
		// Go through every purchase, add them into purchase list if valid
		// For every purchase, index by User and Item
		for (Purchase current : purchaseData.values()) {
			// If purchase is valid, add to list
			if (current != null && current.getItem() != null
					&& current.getUser() != null) {
				purchases.add(current);
				HashSet<User> currentUsers = current.getUser();
				Item currentItem = current.getItem();
				// For every user involved in purchase, index accordingly
				for (User currentUser : currentUsers) {
					// For new user, create new entry and purchase set
					if (currentUser != null
							&& userAsKey.get(currentUser) == null) {
						HashSet<Purchase> newPurchaseSet = new HashSet<Purchase>();
						newPurchaseSet.add(current);
						userAsKey.put(currentUser, newPurchaseSet);
						// For existing user, update current purchase set
					} else if (currentUser != null) {
						HashSet<Purchase> currentPurchaseSet = userAsKey
								.get(currentUser);
						currentPurchaseSet.add(current);
						userAsKey.put(currentUser, currentPurchaseSet);
					}
				}
				// For new item, create new entry and purchase set
				if (itemAsKey.get(currentItem) == null) {
					HashSet<Purchase> newPurchaseSet = new HashSet<Purchase>();
					newPurchaseSet.add(current);
					itemAsKey.put(currentItem, newPurchaseSet);
					// For existing item, update current purchase set
				} else {
					HashSet<Purchase> currentPurchaseSet = itemAsKey
							.get(currentItem);
					currentPurchaseSet.add(current);
					itemAsKey.put(currentItem, currentPurchaseSet);
				}
			}
		}
	}

	/*
	 * Getter for purchase set
	 * 
	 * @return HashSet<Purchase> purchases
	 * 
	 * Returns a set of all purchases in this list, with no indexing
	 */
	public HashSet<Purchase> getPurchases() {
		return purchases;
	}

	/*
	 * Getter for purchase set (Indexed by User)
	 * 
	 * @return HashMap<User, HashSet<Purchase> userAsKey
	 * 
	 * Returns a set of all purchases, indexed by User.
	 * 
	 * Each entry of the hash table can be accessed by providing the User, and
	 * returns a subset of all purchases involving the user.
	 */
	public HashMap<User, HashSet<Purchase>> getUserIndexedPurchases() {
		return userAsKey;
	}

	/*
	 * Getter for purchase set (Indexed by Item)
	 * 
	 * @return HashMap<Item, HashSet<Purchase> itemAsKey
	 * 
	 * Returns a set of all purchases indexed by Item.
	 * 
	 * Each entry of the hash table can be accessed by providing the Item, and
	 * returns a subset of all purchases involving the item.
	 */
	public HashMap<Item, HashSet<Purchase>> getItemIndexedPurchases() {
		return itemAsKey;
	}

	/*
	 * Purchase Adder
	 * 
	 * @param Purchase purchase
	 * 
	 * Adds a valid purchase to the set of purchases in this list, and updates
	 * the Item-indexed and User-indexed structures.
	 */
	public boolean addPurchase(Purchase purchase) {
		// Check for valid argument
		if (purchase == null) {
			throw new RuntimeException("Target purchase object not found!");
		}
		// Only add the purchase if it does not already exist in this list
		if (!purchases.contains(purchase)) {
			// Add purchase to purchase list
			purchases.add(purchase);
			// Update Item-Indexed Table
			HashSet<Purchase> purchasesOfItem = itemAsKey.get(purchase
					.getItem());
			// If this is the first purchase of the item, initialize new set
			if (purchasesOfItem == null) {
				purchasesOfItem = new HashSet<Purchase>();
				purchasesOfItem.add(purchase);
				itemAsKey.put(purchase.getItem(), purchasesOfItem);
				// Otherwise, update existing set
			} else {
				purchasesOfItem.add(purchase);
			}
			// Update User-Indexed Table
			// For every user involved in this purchase, update their purchase
			// set
			for (User user : purchase.getUser()) {
				// If this is the first purchase by a user, initialize new set
				if (userAsKey.get(user) == null) {
					HashSet<Purchase> newSet = new HashSet<Purchase>();
					newSet.add(purchase);
					userAsKey.put(user, newSet);
					// Otherwise, update existing set(s)
				} else {
					HashSet<Purchase> curSet = userAsKey.get(user);
					curSet.add(purchase);
				}
			}
			// Flag success in adding purchase
			return true;
		} else {
			// If purchase already exists, flag failure in adding purchase
			System.out.println("This purchase is already added!");
			return false;
		}
	}

	/*
	 * Purchase Remover
	 * 
	 * @param Purchase purchase
	 * 
	 * Removes the valid target purchase from the set of purchases in this list
	 * and updates the User-Indexed and Item-Indexed structures.
	 */
	public boolean removePurchase(Purchase purchase) {
		// Check for valid argument
		if (purchase == null) {
			throw new RuntimeException("Target purchase object not found!");
		}
		if (purchases.contains(purchase)) {
			// Remove purchase from purchase list
			purchases.remove(purchase);
			// Update Item-Indexed Table
			HashSet<Purchase> purchasesOfItem = itemAsKey.get(purchase
					.getItem());
			if (purchasesOfItem.contains(purchase)) {
				purchasesOfItem.remove(purchase);
			}
			// Update User-Indexed Table (for every user involved)
			for (User user : purchase.getUser()) {
				HashSet<Purchase> purchasesOfUser = userAsKey.get(user);
				if (purchasesOfUser.contains(purchase)) {
					purchasesOfUser.remove(purchase);
				}
			}
			// Flag success in removing purchase
			return true;
		} else {
			// Flag failure in removing purchase
			System.out.println("This purchase is not in the purchase list!");
			return false;
		}
	}

	/*
	 * Setter for purchase quantity
	 * 
	 * @param Purchase purchase, int qty
	 * 
	 * Sets the valid target purchase quantity to a value > 0
	 */
	public boolean changePurchaseQuantity(Purchase purchase, int qty) {
		// Check for valid argument
		if (purchase == null) {
			throw new RuntimeException("Target purchase object not found!");
		}
		// Check for valid quantity
		if (qty > 0) {
			purchase.setQuantity(qty);
			// Flag success
			return true;
		} else {
			// Flag failure
			System.out.println("Quantity to set to is invalid!");
			return false;
		}
	}

	/*
	 * Item Query
	 * 
	 * @param String itemName
	 * 
	 * @return ArrayList<String> output
	 * 
	 * For a queried Item, return a contiguous list of stringified purchases,
	 * each purchase is structured as: quantity, share state, person or people
	 * who purchased this item
	 */
	public ArrayList<String> getByItem(String itemName) {
		// Search database for Item object
		Item item = itemStringToObject(itemName);
		ArrayList<String> output = new ArrayList<String>();
		HashSet<Purchase> purchases = null;
		// Handle situation whereby Item is not found in our database
		if (item == null) {
			throw new RuntimeException("This item is not in our database!");
		}
		purchases = itemAsKey.get(item);
		// Check for valid purchase set of this item
		if (purchases == null) {
			throw new RuntimeException("There are no purchases of this item!");
		}
		// Iterate through all purchases related to this item
		for (Purchase purchase : purchases) {
			if (purchase != null) {
				// Get the users who purchased this item
				HashSet<User> users = purchase.getUser();
				// Get the quantity purchased
				int qty = purchase.getQuantity();
				// Get the share status
				boolean isShared = (users.size() > 1);
				// Convert to strings and add to output array
				output.add(Integer.toString(qty));
				output.add(Boolean.toString(isShared));
				for (User user : users) {
					if (user != null) {
						output.add(userObjectToString(user));
					}
				}
			}
		}
		// Return array of strings
		return output;
	}

	/*
	 * User Query
	 * 
	 * @param String userName
	 * 
	 * @return ArrayList<String> output
	 * 
	 * For a queried User, return a contiguous list of stringified purchases,
	 * each purchase is structured as: quantity, share state, item purchased
	 */
	public ArrayList<String> getByUser(String userName) {
		// Search database for User object
		User user = userStringToObject(userName);
		ArrayList<String> output = new ArrayList<String>();
		HashSet<Purchase> purchases = null;
		// Check for valid User object
		if (user == null) {
			throw new RuntimeException("This user is not in our database!");
		}
		// Get the set of purchases under this user
		purchases = userAsKey.get(user);
		// If no purchases found, handle exception
		if (purchases == null) {
			throw new RuntimeException(
					"There are no purchases made by this user!");
		}
		// Iterate through all the purchases by this user
		for (Purchase purchase : purchases) {
			if (purchase != null) {
				// Convert the items, quantity and share status of each purchase
				// to string
				Item item = purchase.getItem();
				int qty = purchase.getQuantity();
				boolean isShared = (purchase.getUser().size() > 1);
				output.add(Integer.toString(qty));
				output.add(Boolean.toString(isShared));
				output.add(itemObjectToString(item));
			}
		}
		// Return array of strings
		return output;
	}

	/*
	 * Getter for Item object
	 * 
	 * @param String itemName
	 * 
	 * Retrieves the Item object by searching its name in database
	 */
	public Item itemStringToObject(String itemName) {
		return itemDatabase.get(itemName);
	}

	/*
	 * Getter for User object
	 * 
	 * @param String userName
	 * 
	 * Retrieves the User object by searching his/her name in database
	 */
	public User userStringToObject(String userName) {
		return userDatabase.get(userName);
	}

	/*
	 * Getter for Purchase object
	 * 
	 * @param String purchaseID
	 * 
	 * Retrieves the Purchase object by searching its ID in database
	 */
	public Purchase purchaseStringToObject(String purchaseID) {
		return purchaseDatabase.get(purchaseID);
	}

	/*
	 * Stringify Item Object
	 * 
	 * @param Item item
	 * 
	 * Returns a string representation of the Item object; its name
	 */
	public String itemObjectToString(Item item) throws IllegalArgumentException {
		// Check for valid Item object
		if (item == null) {
			throw new IllegalArgumentException(
					"Item provided in parameter is NULL.");
			// Check for valid Item name
		} else if (item.getItemName() == null) {
			throw new RuntimeException("Item ID is NULL.");
		}
		return item.getItemName();
	}

	/*
	 * Stringify User Object
	 * 
	 * @param User user
	 * 
	 * Returns a string representation of the User object; his/her name
	 */
	public String userObjectToString(User user) {
		// Check for valid user
		if (user == null) {
			throw new IllegalArgumentException(
					"User provided in parameter is NULL.");
			// Check for valid name
		} else if (user.getUserName() == null) {
			throw new RuntimeException("User ID is NULL.");
		}
		return user.getUserName();
	}

	/*
	 * Stringify Purchase Object
	 * 
	 * @param Purchase purchase
	 * 
	 * Returns a string representation of the Purchase object; its ID
	 */
	public String purchaseObjectToString(Purchase purchase) {
		// Check for valid purchase
		if (purchase == null) {
			throw new IllegalArgumentException(
					"Purchase provided in parameter is NULL.");
			// Check for valid ID
		} else if (purchase.getPurchaseID() == null) {
			throw new RuntimeException("Purchase ID is NULL.");
		}
		return purchase.getPurchaseID();
	}
}