package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GestureDetectorCompat;

import android.annotation.TargetApi;
import android.text.Editable;
import android.widget.EditText;
import android.app.AlertDialog;
import android.os.Parcel;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuInflater;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.text.TextWatcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import static android.view.GestureDetector.SimpleOnGestureListener;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BillsActivity extends ActionBarActivity implements RecyclerView
        .OnItemTouchListener,
        View.OnClickListener, //Parcelable,
        ActionMode.Callback {

    protected Bundle billBundle = null;
    protected ArrayList<Bill> myBills = new ArrayList<Bill>();
    protected Parcel parcel = null;
    protected Context mContext;

    GestureDetectorCompat gestureDetector = null;
    ActionMode actionMode = null;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BillsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        // Initialize layout
        setContentView(R.layout.activity_bills);

        ContextManager.context = mContext;

        // Dummy Bills Array

        User user01 = new User("Cecilia", "95263467");
        User user02 = new User("Leonardo", "82560134");
        User user03 = new User("Bieber", "83464119");
        User user04 = new User("Aladdin", "91613543");
        User user05 = new User("Shammu", "93151623");
        User user06 = new User("Dennis", "81511353");
        User user07 = new User("Seraphine", "91512415");
        User user08 = new User("Fandi Ahmad", "81251012");
        User user09 = new User("Unbelievable", "91632235");
        User user10 = new User("Terence", "92552432");
        User user11 = new User("Courtney", "92363262");
        User user12 = new User("Louis", "81623410");
        User user13 = new User("Betty", "92346239");
        User user14 = new User("Binarie", "90236899");
        User user15 = new User("Shaunti", "98923623");
        User user16 = new User("Denise", "81629145");
        User user17 = new User("Thomas", "80908235");
        User user18 = new User("Vishnu", "97078987");
        User user19 = new User("Ridhwan", "97801249");
        User mPayer = new User("AH LONG", "1800 555 0000");

        Group group = new Group();
        group.addUser(user01);
        group.addUser(user02);
        group.addUser(user03);
        group.addUser(user04);
        group.addUser(user05);
        group.addUser(user06);
        group.addUser(user07);
        group.addUser(user08);
        group.addUser(user09);
        group.addUser(user10);
        group.addUser(user11);
        group.addUser(user12);
        group.addUser(user13);
        group.addUser(user14);
        group.addUser(user15);
        group.addUser(user16);
        group.addUser(user17);
        group.addUser(user18);
        group.addUser(user19);

        Bill bill01 = new Bill(group, mPayer, "Catching Bugs!");
        Bill bill02 = new Bill(group, mPayer, "Fun at Water Park");
        Bill bill03 = new Bill(group, mPayer, "Mountain Climbing");
        Bill bill04 = new Bill(group, mPayer, "Food Paradise");
        Bill bill05 = new Bill(group, mPayer, "Sheryl's Birthday Party!");
        Bill bill06 = new Bill(group, mPayer, "Finals Mugging in SoC");

        myBills.add(bill01);
        myBills.add(bill02);
        myBills.add(bill03);
        myBills.add(bill04);
        myBills.add(bill05);
        myBills.add(bill06);

        // Set Layout Manager and RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.scrollToPosition(0);
        mRecyclerView = (RecyclerView) findViewById(R.id.bills_recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Instantiate the adapter and pass in its data source:
        mAdapter = new BillsAdapter(myBills);

        // Plug the adapter into the RecyclerView:
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());
    }


    public void createBill(View view) {
        Intent intent = new Intent(this, CreateBillActivity.class);
        startActivity(intent);
    }

    public void backHome(View view) {
        finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createBillButton) {
            createBill(view);
        } else if (view.getId() == R.id.backToMain) {
            backHome(view);
        } else if (view.getId() == R.id.bill) {
            int index = mRecyclerView.getChildAdapterPosition(view);
            if (actionMode != null) {
                myToggleSelection(index);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_bills, menu);
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

    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_bills, menu);
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
            actionMode = startActionMode(BillsActivity.this);
            int index = mRecyclerView.getChildAdapterPosition(view);
            myToggleSelection(index);
            super.onLongPress(e);
        }
    }

    private void myToggleSelection(int index) {
        mAdapter.toggleSelection(index);
        String title = "SELECT BILLS";
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
