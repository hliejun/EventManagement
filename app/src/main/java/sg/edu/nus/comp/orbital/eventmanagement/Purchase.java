package sg.edu.nus.comp.orbital.eventmanagement;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashSet;

/*
 * Class Purchase
 * 
 * A wrapper class that wraps a set of users (size >= 1) with a particular unique item and quantity.
 * 
 * Each purchase is a unique relation between user(s) and item. This is identified by purchaseID.
 * 
 * A purchase has an unique set of users, an unique item being purchased, and an unique quantity.
 * 
 * A purchase cannot have NULL as its set of users, or as its item, or quantity = 0. If returns NULL
 * in any of these fields, the purchase must be considered invalid and should throw an exception. This
 * error handling must be dealt with in external codes that utilizes the purchase class.
 * 
 * @author Huang Lie Jun
 */
public class Purchase implements Parcelable{
	protected HashSet<User> users = new HashSet<User>();
	protected Item item = null;
	protected int quantity = 0;
	protected String purchaseID = null;

	/*
	 * Constructor (For single-user purchase)
	 * 
	 * @param User mUser, Item mItem, int mQuantity
	 * 
	 * Initializes a single-user purchase by creating a purchase object
	 */
	public Purchase(User mUser, Item mItem, int mQuantity)
			throws IllegalArgumentException {
		// Check for invalid arguments
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		if (mItem == null) {
			throw new IllegalArgumentException("Invalid Item!");
		}
		if (mQuantity <= 0) {
			throw new IllegalArgumentException("Invalid Quantity!");
		}
		// Add user to user set
		users.add(mUser);
		// Associate item with the purchase
		item = mItem;
		// Set quantity
		quantity = mQuantity;
		// Set purchase ID on object creation
		purchaseID = Integer.toString(this.hashCode());
	}

	/*
	 * Constructor (For multiple-user purchase, cost-sharing instances)
	 * 
	 * @param HashSet<User> mUser, Item mItem, int mQuantity
	 * 
	 * Initializes a multi-user purchase by creating a purchase object
	 */
	public Purchase(HashSet<User> mUser, Item mItem, int mQuantity)
			throws IllegalArgumentException {
		// Check for invalid arguments
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		if (mItem == null) {
			throw new IllegalArgumentException("Invalid Item!");
		}
		if (mQuantity <= 0) {
			throw new IllegalArgumentException("Invalid Quantity!");
		}
		// Add every user in the set into the purchase
		for (User user : mUser) {
			if (user != null) {
				users.add(user);
			}
		}
		// Associate item with the purchase
		item = mItem;
		// Set quantity
		quantity = mQuantity;
		// Set purchase ID on object creation
		purchaseID = Integer.toString(this.hashCode());
	}

	// Parcelable Constructor
	protected Purchase(Parcel in) {
        try {
            User[] userArray;
            userArray = in.createTypedArray(User.CREATOR);
            users = new HashSet<User>();
            for (User user : userArray) {
                users.add(user);
            }
            item = in.readParcelable(Item.class.getClassLoader());
            quantity = in.readInt();
            purchaseID = in.readString();
        } catch (Exception e) {
            Log.e("PURCHASE PARCEL ERROR", e.toString());
            e.printStackTrace();
        }
	}

	public static final Creator<Purchase> CREATOR = new Creator<Purchase>() {
		@Override
		public Purchase createFromParcel(Parcel in) {
			return new Purchase(in);
		}

		@Override
		public Purchase[] newArray(int size) {
			return new Purchase[size];
		}
	};

	/*
         * Getter for user(s) of the purchase
         *
         * @return HashSet<User> users
         *
         * Returns a set of users involved in the particular purchase
         */
	public HashSet<User> getUser() {
		return users;
	}

	/*
	 * Getter for item of the purchase
	 * 
	 * @return Item item
	 * 
	 * Returns the item that is being purchased in the particular purchase
	 */
	public Item getItem() {
		return item;
	}

	/*
	 * Getter for quantity of item being purchased
	 * 
	 * @return int quantity
	 * 
	 * Returns the number (whole, rational) of items purchased
	 */
	public int getQuantity() {
		return quantity;
	}

