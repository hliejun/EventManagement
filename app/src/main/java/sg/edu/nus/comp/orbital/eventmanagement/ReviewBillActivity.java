package sg.edu.nus.comp.orbital.eventmanagement;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GestureDetectorCompat;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuInflater;
import android.view.Window;
import android.content.Context;
import android.transition.ChangeTransform;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import static android.view.GestureDetector.SimpleOnGestureListener;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ReviewBillActivity extends ActionBarActivity implements RecyclerView
        .OnItemTouchListener,
        View.OnClickListener, Parcelable,
        ActionMode.Callback {

    protected Bundle reviewBillBundle = null;
    protected static ArrayList<Bill> billList = null;
    protected Bill billInReview = null;
    protected String eventTitle = null;
    protected HashMap<User, Double> userCostTable = null;
    protected Parcel parcel = null;
    protected Context mContext;

    GestureDetectorCompat gestureDetector = null;
    ActionMode actionMode = null;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BillSummaryAdapter mAdapter;

    // Create bill from parcel
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ArrayList<Bill> createFromParcel(Parcel in) {
            in.readList(billList, null);
            return billList;
        }
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        // Animation and transition handling
        //getWindow().setAllowReturnTransitionOverlap(true);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //getWindow().setSharedElementExitTransition(new ChangeTransform());

        // Initialize layout
        setContentView(R.layout.activity_review_bill);

        // Read bill from previous activity (disable when testing this activity alone)
        reviewBillBundle = this.getIntent().getExtras();
        if (reviewBillBundle != null) {
            parcel = reviewBillBundle.getParcelable("BILL_IN_REVIEW");
            billList = (ArrayList<Bill>) CREATOR.createFromParcel(parcel);
            if (billList != null) {
                billInReview = billList.get(0);
            }
        }

        //TODO: ADD FAKE BILL HERE!

        // ...

        // Throw exception if cannot read bill
        if (billInReview == null) {
            throw new RuntimeException("Error acquiring bill for this review session!");
        }

        // Get variables from Bill object received from previous activity
        eventTitle = billInReview.getEventTitle();
        userCostTable = billInReview.getUserCostTable();

        // Set our view from the "main" layout resource:
        setContentView(R.layout.activity_review_bill);

        // Set Layout Manager and RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.scrollToPosition(0);
        mRecyclerView = (RecyclerView) findViewById(R.id.UserCostList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Instantiate the adapter and pass in its data source:
        mAdapter = new BillSummaryAdapter(userCostTable);

        // Plug the adapter into the RecyclerView:
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());
    }

    public void createDebts(Bill targetBill, BillSummaryAdapter adapter) {
        //TODO: ON BUTTON CREATE DEBT, GET THE USER-COST PAIRS AND MAKE DEBTS IN TARGET BILL

    }

    public void remindPayment(Bill targetBill, BillSummaryAdapter adapter) {
        //TODO: ON BUTTON REMIND, SEND REMINDER TO USERS IN LIST OF VIEWS
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createDebtsButton) {
            createDebts(billInReview, mAdapter);
        } else if (view.getId() == R.id.remindPayButton) {
            remindPayment(billInReview, mAdapter);
        } else if (view.getId() == R.id.userCost) {
            int index = mRecyclerView.getChildAdapterPosition(view);
            if (actionMode != null) {
                myToggleSelection(index);
                return;
            }
        }
    }

    //TODO: CREATE PAGES / ACTIVITY FOR VIEW AND EDIT EXISTING DEBTS / ASSETS

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_review_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_review_bill, menu);
        return true;
    }

    // Called each time the action mode is shown. Always called after onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false; // Return false if nothing is done
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete_payment:
                List<Integer> selectedItemPositions = mAdapter.getSelectedIndex();
                for (int count = selectedItemPositions.size() - 1; count >= 0; --count){
                    mAdapter.removeData(selectedItemPositions.get(count));
                }
                actionMode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.actionMode = null;
        mAdapter.clearSelections();
    }

    private class RecyclerViewDemoOnGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            // Start the callback action using the ActionMode.Callback defined above
            actionMode = startActionMode(ReviewBillActivity.this);
            int index = mRecyclerView.getChildAdapterPosition(view);
            myToggleSelection(index);
            super.onLongPress(e);
        }
    }

    private void myToggleSelection(int index) {
        mAdapter.toggleSelection(index);
        String title = getString(R.string.selected_count, mAdapter.getSelectionCount());
        actionMode.setTitle(title);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }
}
