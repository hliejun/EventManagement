package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

/*
Custom Adapter for handling bill views (billView) in BillsActivity.
 */
public class CreateBillAdapter extends RecyclerView.Adapter {
    // Input from parent view (to be handled with care)
    private ListOfPurchases purchaseList;
    private ArrayList<Purchase> myPurchases;
    // Payment mask selection for debt creation
    private SparseBooleanArray selectionMask;

    private DecimalFormat df = new DecimalFormat("#.00");

    // Adapter Constructor: Initialize all inputs
    public CreateBillAdapter(ListOfPurchases purchases) {
        purchaseList = purchases;
        myPurchases = new ArrayList<Purchase>();
        for (Purchase purchase : purchaseList.getPurchases()) {
            myPurchases.add(purchase);
        }
        selectionMask = new SparseBooleanArray();
    }

    // Create view holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase, parent,
                false);
        final CreateBillViewHolder holder = new CreateBillViewHolder(view);
        OnFocusChangeListener ofcListener = new CustomFocusChangeListener();
        return holder;
    }

    // Initialize view holder components and associate them with their equivalent in view
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CreateBillViewHolder viewHolder = (CreateBillViewHolder) holder;
        viewHolder.profilePic.setImageResource(R.drawable.small_profile);
        HashSet<User> users = myPurchases.get(position).getUser();
        if (users.size() > 1) {
            viewHolder.userName.setText("GROUP PURCHASE");
        } else {
            for (User user : users) {
                viewHolder.userName.setText("Purchased By: " + user.getUserName());
            }
        }
        viewHolder.itemName.setText("Item: " + myPurchases.get(position).getItem
                ().getItemName());
        viewHolder.itemCost.setText("Unit Cost: $" + df.format(myPurchases.get(position).getItem
                ().getItemCost()));
        viewHolder.itemQuantity.setText("Quantity: " + myPurchases.get(position).getQuantity());
        viewHolder.itemView.setActivated(selectionMask.get(position, false));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myPurchases.size();
    }

    // Toggles selection
    public void toggleSelection(int pos) {
        // If key is found, set it off
        if (selectionMask.get(pos, false)) {
            selectionMask.delete(pos);
        }
        // If key is not found, add it
        else {
            selectionMask.put(pos, true);
        }

        // Toggle activated state of view through new view holder binding
        notifyItemChanged(pos);
    }

    // Reset and clear selection mask
    public void clearSelections() {
        selectionMask.clear();
        // Toggle activated state of view through new view holder binding
        notifyDataSetChanged();
    }

    // Get the size of selection
    public int getSelectionCount() {
        return selectionMask.size();
    }

    // Get a mask / list of indices of the selected views
    // This can be used for deleting views, or to acquire list of payment to create debts or
    // remind users
    public List<Integer> getSelectedIndex() {
        List<Integer> selectedMask =
                new ArrayList<Integer>(selectionMask.size());
        for (int count = 0; count < selectionMask.size(); ++count) {
            selectedMask.add(selectionMask.keyAt(count));
        }
        return selectedMask;
    }

    public void removeData(int pos) {
        // Remove data here
    }

    public ArrayList<Purchase> getPurchases() {
        return myPurchases;
    }

}