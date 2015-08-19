package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.SparseBooleanArray;
import android.view.View.OnFocusChangeListener;

import java.util.List;
import java.util.ArrayList;

/*
Custom Adapter for handling bill views (billView) in BillsActivity.
 */
public class BillsAdapter extends RecyclerView.Adapter {
    // Input from parent view (to be handled with care)
    private final ArrayList<Bill> myBills;
    // Payment mask selection for debt creation
    private SparseBooleanArray selectionMask;

    // Adapter Constructor: Initialize all inputs
    public BillsAdapter(ArrayList<Bill> bills) {
        myBills = bills;
        selectionMask = new SparseBooleanArray();
    }

    // Create a view for specific entry in myBills
    public View getView(int position, View convertView, ViewGroup parent) {
        BillView view;
        view = (BillView) convertView;
        view.setBillEventTitle(myBills.get(position).getBillTitle());
        return view;
    }

    // Create view holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill, parent, false);
        final BillViewHolder holder = new BillViewHolder(view);
        OnFocusChangeListener ofcListener = new CustomFocusChangeListener();
        return holder;
    }

    // Initialize view holder components and associate them with their equivalent in view
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BillViewHolder viewHolder = (BillViewHolder) holder;
        int[] colors = ((BillViewHolder) holder).billIcon.getResources().getIntArray(R.array.palecolors);
        int color = colors[position%colors.length];
        ((BillViewHolder) holder).billIcon.setColorFilter(color);
        viewHolder.billEventTitle.setText(myBills.get(position).getBillTitle());
        viewHolder.itemView.setActivated(selectionMask.get(position, false));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myBills.size();
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

    public ArrayList<Bill> getBills() {
        return myBills;
    }

}
