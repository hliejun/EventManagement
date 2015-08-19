package sg.edu.nus.comp.orbital.eventmanagement;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class AddUsersAdapter extends RecyclerView.Adapter<AddUsersAdapter.ViewHolder> {

    // Data Sets: Filter using these sets, then apply changes to Display Sets
    protected Group[] groupData;
    protected User[] userData;
    protected ContactPair[] contactData;

    // Display Sets: Add, Remove, Move using these sets
    protected ArrayList<Group> groupVisible;
    protected ArrayList<User> userVisible;
    protected ArrayList<ContactPair> contactVisible;

    protected int colorCounter = 0;

    //TODO: ADD IN SELECTION FLAG HANDLING
    //TODO: ADD IN FEED SELECTION FLAG BACK TO ACTIVITY
    //TODO: AT ACTIVITY LEVEL, CONSTRUCT ARRAY OF USERS
    //TODO: REPEAT FOR ALL OTHER FRAGMENTS

    // Matches Display Set Index (Key) to Data Set Index (Value)
    // Used to access Sparse Boolean Array to set flags
    // Updated when changes are made to Display Set after filter changes
    protected HashMap<Integer, Integer> groupIndexMap = null;
    protected HashMap<Integer, Integer> userIndexMap = null;
    protected HashMap<Integer, Integer> contactIndexMap = null;

    protected String typeString = null;

    protected static CheckBox lastCheckedGroup = null;
    protected static int lastCheckedPosGroup = 0;

    protected static CheckBox lastCheckedUser = null;
    protected static int lastCheckedPosUser = 0;

    protected static CheckBox lastCheckedContacts = null;
    protected static int lastCheckedPosContacts = 0;

    // Accessed when checkboxes are toggled (on checkbox change)
    protected SparseBooleanArray groupFlag = null;
    protected SparseBooleanArray userFlag = null;
    protected SparseBooleanArray contactFlag = null;


    public AddUsersAdapter(Group[] dataArgs){
        groupFlag = new SparseBooleanArray();
        groupData = dataArgs;
        groupVisible = new ArrayList<Group>();
        int i = 0;
        groupIndexMap = new HashMap<Integer, Integer>();
        for (Group group: dataArgs) {
            groupVisible.add(group);
            groupIndexMap.put(i, i);
            i++;
        }
        typeString = "Group";
    }
    public AddUsersAdapter(User[] dataArgs){
        userFlag = new SparseBooleanArray();
        userData = dataArgs;
        userVisible = new ArrayList<User>();
        int i = 0;
        userIndexMap = new HashMap<Integer, Integer>();
        for (User user: dataArgs) {
            userVisible.add(user);
            userIndexMap.put(i, i);
            i++;
        }
        typeString = "User";
    }
    public AddUsersAdapter(ContactPair[] dataArgs){
        contactFlag = new SparseBooleanArray();
        contactData = dataArgs;
        contactVisible = new ArrayList<ContactPair>();
        int i = 0;
        contactIndexMap = new HashMap<Integer, Integer>();
        for (ContactPair contact: dataArgs) {
            contactVisible.add(contact);
            contactIndexMap.put(i, i);
            i++;
        }
        typeString = "ContactPair";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_view, parent, false);
        int[] colors = parent.getContext().getResources().getIntArray(R.array.palecolors);
        int color = colors[colorCounter%colors.length];
        //  view.setBackgroundColor(color);
        if (colorCounter > 10 * colors.length) {
            colorCounter = colorCounter%colors.length;
        }
        colorCounter++;
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;


        int[] colors = holder.imageView.getResources().getIntArray(R.array.palecolors);
        int color = colors[position%colors.length];

        switch (typeString) {
            case "Group": {
                //TODO: implement onClick function on textView
                holder.imageView.setImageResource(R.drawable.group_icon);
                holder.imageView.setColorFilter(color);
                holder.textView.setText(groupVisible.get(position).getGroupName());
                holder.checkBox.setChecked(groupFlag.get(groupIndexMap.get(position), false));


//                if(position == 0 && groupFlag.get(groupIndexMap.get(position), false) && holder.checkBox.isChecked()) {
//                    lastCheckedGroup = holder.checkBox;
//                    lastCheckedPosGroup = groupIndexMap.get(0);
//                }
                holder.checkBox.setTag(position);
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.contact_checkbox) {
                            CheckBox cb = (CheckBox) v;
                            int clickedPos = (Integer) cb.getTag();
//                            Log.d("TAG INDICATOR_VISIBLE", Integer.toString(clickedPos));
//                            if (groupIndexMap.get(clickedPos) == null) {
//                                Log.d("TAG INDICATOR_ORIGINAL", "NULL");
//                            } else {
//                                Log.d("TAG INDICATOR_ORIGINAL", Integer.toString(groupIndexMap.get(clickedPos)));
//                            }

                            if (groupIndexMap.get(clickedPos) != null && groupFlag.get(groupIndexMap.get(clickedPos), false)) {
                                //if (lastCheckedGroup != null) {
                                    groupFlag.delete(groupIndexMap.get(clickedPos));
                               // }
//                                lastCheckedGroup = cb;
//                                lastCheckedPosGroup = groupIndexMap.get(clickedPos);
                            } else if (groupIndexMap.get(clickedPos) != null){
//                                lastCheckedGroup = null;
                                groupFlag.put(groupIndexMap.get(clickedPos), true);
                            }
                        }
                    }
                });
                break;
            }
            case "User": {
                //TODO: implement onClick function on textView
                holder.imageView.setImageResource(R.drawable.user_icon);
                holder.imageView.setColorFilter(color);
                holder.textView.setText(userVisible.get(position).getUserName());
                if (userIndexMap.get(position) != null) {
                    holder.checkBox.setChecked(userFlag.get(userIndexMap.get(position), false));
                }
                holder.checkBox.setTag(position);
                //holder.itemView.setActivated(userFlag.get(position, false));

//                if(userIndexMap.get(position) == 0 && userFlag.get(0, false) && holder.checkBox.isChecked()) {
//                    lastCheckedUser = holder.checkBox;
//                    lastCheckedPosUser = 0;
//                }

                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        int clickedPos = ((Integer) cb.getTag()).intValue();

                        if (userFlag.get(userIndexMap.get(clickedPos), false)) {
                            //if (lastCheckedUser != null) {
                                userFlag.delete(userIndexMap.get(clickedPos));
                            //}
//                            lastCheckedUser = cb;
//                            lastCheckedPosUser = userIndexMap.get(clickedPos);
                        } else if (userIndexMap.get(clickedPos) != null) {
//                            lastCheckedUser = null;
                            userFlag.put(userIndexMap.get(clickedPos), true);
                        }
                    }
                });
                break;
            }
            case "ContactPair": {
                //TODO: implement onClick function on textView
                holder.imageView.setImageResource(R.drawable.contacts_icon);
                holder.imageView.setColorFilter(color);
                holder.textView.setText(contactVisible.get(position).getName());
                if (contactIndexMap.get(position) != null) {
                    holder.checkBox.setChecked(contactFlag.get(contactIndexMap.get(position)));
                }
                holder.checkBox.setTag(new Integer(position));
                //holder.itemView.setActivated(contactFlag.get(position));

//                if(contactIndexMap.get(position) != null && contactIndexMap.get(position) == 0 && contactFlag.get(0, false) && holder.checkBox.isChecked()) {
//                    lastCheckedContacts = holder.checkBox;
//                    lastCheckedPosContacts = 0;
//                }

                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        int clickedPos = ((Integer) cb.getTag()).intValue();

                        if (contactIndexMap.get(clickedPos) != null && contactFlag.get(contactIndexMap.get(clickedPos), false)) {
                            //if (lastCheckedContacts != null) {
                                contactFlag.delete(contactIndexMap.get(clickedPos));
                            //}
//                            lastCheckedContacts = cb;
//                            lastCheckedPosContacts = contactIndexMap.get(clickedPos);
                        } else if (contactIndexMap.get(clickedPos) != null) {
                            //lastCheckedContacts = null;
                            contactFlag.put(contactIndexMap.get(clickedPos), true);
                        }
                    }
                });
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        switch (typeString) {
            case "Group": {
                if (groupVisible == null) {
                    return 0;
                }
                return groupVisible.size();
            }
            case "User": {
                if (userVisible == null) {
                    return 0;
                }
                return userVisible.size();
            }
            case "ContactPair": {
                if (contactVisible == null) {
                    return 0;
                }
                return contactVisible.size();
            }
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView;
        protected TextView textView;
        protected CheckBox checkBox;
        protected View currentView;
        protected ViewHolder(View view) {
            super(view);
            currentView = view;
            imageView = (ImageView) view.findViewById(R.id.contact_picture);
            textView = (TextView) view.findViewById(R.id.contact_name);
            checkBox = (CheckBox) view.findViewById(R.id.contact_checkbox);
            Typeface type = Typeface.createFromAsset(view.getContext().getAssets(),
                    "fonts/GoodDog.ttf");
            textView.setTypeface(type);
        }
    }

    public SparseBooleanArray getGroupFlag() {
        return groupFlag;
    }

    public SparseBooleanArray getUserFlag() {
        return userFlag;
    }

    public SparseBooleanArray getContactFlag() {
        return contactFlag;
    }

    public void setGroupFlag(SparseBooleanArray flag) {
        groupFlag = flag;
    }

    public void setUserFlag(SparseBooleanArray flag) {
        userFlag = flag;
    }

    public void setContactFlag(SparseBooleanArray flag) {
        contactFlag = flag;
    }

    public Group removeGroup(int position) {
        final Group group = groupVisible.remove(position);
        groupIndexMap.remove(position);
        // groupIndexMap.put(position, -1);
        notifyItemRemoved(position);
       // Log.d("DEBUGGING", "removeGroup is called");
        return group;
    }

    public void addGroup(int position, Group group) {
        groupVisible.add(position, group);
        //int originalIndex = Arrays.asList(groupData).indexOf(group);
        int originalIndex = -1;
        int count = 0;
        for (Group originalGroup : groupData) {
            if (group.getGroupName().equals(originalGroup.getGroupName()) /*&& group.getUsers()
                    .size() == originalGroup.getUsers().size()*/) {
                originalIndex = count;
                break;
            }
            count++;
        }
        if (originalIndex != -1) {
            groupIndexMap.put(position, originalIndex);
        }
        notifyItemInserted(position);
        //Log.d("DEBUGGING", "addGroup is called");
    }

    public void moveGroup(int fromPosition, int toPosition) {
        final Group group = groupVisible.remove(fromPosition);
        groupVisible.add(toPosition, group);
        int originalIndex = groupIndexMap.remove(fromPosition);
        groupIndexMap.put(toPosition, originalIndex);
        notifyItemMoved(fromPosition, toPosition);
        //Log.d("DEBUGGING", "moveGroup is called");
    }

    public User removeUser(int position) {
        final User user = userVisible.remove(position);
        userIndexMap.remove(position);
        notifyItemRemoved(position);
        return user;
    }

    public void addUser(int position, User user) {
        userVisible.add(position, user);
        int originalIndex = -1;
        int count = 0;
        for (User originalUser : userData) {
            if (user.getUserName().equals(originalUser.getUserName()) && user.getPhoneNumber()
                    .equals(originalUser.getPhoneNumber())) {
                originalIndex = count;
                break;
            }
            count++;
        }
        if (originalIndex != -1) {
            userIndexMap.put(position, originalIndex);
        }
        notifyItemInserted(position);
    }

    public void moveUser(int fromPosition, int toPosition) {
        final User user = userVisible.remove(fromPosition);
        int originalIndex = userIndexMap.remove(fromPosition);
        userIndexMap.put(toPosition, originalIndex);
        userVisible.add(toPosition, user);
        notifyItemMoved(fromPosition, toPosition);
    }

    public ContactPair removeContact(int position) {
        final ContactPair contact = contactVisible.remove(position);
        contactIndexMap.remove(position);
        notifyItemRemoved(position);
        return contact;
    }

    public void addContact(int position, ContactPair contact) {
        contactVisible.add(position, contact);
        int originalIndex = -1;//Arrays.asList(contactData).indexOf(contact);
        int count = 0;
        for (ContactPair contactPair : contactData) {
            if (contactPair.getName().equals(contact.getName())) {
                originalIndex = count;
                break;
            }
            count++;
        }
        if (originalIndex != -1) {
            contactIndexMap.put(position, originalIndex);
        }
        notifyItemInserted(position);
    }

    public void moveContact(int fromPosition, int toPosition) {
        final ContactPair contact = contactVisible.remove(fromPosition);
        int originalIndex = contactIndexMap.remove(fromPosition);
        contactIndexMap.put(toPosition, originalIndex);
        contactVisible.add(toPosition, contact);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateGroup(ArrayList<Group> groupList) {
       //Log.d("DEBUGGING", "animateGroup is called");
        applyAndAnimateRemovalsGroup(groupList);
        applyAndAnimateAdditionsGroup(groupList);
        applyAndAnimateMovedItemsGroup(groupList);
    }

    public void animateUser(ArrayList<User> userList) {
        applyAndAnimateRemovalsUser(userList);
        applyAndAnimateAdditionsUser(userList);
        applyAndAnimateMovedItemsUser(userList);
    }

    public void animateContact(ArrayList<ContactPair> contactList) {
        applyAndAnimateRemovalsContact(contactList);
        applyAndAnimateAdditionsContact(contactList);
        applyAndAnimateMovedItemsContact(contactList);
    }


    public void applyAndAnimateRemovalsGroup(ArrayList<Group> filteredGroupList) {
        for (int count = groupVisible.size() - 1; count >= 0; --count) {
            final Group group = groupVisible.get(count);
            if (!filteredGroupList.contains(group)) {
                removeGroup(count);
            }
        }
    }

    public void applyAndAnimateAdditionsGroup(ArrayList<Group> filteredGroupList) {
        for (int count = 0; count < filteredGroupList.size(); ++count) {
            final Group group = filteredGroupList.get(count);
            //Log.d("DEBUGGING", "applyAndAnimatedAdditionsGroup is called");
            if (!groupVisible.contains(group)) {
                //Log.d("DEBUGGING", "addGroup is going to be called");
                addGroup(count, group);
            }
        }
    }

    public void applyAndAnimateMovedItemsGroup(ArrayList<Group> filteredGroupList) {
        for (int toPosition = filteredGroupList.size() - 1; toPosition >= 0; --toPosition) {
            final Group group = filteredGroupList.get(toPosition);
            final int fromPosition = groupVisible.indexOf(group);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveGroup(fromPosition, toPosition);
            }
        }
    }


    public void applyAndAnimateRemovalsUser(ArrayList<User> filteredUserList) {
        for (int count = userVisible.size() - 1; count >= 0; --count) {
            final User user = userVisible.get(count);
            if (!filteredUserList.contains(user)) {
                removeUser(count);
            }
        }
    }

    public void applyAndAnimateAdditionsUser(ArrayList<User> filteredUserList) {
        for (int count = 0; count < filteredUserList.size(); ++count) {
            final User user = filteredUserList.get(count);
            if (!userVisible.contains(user)) {
                addUser(count, user);
            }
        }
    }

    public void applyAndAnimateMovedItemsUser(ArrayList<User> filteredUserList) {
        for (int toPosition = filteredUserList.size() - 1; toPosition >= 0; --toPosition) {
            final User user = filteredUserList.get(toPosition);
            final int fromPosition = userVisible.indexOf(user);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveUser(fromPosition, toPosition);
            }
        }
    }



    public void applyAndAnimateRemovalsContact(ArrayList<ContactPair> filteredContactList) {
        for (int count = contactVisible.size() - 1; count >= 0; --count) {
            final ContactPair contact = contactVisible.get(count);
            if (!filteredContactList.contains(contact)) {
                removeContact(count);
            }
        }
    }

    public void applyAndAnimateAdditionsContact(ArrayList<ContactPair> filteredContactList) {
        for (int count = 0; count < filteredContactList.size(); ++count) {
            final ContactPair contact = filteredContactList.get(count);
            if (!contactVisible.contains(contact)) {
                addContact(count, contact);
            }
        }
    }

    public void applyAndAnimateMovedItemsContact(ArrayList<ContactPair> filteredContactList) {
        for (int toPosition = filteredContactList.size() - 1; toPosition >= 0; --toPosition) {
            final ContactPair contact = filteredContactList.get(toPosition);
            final int fromPosition = contactVisible.indexOf(contact);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveContact(fromPosition, toPosition);
            }
        }
    }

    public void setFilterGroup(String query) {
        final ArrayList<Group> filteredGroupList = filterGroup(groupData, query);
        //Log.d("DEBUGGING", "setFilterGroup is called");
        animateGroup(filteredGroupList);
    }

    public void setFilterUser(String query) {
        final ArrayList<User> filteredUserList = filterUser(userData, query);
        animateUser(filteredUserList);
    }

    public void setFilterContact(String query) {
        final ArrayList<ContactPair> filteredContactList = filterContact(contactData, query);
        animateContact(filteredContactList);
    }

    public ArrayList<Group> filterGroup(Group[] groupData, String query) {
        final ArrayList<Group> filteredGroupList = new ArrayList<Group>();
        if(query == null) {
            //groupIndexMap.clear();
            int i = 0;
            for (Group group : groupData) {
                filteredGroupList.add(group);
                groupIndexMap.put(i, i);
                i++;
            }
        } else {
            //groupIndexMap.clear();
            query = query.toLowerCase();
            int originalIndex = 0;
            int visibleIndex = 0;
            for (Group group : groupData) {
                final String groupName = group.getGroupName().toLowerCase();
                if (groupName.contains(query)) {
                    filteredGroupList.add(group);
                    groupIndexMap.put(visibleIndex, originalIndex);
                    visibleIndex++;
                }
                originalIndex++;
            }
        }
        return filteredGroupList;
    }

    public ArrayList<User> filterUser(User[] userData, String query) {
        final ArrayList<User> filteredUserList = new ArrayList<User>();
        if(query == null) {
            for (User user : userData) {
                filteredUserList.add(user);
            }
        } else {
            query = query.toLowerCase();
            for (User user : userData) {
                final String userName = user.getUserName().toLowerCase();
                if (userName.contains(query)) {
                    filteredUserList.add(user);
                }
            }
        }
        return filteredUserList;
    }

    public ArrayList<ContactPair> filterContact(ContactPair[] contactData, String query) {
        final ArrayList<ContactPair> filteredContactList = new ArrayList<ContactPair>();
        if(query == null) {
            for (ContactPair contact : contactData) {
                filteredContactList.add(contact);
            }
        } else {
            query = query.toLowerCase();
            for (ContactPair contact : contactData) {
                final String contactName = contact.getName().toLowerCase();
                if (contactName.contains(query)) {
                    filteredContactList.add(contact);
                }
            }
        }
        return filteredContactList;
    }

}
