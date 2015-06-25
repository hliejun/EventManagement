package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.SparseBooleanArray;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;

/*
Custom Adapter for handling payment views (userCostView) in ReviewBillActivity.
 */
public class BillSummaryAdapter extends RecyclerView.Adapter {
    // Input from parent view (to be handled with care)
    private final UserCostPair[] userCostTable;
    // Payment mask selection for debt creation
    private SparseBooleanArray selectionMask;

    // Adapter Constructor: Initialize all inputs
    public BillSummaryAdapter(HashMap<User, Double> userCostTable) {
        int count = 0;
        UserCostPair[] tempArray = new UserCostPair[userCostTable.size()];
        // Set-up array of entries by converting userCostTable
        for (Entry<User, Double> entry : userCostTable.entrySet()) {
            tempArray[count] = new UserCostPair(entry.getKey(), entry.getValue());
            ++count;
        }
        this.userCostTable = tempArray;
    }

    // Create a view for specific entry in userCostTable
    public View getView(int position, View convertView, ViewGroup parent) {
        UserCostView view;
        UserCostPair userCost;
        view = (UserCostView) convertView;
        userCost = userCostTable[position];
        view.showCost(userCost);
        return view;
    }

    // Create view holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_costs, parent, false);
        return new UserCostViewHolder(view);
    }

    // Initialize view holder components and associate them with their equivalent in view
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserCostViewHolder viewHolder = (UserCostViewHolder) holder;
        viewHolder.profilePic.setImageResource(userCostTable[position].getUser().getPhotoID());
        viewHolder.userName.setText(userCostTable[position].getUser().getUserName());
        viewHolder.cost.setText(userCostTable[position].getCost().toString());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return userCostTable.length;
    }

    // Toggles selection
    public void toggleSelection(int pos) {
        // If key is found, delete it
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
}
