package sg.edu.nus.comp.orbital.eventmanagement;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/*
 * Class User
 * 
 * A user class identifies a unique single person who is involved in a purchase, or debt.
 * 
 * A user is uniquely identified by his/her name.
 * 
 * A user may also be added using Facebook API, with an unique Facebook UID.
 * 
 * A user may not have a NULL user name, or Facebook UID. If a NULL is encountered in these fields,
 * error handling must be ensured in the external code. Before creating a user or changing his/her
 * name or Facebook UID, the external code must scan through the user database to ensure unicity of
 * user and his/her identity before allowing such changes.
 * 
 * @author Huang Lie Jun
 */
public class User implements Parcelable, Serializable, Comparable<User> {

	protected String userName = null;
	protected String facebookUID = null;
	protected String phoneNumber = null;
	protected Integer phoneNumThresh = 5;
	//protected Integer photoID = null;

	/*
	 * Constructor (For non-facebook users)
	 * 
	 * @param String name, String phoneNum, double cost
	 * 
	 * Initializes a user object with a unique name as ID, a phone number and
	 * default cost incurred = 0
	 */
	public User(String name, String phoneNum) throws IllegalArgumentException {
		// Checks for invalid argument
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException("Invalid Username Input!");
		}
		if (phoneNum == null || phoneNum.length() < phoneNumThresh) {
			throw new IllegalArgumentException("Invalid Phone Number Input!");
		}
		// Sets name
		userName = name;
		// Sets phoneNumber
		phoneNumber = phoneNum;
	}

	/*
	 * Constructor (For facebook users)
	 * 
	 * @param String name, String facebookIden, String phoneNum, double cost
	 * 
	 * Initializes a user object with a unique name, a unique facebook UID, a
	 * phone number and default cost incurred = 0
	 */
	public User(String name, String facebookIden, String phoneNum) {
		// Checks for invalid argument
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException("Invalid Username Input!");
		}
//		if (facebookIden == null || facebookIden.length() <= 0) {
//			throw new IllegalArgumentException("Invalid Facebook UID Input!");
//		}
		if (phoneNum == null || phoneNum.length() < phoneNumThresh) {
			throw new IllegalArgumentException("Invalid Phone Number Input!");
		}
		// Sets name of user, facebook UID, phone number
		userName = name;
		facebookUID = facebookIden;
		phoneNumber = phoneNum;
	}

    /*
     * Constructor (Parcelable)
     *
     * @param
     *
     *
     */
    protected User(Parcel in) {
        try {
            userName = in.readString();
            facebookUID = in.readString();
            phoneNumber = in.readString();
            phoneNumThresh = in.readInt();
            //photoID = in.readInt();
        } catch (Exception e) {
            Log.e("USER PARCEL ERROR", e.toString());
            e.printStackTrace();
        }
    }

	// Implementation of Parcelable Creator
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /*
	 * Getter for name of user
	 * 
	 * @ return String userName
	 * 
	 * Returns the unique name of the user as identification
	 */
	public String getUserName() {
		return userName;
	}

	/*
	 * Getter for Facebook UID of user
	 * 
	 * @ return String facebookUID
	 * 
	 * Returns the unique Facebook User ID of the user as identification for
	 * Facebook-related processes
	 */
	public String getFacebookUID() {
		return facebookUID;
	}

	/*
	 * Getter for phone number
	 * 
	 * @ return String phoneNumber
	 * 
	 * Returns the phone number of the user
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/*
	 * Setter for name of user
	 * 
	 * @param String name
	 * 
	 * Sets the name of the user with a string, assuming unicity.
	 */
	public void setUserName(String name) throws IllegalArgumentException {
		// Checks for invalid argument
		if (name == null || name.length() <= 0) {
			throw new IllegalArgumentException("Invalid Username Input!");
		}
		// Sets name
		userName = name;
	}

	/*
	 * Setter for Facebook UID of user
	 * 
	 * @param String facebookIden
	 * 
	 * Sets the Facebook UID of the user with a string, assuming unicity.
	 */
	public void setFacebookUID(String facebookIden)
			throws IllegalArgumentException {
		// Checks for invalid argument
		if (facebookIden == null || facebookIden.length() <= 0) {
			throw new IllegalArgumentException("Invalid Facebook UID Input!");
		}
		// Sets UID
		facebookUID = facebookIden;
	}

	/*
	 * Setter for phone number
	 * 
	 * @param String phoneNum
	 * 
	 * Sets the phone number of user
	 */
	public void setPhoneNumber(String phoneNum) throws IllegalArgumentException {
		// Checks for invalid argument
		if (phoneNum == null || phoneNum.length() < phoneNumThresh) {
			throw new IllegalArgumentException("Invalid Phone Number Input!");
		}
		// Sets phone number
		phoneNumber = phoneNum;
	}

	// Setter for photoID
//	public void setPhotoID (int photoID) {
//		this.photoID = photoID;
//	}

	// Getter for photoID
//	public int getPhotoID () {
//		return photoID;
//	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
        out.writeString(userName);
        out.writeString(facebookUID);
        out.writeString(phoneNumber);
        out.writeInt(phoneNumThresh);
//      out.writeInt(photoID);
    }

	@Override
	public int compareTo(User another) {
		return this.getUserName().compareTo(another.getUserName());
	}
}
