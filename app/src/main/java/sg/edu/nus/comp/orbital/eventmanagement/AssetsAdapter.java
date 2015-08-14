package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.ViewHolder> {

    // Data Sets: Filter using these sets, then apply changes to Display Sets
    protected ArrayList<Debt> debtData;
    //protected ArrayList<Debt> assetData;

    // Display Sets: Add, Remove, Move using these sets
    protected ArrayList<Debt> debtDataVisible;
    //protected ArrayList<Debt> assetDataVisible;

    // Matches Display Set Index (Key) to Data Set Index (Value)
    // Used to access Sparse Boolean Array to set flags
    // Updated when changes are made to Display Set after filter changes
    protected HashMap<Integer, Integer> debtIndexMap = null;
    //protected HashMap<Integer, Integer> assetIndexMap = null;

    protected String typeString = null;
    private DecimalFormat df = new DecimalFormat("#.00");

//    protected static CheckBox lastCheckedGroup = null;
//    protected static int lastCheckedPosGroup = 0;
//
//    protected static CheckBox lastCheckedUser = null;
//    protected static int lastCheckedPosUser = 0;
//
//    protected static CheckBox lastCheckedContacts = null;
//    protected static int lastCheckedPosContacts = 0;

    // Accessed when checkboxes are toggled (on checkbox change)
//    protected SparseBooleanArray groupFlag = null;
//    protected SparseBooleanArray userFlag = null;
//    protected SparseBooleanArray contactFlag = null;


    public AssetsAdapter(ArrayList<Debt> dataArgs){
        //groupFlag = new SparseBooleanArray();
        debtData = dataArgs;
        debtDataVisible = new ArrayList<Debt>();
        int i = 0;
        debtIndexMap = new HashMap<Integer, Integer>();
        for (Debt debt: dataArgs) {
            debtDataVisible.add(debt);
            debtIndexMap.put(i, i);
            i++;
        }
        //typeString = "Group";
    }
//    public DebtsAdapter(User[] dataArgs){
//        userFlag = new SparseBooleanArray();
//        userData = dataArgs;
//        userVisible = new ArrayList<User>();
//        int i = 0;
//        userIndexMap = new HashMap<Integer, Integer>();
//        for (User user: dataArgs) {
//            userVisible.add(user);
//            userIndexMap.put(i, i);
//            i++;
//        }
//        //typeString = "User";
//    }
//    public DebtsAdapter(ContactPair[] dataArgs){
//        contactFlag = new SparseBooleanArray();
//        contactData = dataArgs;
//        contactVisible = new ArrayList<ContactPair>();
//        int i = 0;
//        contactIndexMap = new HashMap<Integer, Integer>();
//        for (ContactPair contact: dataArgs) {
//            contactVisible.add(contact);
//            contactIndexMap.put(i, i);
//            i++;
//        }
//        typeString = "ContactPair";
//    }

//    public AddUsersAdapter(Group[] arg1, User[] arg2, ContactPair[] arg3){
//        groupFlag = new SparseBooleanArray();
//        userFlag = new SparseBooleanArray();
//        contactFlag = new SparseBooleanArray();
//        groupData = arg1;
//        userData = arg2;
//        contactData = arg3;
//        groupVisible = new ArrayList<Group>();
//        userVisible = new ArrayList<User>();
//        contactVisible = new ArrayList<ContactPair>();
//        int i = 0;
//        groupIndexMap = new HashMap<Integer, Integer>();
//        userIndexMap = new HashMap<Integer, Integer>();
//        contactIndexMap = new HashMap<Integer, Integer>();
//        for (ContactPair contact: dataArgs) {
//            contactVisible.add(contact);
//            contactIndexMap.put(i, i);
//            i++;
//        }
//        typeString = "ContactPair";
//    }
//TODO: USE COMBINED CONSTRUCTOR AND SETTLE ALL AT ONCE

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.debt_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
//        switch (typeString) {
//            case "Group": {
                //TODO: implement onClick function on textView
                //holder.checkBox.setTag(position);
                holder.imageView.setImageResource(R.drawable.assets_tab);
                holder.textView.setText(debtDataVisible.get(position).getDebtor().getUserName());
                holder.costView.setText("$" + df.format(debtDataVisible.get(position).getDebtAmt()));
        //Log.d("DEBUGGING", groupIndexMap.get(position).toString());
                //holder.checkBox.setChecked(groupFlag.get(groupIndexMap.get(position), false));
                //holder.itemView.setActivated(groupFlag.get(position, false));

//                if(position == 0 && groupFlag.get(groupIndexMap.get(position), false) && holder.checkBox.isChecked()) {
//                    lastCheckedGroup = holder.checkBox;
//                    lastCheckedPosGroup = 0;
//                }

//                holder.checkBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (v.getId() == R.id.contact_checkbox) {
//                            CheckBox cb = (CheckBox) v;
//                            int clickedPos = (Integer) cb.getTag();
//                            Log.d("TAG INDICATOR_VISIBLE", Integer.toString(clickedPos));
//                            if (debtIndexMap.get(clickedPos) == null) {
//                                Log.d("TAG INDICATOR_ORIGINAL", "NULL");
//                            } else {
//                                Log.d("TAG INDICATOR_ORIGINAL", Integer.toString(debtIndexMap.get
//                                        (clickedPos)));
//                            }
//
////                            if (debtIndexMap.get(clickedPos) != null && groupFlag.get(debtIndexMap.get(clickedPos), false)) {
////                                if (lastCheckedGroup != null) {
////                                    groupFlag.delete(groupIndexMap.get(clickedPos));
////                                }
////                                lastCheckedGroup = cb;
////                                lastCheckedPosGroup = groupIndexMap.get(clickedPos);
////                            } else {
////                                lastCheckedGroup = null;
////                                groupFlag.put(groupIndexMap.get(clickedPos), true);
////                            }
//                        }
//                    }
//                });
//                break;
//            }
//            case "User": {
//                //TODO: implement onClick function on textView
//                holder.imageView.setImageResource(R.drawable.user_icon);
//                holder.textView.setText(userVisible.get(position).getUserName());
//                if (userIndexMap.get(position) != null) {
//                    holder.checkBox.setChecked(userFlag.get(userIndexMap.get(position), false));
//                }
//                holder.checkBox.setTag(position);
//                //holder.itemView.setActivated(userFlag.get(position, false));
//
//                if(userIndexMap.get(position) == 0 && userFlag.get(0, false) && holder.checkBox.isChecked()) {
//                    lastCheckedUser = holder.checkBox;
//                    lastCheckedPosUser = 0;
//                }
//
//                holder.checkBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CheckBox cb = (CheckBox) v;
//                        int clickedPos = ((Integer) cb.getTag()).intValue();
//
//                        if (userFlag.get(userIndexMap.get(clickedPos), false)) {
//                            if (lastCheckedUser != null) {
//                                userFlag.delete(userIndexMap.get(clickedPos));
//                            }
//                            lastCheckedUser = cb;
//                            lastCheckedPosUser = userIndexMap.get(clickedPos);
//                        } else {
//                            lastCheckedUser = null;
//                            userFlag.put(userIndexMap.get(clickedPos), true);
//                        }
//                    }
//                });
//                break;
//            }
//            case "ContactPair": {
//                //TODO: implement onClick function on textView
//                holder.imageView.setImageResource(R.drawable.contacts_icon);
//                holder.textView.setText(contactVisible.get(position).getName());
//                if (contactIndexMap.get(position) != null) {
//                    holder.checkBox.setChecked(contactFlag.get(contactIndexMap.get(position)));
//                }
//                holder.checkBox.setTag(new Integer(position));
//                //holder.itemView.setActivated(contactFlag.get(position));
//
//                if(contactIndexMap.get(position) != null && contactIndexMap.get(position) == 0 && contactFlag.get(0, false) && holder.checkBox.isChecked()) {
//                    lastCheckedContacts = holder.checkBox;
//                    lastCheckedPosContacts = 0;
//                }
//
//                holder.checkBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        CheckBox cb = (CheckBox) v;
//                        int clickedPos = ((Integer) cb.getTag()).intValue();
//
//                        if (contactIndexMap.get(clickedPos) != null && contactFlag.get
//                                (contactIndexMap
//                                .get(clickedPos), false)) {
//                            if (lastCheckedContacts != null) {
//                                contactFlag.delete(contactIndexMap.get(clickedPos));
//                            }
//                            lastCheckedContacts = cb;
//                            lastCheckedPosContacts = contactIndexMap.get(clickedPos);
//                        } else if (contactIndexMap.get(clickedPos) != null) {
//                            lastCheckedContacts = null;
//                            contactFlag.put(contactIndexMap.get(clickedPos), true);
//                        }
//                    }
//                });
//                break;
//            }
//        }
    }

    @Override
    public int getItemCount() {
//        switch (typeString) {
//            case "Group": {
//                if (groupVisible == null) {
//                    return 0;
//                }
//                return groupVisible.size();
//            }
//            case "User": {
//                if (userVisible == null) {
//                    return 0;
//                }
//                return userVisible.size();
//            }
//            case "ContactPair": {
//                if (contactVisible == null) {
//                    return 0;
//                }
//                return contactVisible.size();
//            }
//        }
//        return 0;
        if (debtDataVisible == null) {
            return 0;
        }
        return debtDataVisible.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView;
        protected TextView textView;
        //protected CheckBox checkBox;
        protected TextView costView;

        protected ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.contact_picture);
            textView = (TextView) view.findViewById(R.id.contact_name);
            costView = (TextView) view.findViewById(R.id.contact_price);

           // checkBox = (CheckBox) view.findViewById(R.id.contact_checkbox);
        }
    }

