package sg.edu.nus.comp.orbital.eventmanagement;

import java.util.HashSet;

public class Group {
	String groupID = null;
	HashSet<User> users = null;

	// Constructor
	public Group() {
		users = new HashSet<User>();
	}

	// Getter for users
	public HashSet<User> getUsers() {
		return users;
	}

	// Add user
	public void addUser(User user) {
		users.add(user);
	}

	// Remove user
	public void removeUser(User user) {
		users.remove(user);
	}

	// Get phone number?
	
	
}
