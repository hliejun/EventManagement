//package sg.edu.nus.comp.orbital.eventmanagement;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
///**
// * Created by Larry on 14/6/15.
// */
//
//
//public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
//
//    /* INSTEAD OF USING THE TEXT EXAMPLE, USE BILLS ITEMS*/
//
//    // Put bill and purchase dataset here
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public PurchaseView mPurchaseView;
//        public ViewHolder(PurchaseView v) {
//
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public BillAdapter(Dataset) {
//        mDataset = myDataset;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//        PurchaseView v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.mTextView.setText(mDataset[position]);
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }
//
//}