//    public SparseBooleanArray getGroupFlag() {
//        return groupFlag;
//    }
//
//    public SparseBooleanArray getUserFlag() {
//        return userFlag;
//    }
//
//    public SparseBooleanArray getContactFlag() {
//        return contactFlag;
//    }
//
//    public void setGroupFlag(SparseBooleanArray flag) {
//        groupFlag = flag;
//    }
//
//    public void setUserFlag(SparseBooleanArray flag) {
//        userFlag = flag;
//    }
//
//    public void setContactFlag(SparseBooleanArray flag) {
//        contactFlag = flag;
//    }

    public Debt removeDebt(int position) {
        final Debt debt = debtDataVisible.remove(position);
        // groupIndexMap.remove(position);
        // groupIndexMap.put(position, -1);
        notifyItemRemoved(position);
       // Log.d("DEBUGGING", "removeDebt is called");
        return debt;
    }

    public void addDebt(int position, Debt debt) {
        debtDataVisible.add(position, debt);
        //int originalIndex = Arrays.asList(groupData).indexOf(group);
        //groupIndexMap.put(position, originalIndex);
        notifyItemInserted(position);
        //Log.d("DEBUGGING", "addDebt is called");
    }

    public void moveDebt(int fromPosition, int toPosition) {
        final Debt debt = debtDataVisible.remove(fromPosition);
        debtDataVisible.add(toPosition, debt);
        //int originalIndex = groupIndexMap.remove(fromPosition);
        //groupIndexMap.put(toPosition, originalIndex);
        notifyItemMoved(fromPosition, toPosition);
        //Log.d("DEBUGGING", "moveDebt is called");
    }

    public void animateDebt(ArrayList<Debt> debtList) {
       //Log.d("DEBUGGING", "animateGroup is called");
        applyAndAnimateRemovalsDebt(debtList);
        applyAndAnimateAdditionsDebt(debtList);
        applyAndAnimateMovedItemsDebt(debtList);
    }

