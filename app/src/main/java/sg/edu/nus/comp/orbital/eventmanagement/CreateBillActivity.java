package sg.edu.nus.comp.orbital.eventmanagement;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GestureDetectorCompat;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuInflater;
import static android.view.GestureDetector.SimpleOnGestureListener;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

// Create a new bill:
//  - can be miscellaneous (if from BillsActivity)
//  - or event-based (if from EventActivity)
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CreateBillActivity extends ActionBarActivity implements RecyclerView
        .OnItemTouchListener,
        View.OnClickListener,
        ActionMode.Callback {

    protected ListOfPurchases myPurchases = null;
    protected Bill billInReview = null;
    protected Context mContext;
    final static private String USER_FILENAME = "masterUser.xml";
    protected User masterUser;

    protected EditText billTitleField = null;

    protected GestureDetectorCompat gestureDetector = null;
    protected ActionMode actionMode = null;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected CreateBillAdapter mAdapter;
    protected TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        // Initialize layout
        setContentView(R.layout.activity_create_bill);
        ContextManager.context = mContext;

//        // Dummy Purchases Array
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
//        User user20 = new User("LaLa", "912314923");
//        User user21 = new User("Po", "094384928");
//        //User mPayer = new User("AH LONG", "1800 555 0000");
//
//        Group group = new Group("HAPZ");
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
//        group.addUser(user20);
//        group.addUser(user21);

        Intent intent = getIntent();
//        String previousActivity = intent.getStringExtra("FROM_ACTIVITY");
//        switch(previousActivity) {
//            case "BillsActivity":
//                User mPayer = new User("AH LONG", "1800 555 0000");
//                billInReview = new Bill(mPayer);
//                //TODO: Add group to Bill (Define method)
//                break;
//            default:
//                Log.d("CREATE_BILL_ACTIVITY", "Error constructing bill!");
//                break;
//        }

        //TODO: Retrieve mPayer from database, or set as global from start

//        billInReview.addItem("Sheltox", "Tools", 6.45);
//        billInReview.addItem("Noodle", "Food", 2.75);
//        billInReview.addItem("Beer", "Beverage", 4.15);
//        billInReview.addItem("Lollipop", "Snacks", 1.20);
//        billInReview.addItem("Pokeball", "Tools", 100);
//        billInReview.addItem("Green Tea", "Beverage", 2.65);
//        billInReview.addItem("Yang Chow Fried Rice", "Food", 4.45);
//        billInReview.addItem("Xian Dan Ji Ding Rice", "Food", 3.80);
//        billInReview.addItem("Chicken Rice", "Food", 3.50);
//        billInReview.addItem("Chilly Crab", "Food", 8);
//
//        ArrayList<String> sheltoxGang = new ArrayList<String>();
//        sheltoxGang.add("Leonardo");
//        sheltoxGang.add("Bieber");
//        sheltoxGang.add("Aladdin");
//
//        ArrayList<String> pgprOrders = new ArrayList<String>();
//        pgprOrders.add("Leonardo");
//        pgprOrders.add("Shammu");
//        pgprOrders.add("Dennis");
//        pgprOrders.add("Seraphine");
//        pgprOrders.add("Terence");
//        pgprOrders.add("Courtney");
//        pgprOrders.add("Louis");
//        pgprOrders.add("Xian Dan Ji Ding Rice");
//        pgprOrders.add("200");
//
//        ArrayList<String> lollipop = new ArrayList<String>();
//        lollipop.add("Betty");
//        lollipop.add("Binarie");
//        lollipop.add("Shaunti");
//        lollipop.add("Denise");
//        lollipop.add("Thomas");
//        lollipop.add("Vishnu");
//        lollipop.add("Ridhwan");
//        lollipop.add("Lollipop");
//        lollipop.add("500");
//
//        billInReview.addPurchase("Cecilia", "Lollipop", 5);
//        billInReview.addPurchase("Denise", "Pokeball", 1);
//        billInReview.addPurchase("Vishnu", "Xian Dan Ji Ding Rice", 1);
//        billInReview.addPurchase("Thomas", "Beer", 1);
//        //billInReview.addPurchase(sheltoxGang, "Sheltox", 23);
//        //billInReview.addPurchase(pgprOrders);
//        //billInReview.addPurchase(lollipop);
//        billInReview.addPurchase("LaLa", "Chicken Rice", 1);
//        billInReview.addPurchase("Po", "Chilly Crab", 1);

        //TODO: Shift to confirm method
//        billInReview.setList();
//        myPurchases = billInReview.getListOfPurchases();


        // Set Layout Manager and RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.scrollToPosition(0);
        mRecyclerView = (RecyclerView) findViewById(R.id.create_bill_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setScrollContainer(true);

        // Instantiate the adapter and pass in its data source:
        mAdapter = new CreateBillAdapter(/*myPurchases*/);

        // Plug the adapter into the RecyclerView:
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());

        billTitleField = (EditText) findViewById(R.id.bill_title);
        billTitleField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard();
                }
            }
        });

        Button addPurchaseButton = (Button) findViewById(R.id.addPurchaseButton);
        Button confirmBill = (Button) findViewById(R.id.confirmButton);
        emptyView = (TextView) findViewById(R.id.empty_view);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.ttf");

        if (mAdapter.getPurchases() == null || mAdapter.getPurchases().isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setTypeface(type);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        billTitleField.setTypeface(type);
        addPurchaseButton.setTypeface(type);
        confirmBill.setTypeface(type);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getBaseContext().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(billTitleField.getWindowToken(), 0);
    }

    public void addPurchase(View view) {
        Intent intent = new Intent(this, AddPurchaseActivity.class);
        startActivityForResult(intent, 0);
    }

    public void dialogQuery(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Context context = this.getApplicationContext();
        LinearLayout layout = new LinearLayout(context, null, R.style.appCompatDialog);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText gstField = new EditText(context);
        gstField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        gstField.setHint("GST (%)");
        layout.addView(gstField);

        final EditText serviceChargeField = new EditText(context);
        serviceChargeField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        serviceChargeField.setHint("Svc. Charges (%)");
        layout.addView(serviceChargeField);

        final EditText discountField = new EditText(context);
        discountField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        discountField.setHint("Discounts ($)");
        layout.addView(discountField);

        final EditText additionalChargesField = new EditText(context);
        additionalChargesField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        additionalChargesField.setHint("Addt. Charges ($)");
        layout.addView(additionalChargesField);


        builder.setTitle("Enter Tax Rate!");
        builder.setView(layout);

        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener
                () {
            @Override
            public void onClick(View v) {
                double gst = 0;
                double svc = 0;
                double addtCost = 0;
                double discount = 0;

                if (gstField.getText() != null && gstField.getText().length() > 0) {
                    gst = Double.parseDouble(gstField.getText().toString());
                }
                if (serviceChargeField.getText() != null && serviceChargeField.getText().length() > 0) {
                    svc = Double.parseDouble(serviceChargeField.getText().toString());
                }
                if (additionalChargesField.getText() != null && additionalChargesField.getText()
                        .length() > 0) {
                    addtCost = Double.parseDouble(additionalChargesField.getText().toString());
                }
                if (discountField.getText() != null && discountField.getText()
                        .length() > 0) {
                    discount = Double.parseDouble(discountField.getText().toString());
                }

                if (gst > 1 || gst < 0) {
                    gstField.setError("GST must be decimal between 0 to 1!");
                    //Toast.makeText(getApplicationContext(), gstField.getError(), Toast.LENGTH_SHORT).show();
                } else if (svc > 1 || svc < 0) {
                    serviceChargeField.setError("Svc. charge must be decimal between 0 and 1!");
                    //Toast.makeText(getApplicationContext(), serviceChargeField.getError(), Toast.LENGTH_SHORT).show();
                } else if (addtCost < 0) {
                    additionalChargesField.setError("Addt. cost must not be negative!");
                    //Toast.makeText(getApplicationContext(), additionalChargesField.getError(),Toast.LENGTH_SHORT).show();
                } else if (discount < 0) {
                    discountField.setError("Discount must not be negative!");
                    //Toast.makeText(getApplicationContext(), discountField.getError(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    confirmBill(gst, svc, addtCost, discount);

                }
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener
                () {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void confirmBill(double gst, double svc, double addtCost, double discount) {
        Intent intent = new Intent(this, ReviewBillActivity.class);
        Bundle bundle = new Bundle();

        ArrayList<Purchase> purchases = mAdapter.getPurchases();

        // TODO: Acquire mPayer, Group from database AND event name from input field to be added
        readMasterUserFromFile();
        Group group = new Group("RANDOM");
        final EditText billTitleField = (EditText) findViewById(R.id.bill_title);
        String billTitle = billTitleField.getText().toString();
        billInReview = new Bill(masterUser);
        if (billTitle != null) {
            billInReview.setBillTitle(billTitle);
        }

        for (Purchase purchase : purchases) {
            billInReview.addPurchase(purchase);
        }

        billInReview.setGST(gst);
        billInReview.setServiceTax(svc);
        billInReview.setAdditionalCost(addtCost);
        billInReview.setDiscount(discount);

        //billInReview.setList();
        bundle.putParcelable("NEW_BILL", billInReview);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view == null) {
            // do nothing ...
        } else if (view.getId() == R.id.addPurchaseButton) {
            addPurchase(view);
        } else if (view.getId() == R.id.confirmButton) {
            checkState(view);
        } else if (view.getId() == R.id.purchase) {
            int index = mRecyclerView.getChildAdapterPosition(view);
            if (actionMode != null) {
                myToggleSelection(index);
            }
        } else {
            // do nothing ...
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_create_bill, menu);
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
        inflater.inflate(R.menu.menu_create_bill, menu);
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
            actionMode = startActionMode(CreateBillActivity.this);
            int index = mRecyclerView.getChildAdapterPosition(view);
            myToggleSelection(index);
            super.onLongPress(e);
        }
    }

    private void myToggleSelection(int index) {
        mAdapter.toggleSelection(index);
        String title = "SELECT PURCHASES";
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (0): {
                if (resultCode == AddPurchaseActivity.RESULT_OK) {
                    try {
                        Bundle bundle = data.getExtras();
                        Purchase purchase = bundle.getParcelable("NEW_PURCHASE");
                        //billInReview.addPurchase(purchase);
                        mAdapter.addPurchase(purchase);
                        Log.d("PURCHASE QUANTITY", Integer.toString(purchase.getQuantity()));
                        mAdapter.notifyDataSetChanged();

                        if (mAdapter.getPurchases() == null || mAdapter.getPurchases().isEmpty()) {
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    private boolean readMasterUserFromFile() {
        FileInputStream fin;
        ObjectInputStream ois = null;
        try {
            fin = getApplicationContext().openFileInput(USER_FILENAME);
            ois = new ObjectInputStream(fin);
            masterUser = (User) ois.readObject();
            ois.close();
            Log.d("USER_LOGIN", "Master user read successfully");
            return true;
        } catch (Exception e) {
            Log.d("USER_LOGIN", "Cant read saved user" + e.getMessage());
            return false;
        } finally {
            if (ois != null)
                try {
                    ois.close();
                } catch (Exception e) {
                    Log.d("USER_LOGIN", "Error in closing stream while reading user" + e
                            .getMessage());
                }
        }
    }

    public void checkState(View view) {

        ArrayList<Purchase> purchases = mAdapter.getPurchases();

        if(purchases==null||purchases.isEmpty())

        {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Oh no! You cannot create a bill without any purchases! Click " +
                    "the ADD NEW PURCHASE button to create purchases!");
            alertDialog.setTitle("No purchases detected...");
            alertDialog.setPositiveButton("Okay, got it!", null);
            alertDialog.setCancelable(true);
            alertDialog.create().show();
        }

        else
        {
            dialogQuery(view);
        }
    }
}
