package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.util.SparseBooleanArray;
import android.widget.EditText;
import android.content.Context;
import android.view.View.OnFocusChangeListener;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

/*
Custom Adapter for handling payment views (userCostView) in ReviewBillActivity.
 */
public class BillSummaryAdapter extends RecyclerView.Adapter {
    // Input from parent view (to be handled with care)
    private final UserCostPair[] userCostTable;
    // Payment mask selection for debt creation
    private SparseBooleanArray selectionMask;
    private DecimalFormat df = new DecimalFormat("#.00");

    private String[] paidAmount;
    private EditText paid;

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
        paidAmount = new String[userCostTable.size()];
        selectionMask = new SparseBooleanArray();
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
        final UserCostViewHolder holder = new UserCostViewHolder(view);
        EditText paidField = holder.paid;
        OnFocusChangeListener ofcListener = new CustomFocusChangeListener();
        final TextWatcher textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s != null) {
                    paidAmount[holder.getAdapterPosition()] = holder.paid.getText().toString();
                }
            }
        };
        paidField.setOnFocusChangeListener(ofcListener);
        paidField.addTextChangedListener(textWatcher);
        return holder;
    }

    // Initialize view holder components and associate them with their equivalent in view
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final UserCostViewHolder viewHolder = (UserCostViewHolder) holder;
        viewHolder.profilePic.setImageResource(R.drawable.small_profile);
        viewHolder.userName.setText("Name: " + userCostTable[position].getUser().getUserName());
        viewHolder.cost.setText("Payment: $" + df.format(userCostTable[position].getCost()));
        viewHolder.paid.setText(paidAmount[position]);
        viewHolder.itemView.setActivated(selectionMask.get(position, false));
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

    public UserCostPair[] getUserCostTable() {
        return userCostTable;
    }

    public String[] getPaidAmount() {
        return paidAmount;
    }
}