//    public void animateUser(ArrayList<User> userList) {
//        applyAndAnimateRemovalsUser(userList);
//        applyAndAnimateAdditionsUser(userList);
//        applyAndAnimateMovedItemsUser(userList);
//    }
//
//    public void animateContact(ArrayList<ContactPair> contactList) {
//        applyAndAnimateRemovalsContact(contactList);
//        applyAndAnimateAdditionsContact(contactList);
//        applyAndAnimateMovedItemsContact(contactList);
//    }


    public void applyAndAnimateRemovalsDebt(ArrayList<Debt> filteredDebtList) {
        for (int count = debtDataVisible.size() - 1; count >= 0; --count) {
            final Debt debt = debtDataVisible.get(count);
            if (!filteredDebtList.contains(debt)) {
                removeDebt(count);
            }
        }
    }

    public void applyAndAnimateAdditionsDebt(ArrayList<Debt> filteredDebtList) {
        for (int count = 0; count < filteredDebtList.size(); ++count) {
            final Debt debt = filteredDebtList.get(count);
            //Log.d("DEBUGGING", "applyAndAnimatedAdditionsDebt is called");
            if (!debtDataVisible.contains(debt)) {
                //Log.d("DEBUGGING", "addDebt is going to be called");
                addDebt(count, debt);
            }
        }
    }

    public void applyAndAnimateMovedItemsDebt(ArrayList<Debt> filteredDebtList) {
        for (int toPosition = filteredDebtList.size() - 1; toPosition >= 0; --toPosition) {
            final Debt debt = filteredDebtList.get(toPosition);
            final int fromPosition = debtDataVisible.indexOf(debt);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveDebt(fromPosition, toPosition);
            }
        }
    }


