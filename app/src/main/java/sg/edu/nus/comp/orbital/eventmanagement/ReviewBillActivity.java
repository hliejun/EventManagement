package sg.edu.nus.comp.orbital.eventmanagement;

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
public class ReviewBillActivity extends ActionBarActivity implements RecyclerView
        .OnItemTouchListener,
        View.OnClickListener, //Parcelable,
        ActionMode.Callback {

    protected Bundle reviewBillBundle = null;
    protected static ArrayList<Bill> billList = null;
    protected Bill billInReview = null;
    protected String eventTitle = null;
    protected HashMap<User, Double> userCostTable = null;
    protected Parcel parcel = null;
    protected Context mContext;

    private EditText paidAmount;


    GestureDetectorCompat gestureDetector = null;
    ActionMode actionMode = null;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BillSummaryAdapter mAdapter;

    // Create bill from parcel
//    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
//        public ArrayList<Bill> createFromParcel(Parcel in) {
//            in.readList(billList, null);
//            return billList;
//        }
//        public Bill[] newArray(int size) {
//            return new Bill[size];
//        }
//    };

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

        ContextManager.context = mContext;

        // Read bill from previous activity (disable when testing this activity alone)
//        reviewBillBundle = this.getIntent().getExtras();
//        if (reviewBillBundle != null) {
//            parcel = reviewBillBundle.getParcelable("BILL_IN_REVIEW");
//            billList = (ArrayList<Bill>) CREATOR.createFromParcel(parcel);
//            if (billList != null) {
//                billInReview = billList.get(0);
//            }
//        }

//        paidAmount = (EditText) findViewById(R.id.paid);
//        paidAmount.addTextChangedListener(textWatcher);

        //TODO: ADD FAKE BILL HERE!
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

        billInReview = new Bill(group, mPayer, "Catching Bugs!");

        billInReview.addItem("Sheltox", "Tools", 6.45);
        billInReview.addItem("Noodle", "Food", 2.75);
        billInReview.addItem("Beer", "Beverage", 4.15);
        billInReview.addItem("Lollipop", "Snacks", 1.20);
        billInReview.addItem("Pokeball", "Tools", 100);
        billInReview.addItem("Green Tea", "Beverage", 2.65);
        billInReview.addItem("Yang Chow Fried Rice", "Food", 4.45);
        billInReview.addItem("Xian Dan Ji Ding Rice", "Food", 3.80);

        ArrayList<String> sheltoxGang = new ArrayList<String>();
        sheltoxGang.add("Leonardo");
        sheltoxGang.add("Bieber");
        sheltoxGang.add("Aladdin");

        ArrayList<String> pgprOrders = new ArrayList<String>();
        pgprOrders.add("Leonardo");
        pgprOrders.add("Shammu");
        pgprOrders.add("Dennis");
        pgprOrders.add("Seraphine");
        pgprOrders.add("Terence");
        pgprOrders.add("Courtney");
        pgprOrders.add("Louis");
        pgprOrders.add("Xian Dan Ji Ding Rice");
        pgprOrders.add("200");

        ArrayList<String> lollipop = new ArrayList<String>();
        lollipop.add("Betty");
        lollipop.add("Binarie");
        lollipop.add("Shaunti");
        lollipop.add("Denise");
        lollipop.add("Thomas");
        lollipop.add("Vishnu");
        lollipop.add("Ridhwan");
        lollipop.add("Lollipop");
        lollipop.add("500");

        billInReview.addPurchase("Cecilia", "Lollipop", 5);
        billInReview.addPurchase(sheltoxGang, "Sheltox", 23);
        billInReview.addPurchase(pgprOrders);
        billInReview.addPurchase(lollipop);

        billInReview.setList();

        billInReview.costCalc();

        userCostTable = billInReview.getUserCostTable();

        // Throw exception if cannot read bill
        if (billInReview == null) {
            throw new RuntimeException("Error acquiring bill for this review session!");
        }

        // Get variables from Bill object received from previous activity
        eventTitle = billInReview.getEventTitle();
        userCostTable = billInReview.getUserCostTable();

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

//    private final TextWatcher textWatcher = new TextWatcher() {
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        public void afterTextChanged(Editable s) {
//
//        }
//    };

    public void createDebts(View view) {
        List<Integer> selectionMask = mAdapter.getSelectedIndex();
        UserCostPair[] userCostPair = mAdapter.getUserCostTable();
        String[] paidAmt = mAdapter.getPaidAmount();
        Double currPaid = -1.0;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        boolean failToParse = false;

        for(Integer index : selectionMask) {

            if (paidAmt[index].length() == 0) {
                failToParse = true;
            } else {
                currPaid = Double.parseDouble(paidAmt[index]);
            }
            if (currPaid < 0) {
                failToParse = true;
            } else if (currPaid == userCostPair[index].getCost()) {
                // do nothing here...
            } else {
               Log.i("REVIEW_BILL_ACTIVITY", userCostPair[index].getUser().getUserName());
                Log.i("REVIEW_BILL_ACTIVITY", currPaid.toString());
                billInReview.debtMaking(userCostPair[index].getUser(), currPaid);
            }
        }

        if (failToParse) {
            alertDialog.setMessage("Paid amount must not be empty or negative!");
            alertDialog.setTitle("Invalid Paid Amount");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.setCancelable(true);
            alertDialog.create().show();

            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );
        } else if (mAdapter.getSelectionCount() == 0) {
            alertDialog.setMessage("Please select fields by long press!");
            alertDialog.setTitle("No item selected");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.setCancelable(true);
            alertDialog.create().show();

            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );
        } else {
            for (User user : billInReview.getDebtDatabase().keySet()) {
                HashSet<Debt> debts = billInReview.getDebtDatabase().get(user);
                for (Debt debt : debts) {
                    Log.i("REVIEW_BILL_ACTIVITY", "Name of Debtor: " + user.getUserName());
                    Log.i("REVIEW_BILL_ACTIVITY", "Name of Loaner:" + debt.getLoaner().getUserName());
                    Log.i("REVIEW_BILL_ACTIVITY", "Debt: $" + debt.getDebtAmt());
                }
            }
            alertDialog.setMessage("Great! We have saved your debts! You may return to the main " +
                    "page or edit the fields and create debts again!");
            alertDialog.setTitle("Debts created");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.setCancelable(true);
            alertDialog.create().show();

            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );
        }

    }

    public void remindPayment(View view) {
        //TODO: ON BUTTON REMIND, SEND REMINDER TO USERS IN LIST OF VIEWS
    }

    public void backHome(View view) {
        finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createDebtsButton) {
            createDebts(view);
        } else if (view.getId() == R.id.remindPayButton) {
            remindPayment(view);
        } else if (view.getId() == R.id.backToMain) {
            backHome(view);
        } else if (view.getId() == R.id.userCost) {
            int index = mRecyclerView.getChildAdapterPosition(view);
            if (actionMode != null) {
                myToggleSelection(index);
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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//    }

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
        String title = getString(R.string.select_user, mAdapter.getSelectionCount());
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