	/*
	 * Getter for ID of the purchase
	 * 
	 * @return String purchaseID
	 * 
	 * Returns the purchase ID (the object Hash Code in string) of purchase
	 */
	public String getPurchaseID() {
		return purchaseID;
	}

	/*
	 * Adder (single) for user of the purchase
	 * 
	 * @param User mUser
	 * 
	 * Adds the user provided as argument into the set of users of the purchase
	 */
	public void addUser(User mUser) throws IllegalArgumentException {
		// Check for invalid argument
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		// Add user to set of users
		users.add(mUser);
	}

	/*
	 * Adder (multiple) for users of the purchase
	 * 
	 * @param HashSet<User> mUser
	 * 
	 * Adds set of users provided as argument into the set of users of the
	 * purchase
	 */
	public void addUser(HashSet<User> mUser) throws IllegalArgumentException {
		// Check for invalid argument
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		// Add users to set of users
		for (User user : mUser) {
			if (user != null) {
				users.add(user);
			}
		}
	}

	/*
	 * Remover (single) for user of the purchase
	 * 
	 * @param User mUser
	 * 
	 * Removes the user provided as argument from the set of users of the
	 * purchase
	 */
	public void removeUser(User mUser) throws IllegalArgumentException {
		// Check for invalid argument
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		// Ensures purchase is still valid after removal
		if (users.size() - 1 <= 0) {
			throw new RuntimeException(
					"Cannot remove user from purchase as user group size must be positive!");
		}
		// Remove user from set of users
		users.remove(mUser);
	}

	/*
	 * Remover (multiple) for users of the purchase
	 * 
	 * @param HashSet<User> mUser
	 * 
	 * Removes set of users provided as argument from the set of users of the
	 * purchase
	 */
	public void removeUser(HashSet<User> mUser) throws IllegalArgumentException {
		// Check for invalid argument
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		// Ensures purchase is still valid after removal
		if (users.size() - mUser.size() <= 0) {
			throw new RuntimeException(
					"Cannot remove user from purchase as user group size must be positive!");
		}
		// Remove users from set of users
		for (User user : mUser) {
			if (user != null) {
				users.remove(user);
			}
		}
	}

	/*
	 * Setter (single) for user of the purchase
	 * 
	 * @param User mUser
	 * 
	 * Sets the user provided as argument as the user of the purchase
	 */
	public void setUser(User mUser) throws IllegalArgumentException {
		// Check for invalid argument
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		// Clear previous and sets user as user of purchase
		users.clear();
		users.add(mUser);
	}

	/*
	 * Setter (multiple) for users of the purchase
	 * 
	 * @param HashSet<User> mUser
	 * 
	 * Sets the set of users provided as argument as the set of users of the
	 * purchase
	 */
	public void setUser(HashSet<User> mUser) throws IllegalArgumentException {
		// Check for invalid argument
		if (mUser == null) {
			throw new IllegalArgumentException("Invalid User!");
		}
		// Clear previous and sets users as set of users of purchase
		users.clear();
		users.addAll(mUser);
	}

	/*
	 * Setter for item of purchase
	 * 
	 * @param Item mItem
	 * 
	 * Sets the item of purchase
	 */
	public void setItem(Item mItem) throws IllegalArgumentException {
		// Check for invalid argument
		if (mItem == null) {
			throw new IllegalArgumentException("Invalid Item!");
		}
		// Sets unique item of purchase
		item = mItem;
	}

	/*
	 * Setter for quantity of purchase
	 * 
	 * @param int mQuantity
	 * 
	 * Sets the quantity of purchase
	 */
	public void setQuantity(int mQuantity) throws IllegalArgumentException {
		// Check for invalid argument
		if (mQuantity <= 0) {
			throw new IllegalArgumentException("Invalid Quantity!");
		}
		// Sets unique quantity of item purchased in this purchase
		quantity = mQuantity;
	}

	// Parcelable function
	@Override
	public int describeContents() {
		return 0;
	}

	// Construct Parcel
	@Override
	public void writeToParcel(Parcel out, int flags) {
        User[] userArray = new User[users.size()];
        out.writeTypedArray(users.toArray(userArray), 0);
		out.writeParcelable(item, flags);
		out.writeInt(quantity);
		out.writeString(purchaseID);
	}
}