//    public void applyAndAnimateRemovalsUser(ArrayList<User> filteredUserList) {
//        for (int count = userVisible.size() - 1; count >= 0; --count) {
//            final User user = userVisible.get(count);
//            if (!filteredUserList.contains(user)) {
//                removeUser(count);
//            }
//        }
//    }
//
//    public void applyAndAnimateAdditionsUser(ArrayList<User> filteredUserList) {
//        for (int count = 0; count < filteredUserList.size(); ++count) {
//            final User user = filteredUserList.get(count);
//            if (!userVisible.contains(user)) {
//                addUser(count, user);
//            }
//        }
//    }
//
//    public void applyAndAnimateMovedItemsUser(ArrayList<User> filteredUserList) {
//        for (int toPosition = filteredUserList.size() - 1; toPosition >= 0; --toPosition) {
//            final User user = filteredUserList.get(toPosition);
//            final int fromPosition = userVisible.indexOf(user);
//            if (fromPosition >= 0 && fromPosition != toPosition) {
//                moveUser(fromPosition, toPosition);
//            }
//        }
//    }
//
//
//
//    public void applyAndAnimateRemovalsContact(ArrayList<ContactPair> filteredContactList) {
//        for (int count = contactVisible.size() - 1; count >= 0; --count) {
//            final ContactPair contact = contactVisible.get(count);
//            if (!filteredContactList.contains(contact)) {
//                removeContact(count);
//            }
//        }
//    }
//
//    public void applyAndAnimateAdditionsContact(ArrayList<ContactPair> filteredContactList) {
//        for (int count = 0; count < filteredContactList.size(); ++count) {
//            final ContactPair contact = filteredContactList.get(count);
//            if (!contactVisible.contains(contact)) {
//                addContact(count, contact);
//            }
//        }
//    }
//
//    public void applyAndAnimateMovedItemsContact(ArrayList<ContactPair> filteredContactList) {
//        for (int toPosition = filteredContactList.size() - 1; toPosition >= 0; --toPosition) {
//            final ContactPair contact = filteredContactList.get(toPosition);
//            final int fromPosition = contactVisible.indexOf(contact);
//            if (fromPosition >= 0 && fromPosition != toPosition) {
//                moveContact(fromPosition, toPosition);
//            }
//        }
//    }

    public void setFilterDebt(String query) {
        final ArrayList<Debt> filteredDebtList = filterDebt(debtData, query);
        //Log.d("DEBUGGING", "setFilterDebt is called");
        animateDebt(filteredDebtList);
    }

//    public void setFilterDebt(String query) {
//        final ArrayList<Debt> filteredDebtList = filterDebt(debtData, query);
//        animateDebt(filteredDebtList);
//    }
//
//    public void setFilterContact(String query) {
//        final ArrayList<ContactPair> filteredContactList = filterContact(contactData, query);
//        animateContact(filteredContactList);
//    }

    public ArrayList<Debt> filterDebt(ArrayList<Debt> debtData, String query) {
        final ArrayList<Debt> filteredDebtList = new ArrayList<Debt>();
        if(query == null) {
            //groupIndexMap.clear();
            int i = 0;
            for (Debt debt : debtData) {
                filteredDebtList.add(debt);
                debtIndexMap.put(i, i);
                i++;
            }
        } else {
            //groupIndexMap.clear();
            query = query.toLowerCase();
            int originalIndex = 0;
            int visibleIndex = 0;
            for (Debt debt : debtData) {
                final String debtorName = debt.getDebtor().getUserName().toLowerCase();
                if (debtorName.contains(query)) {
                    filteredDebtList.add(debt);
                    debtIndexMap.put(visibleIndex, originalIndex);
                    visibleIndex++;
                }
                originalIndex++;
            }
        }
        return filteredDebtList;
    }

//    public ArrayList<User> filterUser(User[] userData, String query) {
//        final ArrayList<User> filteredUserList = new ArrayList<User>();
//        if(query == null) {
//            for (User user : userData) {
//                filteredUserList.add(user);
//            }
//        } else {
//            query = query.toLowerCase();
//            for (User user : userData) {
//                final String userName = user.getUserName().toLowerCase();
//                if (userName.contains(query)) {
//                    filteredUserList.add(user);
//                }
//            }
//        }
//        return filteredUserList;
//    }
//
//    public ArrayList<ContactPair> filterContact(ContactPair[] contactData, String query) {
//        final ArrayList<ContactPair> filteredContactList = new ArrayList<ContactPair>();
//        if(query == null) {
//            for (ContactPair contact : contactData) {
//                filteredContactList.add(contact);
//            }
//        } else {
//            query = query.toLowerCase();
//            for (ContactPair contact : contactData) {
//                final String contactName = contact.getName().toLowerCase();
//                if (contactName.contains(query)) {
//                    filteredContactList.add(contact);
//                }
//            }
//        }
//        return filteredContactList;
//    }

}
