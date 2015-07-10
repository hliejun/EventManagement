package sg.edu.nus.comp.orbital.eventmanagement;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/*
 * Class Item
 * 
 * An Item represents an object that can be procured, or purchased by a user.
 * 
 * It has a unique name, which identifies itself. It is categorizable by type.
 * 
 * Upon creation of an item object, or upon changing its name, the external code
 * must ensure that it scans through the item database to check for unicity. Exception
 * handling must also be implemented for null fields, as an item cannot have NULL as name
 * or its type. 
 * 
 * @author Huang Lie Jun
 */
public class Item implements Parcelable{
	protected String itemName = null;
	protected String itemType = null;
	protected double costPerUnit = 0;

	/*
	 * Constructor
	 * 
	 * @param String name, String type, double cost
	 * 
	 * Initializes an item object with a unique name, a type and its unit cost
	 */
	public Item(String name, String type, double cost)
			throws IllegalArgumentException {
		// Checks for invalid argument
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException("Invalid Item Name Input!");
		}
		if (type == null || type.length() <= 0) {
			throw new IllegalArgumentException("Invalid Item Type Input!");
		}
		if (cost <= 0) {
			throw new IllegalArgumentException("Invalid Item Cost Input!");
		}
		// Set variables
		itemName = name;
		itemType = type;
		costPerUnit = cost;
	}

	// Parcelable Constructor
    protected Item(Parcel in) {
        try {
            itemName = in.readString();
            itemType = in.readString();
            costPerUnit = in.readDouble();
        } catch (Exception e) {
            Log.e("ITEM PARCEL ERROR", e.toString());
            e.printStackTrace();
        }
    }

	// Implementation of Parcelable Creator
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /*
	 * Getter for item name
	 * 
	 * @return String itemName
	 * 
	 * Returns the unique name of the item
	 */
	public String getItemName() {
		return itemName;
	}

	/*
	 * Getter for item type
	 * 
	 * @return String itemType
	 * 
	 * Returns the type of the item
	 */
	public String getItemType() {
		return itemType;
	}

	/*
	 * Getter for item unit cost
	 * 
	 * @return double costPerUnit
	 * 
	 * Returns the unit cost of the item
	 */
	public double getItemCost() {
		return costPerUnit;
	}

	/*
	 * Setter for item name
	 * 
	 * @param String name
	 * 
	 * Sets the unique name of the item, assuming unicity
	 */
	public void setItemName(String name) throws IllegalArgumentException {
		// Checks for invalid argument
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException("Invalid Item Name Input!");
		}
		// Set name
		itemName = name;
	}

	/*
	 * Setter for item type
	 * 
	 * @param String type
	 * 
	 * Sets the type of the item
	 */
	public void setItemType(String type) throws IllegalArgumentException {
		// Checks for invalid argument
		if (type == null || type.length() <= 0) {
			throw new IllegalArgumentException("Invalid Item Type Input!");
		}
		// Set type
		itemType = type;
	}

	/*
	 * Setter for item cost
	 * 
	 * @param double cost
	 * 
	 * Sets the cost of the item
	 */
	public void setItemCost(double cost) throws IllegalArgumentException {
		// Checks for invalid argument
		if (cost <= 0) {
			throw new IllegalArgumentException("Invalid Item Cost Input!");
		}
		// Set cost
		costPerUnit = cost;
	}

	// Parcelable function
	@Override
	public int describeContents() {
		return 0;
	}

	// Construct parcel
	@Override
	public void writeToParcel(Parcel out, int flags) {
        out.writeString(itemName);
        out.writeString(itemType);
        out.writeDouble(costPerUnit);
	}
}