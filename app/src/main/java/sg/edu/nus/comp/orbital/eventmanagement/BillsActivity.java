package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GestureDetectorCompat;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuInflater;
import static android.view.GestureDetector.SimpleOnGestureListener;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*** Browsing activity for existing bills, and option to add new misc. bill ***/
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BillsActivity extends ActionBarActivity implements RecyclerView
        .OnItemTouchListener,
        View.OnClickListener,
        ActionMode.Callback {

    protected ArrayList<Bill> myBills = new ArrayList<Bill>();
    final static private String BILLS_FILENAME = "bills.xml";

    protected Context mContext;

    protected GestureDetectorCompat gestureDetector = null;
    protected ActionMode actionMode = null;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected BillsAdapter mAdapter;

    protected TextView emptyView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        // Initialize layout
        setContentView(R.layout.activity_bills);
        ContextManager.context = mContext;

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/GoodDog.ttf");
        Button createBillButton = (Button) findViewById(R.id.createBillButton);
        createBillButton.setTypeface(type);

//        //TODO: Load events and bills from database
//
//        // Dummy Bills Array
//        User user01 = new User("Cecilia", "95263467");
//        User user02 = new User("Leonardo", "82560134");
//        User user03 = new User("Bieber", "83464119");
//        User user04 = new User("Aladdin", "91613543");
//        User user05 = new User("Shammu", "93151623");
//        User user06 = new User("Dennis", "81511353");
//        User user07 = new User("Seraphine", "91512415");
//        User user08 = new User("Fandi Ahmad", "81251012");
//        User user09 = new User("Unbelievable", "91632235");
//        User user10 = new User("Terence", "92552432");
//        User user11 = new User("Courtney", "92363262");
//        User user12 = new User("Louis", "81623410");
//        User user13 = new User("Betty", "92346239");
//        User user14 = new User("Binarie", "90236899");
//        User user15 = new User("Shaunti", "98923623");
//        User user16 = new User("Denise", "81629145");
//        User user17 = new User("Thomas", "80908235");
//        User user18 = new User("Vishnu", "97078987");
//        User user19 = new User("Ridhwan", "97801249");
//        User mPayer = new User("AH LONG", "1800 555 0000");
//
//        Group group = new Group("Good Friends");
//        group.addUser(user01);
//        group.addUser(user02);
//        group.addUser(user03);
//        group.addUser(user04);
//        group.addUser(user05);
//        group.addUser(user06);
//        group.addUser(user07);
//        group.addUser(user08);
//        group.addUser(user09);
//        group.addUser(user10);
//        group.addUser(user11);
//        group.addUser(user12);
//        group.addUser(user13);
//        group.addUser(user14);
//        group.addUser(user15);
//        group.addUser(user16);
//        group.addUser(user17);
//        group.addUser(user18);
//        group.addUser(user19);
//
//        Bill bill01 = new Bill(group, mPayer, "Catching Bugs!");
//        Bill bill02 = new Bill(group, mPayer, "Fun at Water Park");
//        Bill bill03 = new Bill(group, mPayer, "Mountain Climbing");
//        Bill bill04 = new Bill(group, mPayer, "Food Paradise");
//        Bill bill05 = new Bill(group, mPayer, "Sheryl's Birthday Party!");
//        Bill bill06 = new Bill(group, mPayer, "Finals Mugging in SoC");
//
//        myBills.add(bill01);
//        myBills.add(bill02);
//        myBills.add(bill03);
//        myBills.add(bill04);
//        myBills.add(bill05);
//        myBills.add(bill06);

        readBillsFromFile();

        // Set Layout Manager and RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.scrollToPosition(0);
        mRecyclerView = (RecyclerView) findViewById(R.id.bills_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setScrollContainer(true);


        emptyView = (TextView) findViewById(R.id.empty_view);

        // Instantiate the adapter and pass in its data source
        mAdapter = new BillsAdapter(myBills);

        // Plug the adapter into the RecyclerView:
        mRecyclerView.setAdapter(mAdapter);

        if (myBills == null || myBills.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setTypeface(type);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        // Gesture detection
        mRecyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());
    }

    // Create a new miscellaneous bill
    public void createBill(View view) {
        Intent intent = new Intent(this, CreateBillActivity.class);
        intent.putExtra("FROM_ACTIVITY", "BillsActivity");
        startActivityForResult(intent, 0);
        finish();
    }

    // Terminate and return to home without results
    public void backHome(View view) {
        finish();
    }

    // Gesture control
    @Override
    public void onClick(View view) {
        if (view != null) {
            if (view.getId() == R.id.createBillButton) {
                createBill(view);
            } else if (view.getId() == R.id.backToMain) {
                backHome(view);
            } else if (view.getId() == R.id.bill) {
                int index = mRecyclerView.getChildAdapterPosition(view);
                if (actionMode != null) {
                    myToggleSelection(index);
                }
            } else {
                // do nothing...
            }
        }
    }

    // -------- START: Action bar control --------//
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

    // -------- ^^^ END: Action bar control ^^^ --------//


    // -------- START: Recycler selection control --------//
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

    //@Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    // -------- ^^^ END: Recycler selection control ^^^ --------//


    // Handles return of new miscellaneous bill created
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == ReviewBillActivity.RESULT_OK) {
                    try {
                        Bundle bundle = data.getExtras();
                        Bill bill = bundle.getParcelable("NEW_BILL");
                        writeBillsToFile(bill);
                        readBillsFromFile();
                        if (myBills == null || myBills.isEmpty()) {
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    public boolean writeBillsToFile(Bill bill) {
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
            if (myBills == null) {
                myBills = new ArrayList<Bill>();
            }
            myBills.add(bill);
            fos = getApplicationContext().openFileOutput(BILLS_FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(myBills);
            oos.close();
            return true;
        }catch(Exception e){
            Log.d("BILLS", "Can't save bills" + e.getMessage());
            return false;
        }
        finally{
            if(oos!=null)
                try{
                    oos.close();
                }catch(Exception e){
                    Log.d("BILLS", "Error while closing stream " + e.getMessage());
                }
        }
    }

    private boolean readBillsFromFile(){
        FileInputStream fin;
        ObjectInputStream ois=null;
        try{
            fin = getApplicationContext().openFileInput(BILLS_FILENAME);
            ois = new ObjectInputStream(fin);
            myBills =(ArrayList<Bill>)ois.readObject();
            ois.close();
            Log.d("BILLS", "Bills read successfully");
            return true;
        }catch(Exception e){
            Log.d("BILLS", "Cant read saved bills"+e.getMessage());
            return false;
        }
        finally{
            if(ois!=null)
                try{
                    ois.close();
                }catch(Exception e){
                    Log.d("BILLS", "Error in closing stream while reading bills" + e
                            .getMessage());
                }
        }
    }
}
