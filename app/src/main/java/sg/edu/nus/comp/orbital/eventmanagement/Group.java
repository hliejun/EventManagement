package sg.edu.nus.comp.orbital.eventmanagement;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.HashSet;

/*** User Group class ***/
public class Group implements Parcelable, Serializable, Comparable<Group> {
	protected String groupID = null;
	protected String groupName = null;
	protected HashSet<User> users = null;

	// Constructor
	public Group(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Invalid group name!");
		}
		groupName = name;
		users = new HashSet<User>();
	}

	// Parcelable constructor
	protected Group(Parcel in) {
        try {
            groupID = in.readString();
			groupName = in.readString();
            User[] userArray;
            userArray = in.createTypedArray(User.CREATOR);
            users = new HashSet<User>();
            for (User user : userArray) {
                users.add(user);
            }
        } catch (Exception e) {
            Log.e("GROUP PARCEL ERROR", e.toString());
            e.printStackTrace();
        }
	}

	// Implementation of Parcelable Creator
	public static final Creator<Group> CREATOR = new Creator<Group>() {
		@Override
		public Group createFromParcel(Parcel in) {
			return new Group(in);
		}

		@Override
		public Group[] newArray(int size) {
			return new Group[size];
		}
	};

	// Getter for users
	public HashSet<User> getUsers() {
		return users;
	}

	// Add user
	public void addUser(User user) {
		users.add(user);
	}

	// Add multiple users
	public void addUser(User[] users) {
		for (User user : users) {
			this.users.add(user);
		}
	}

	// Remove user
	public void removeUser(User user) {
		users.remove(user);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Invalid group name!");
		}
		groupName = name;
	}

	// Parcelable function
	@Override
	public int describeContents() {
		return 0;
	}

	// Construct Parcel
	@Override
	public void writeToParcel(Parcel out, int flags) {
        out.writeString(groupID);
		out.writeString(groupName);
        User[] userArray = new User[users.size()];
        out.writeTypedArray(users.toArray(userArray), 0);

	}

	@Override
	public int compareTo(Group another) {
		return this.getGroupName().compareTo(another.getGroupName());
	}
}
