package sg.edu.nus.comp.orbital.eventmanagement;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashSet;

public class AddPurchaseActivity extends ActionBarActivity {

    protected HashSet<User> users = new HashSet<User>();
    protected AddPurchaseActivity thisActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);

        thisActivity = this;
        Button addPurchaseButton = (Button)findViewById(R.id.confirmCreatePurchase);
        Button addUserButton = (Button)findViewById(R.id.addUsersButton);
        final EditText itemNameField = (EditText)findViewById(R.id.itemName);
        final EditText itemTypeField = (EditText)findViewById(R.id.itemType);
        final EditText itemCostField = (EditText)findViewById(R.id.itemCost);
        final EditText itemQuantityField = (EditText)findViewById(R.id.itemQuantity);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.ttf");

        itemNameField.setTypeface(type);
        itemTypeField.setTypeface(type);
        itemCostField.setTypeface(type);
        itemQuantityField.setTypeface(type);
        addPurchaseButton.setTypeface(type);
        addUserButton.setTypeface(type);

//        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        // Fetch users into users set

        addPurchaseButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        if(itemNameField.getText() == null || itemNameField.getText().toString()
                                .length() == 0)
                        {
                            itemNameField.requestFocus();
                            itemNameField.setError("Item name cannot be left empty!");
                        }
                        else if(itemTypeField.getText() == null || itemTypeField.getText().toString().length() == 0)
                        {
                            itemTypeField.requestFocus();
                            itemTypeField.setError("Item type cannot be left empty!");
                        }
                        else if (!itemTypeField.getText().toString().matches("[A-z\\u00C0-\\u00ff \\\\./-]*")) {
                            itemTypeField.requestFocus();
                            itemTypeField.setError("Item type is strictly alphabetical only!");
                        }
                        else if (itemCostField.getText() == null || itemCostField.getText()
                                .toString().length() == 0) {
                            itemCostField.requestFocus();
                            itemCostField.setError("Unit cost cannot be left empty!");
                        }
                        else if (Double.parseDouble(itemCostField.getText().toString()) <= 0) {
                            itemCostField.requestFocus();
                            itemCostField.setError("Unit cost must be a positive amount!");
                        }
                        else if (itemQuantityField.getText() == null || itemQuantityField.getText()
                                .toString().length() == 0) {
                            itemQuantityField.requestFocus();
                            itemQuantityField.setError("Quantity cannot be left empty!");
                        }
                        else if (Integer.parseInt(itemQuantityField.getText().toString()) <= 0) {
                            itemQuantityField.requestFocus();
                            itemQuantityField.setError("Quantity must be positive!");
                        }
                        else if (users == null || users.isEmpty()) {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(thisActivity);
                            alertDialog.setMessage("Oh no! You cannot create a purchase without " +
                                    "specifying user(s) who made the purchase! Click " +
                                    "the ADD USERS button to add users!");
                            alertDialog.setTitle("No users detected...");
                            alertDialog.setPositiveButton("Okay, got it!", null);
                            alertDialog.setCancelable(true);
                            alertDialog.create().show();
                        }
                        else
                        {
                            String itemName = itemNameField.getText().toString();
                            String itemType = itemTypeField.getText().toString();
                            double itemCost = Double.parseDouble(itemCostField.getText().toString());
                            int itemQuantity = Integer.parseInt(itemQuantityField.getText().toString());

                            try {
                                // Build Item
                                Item newItem = new Item(itemName, itemType, itemCost);
                                // Create Purchase here ...
                                Purchase newPurchase = new Purchase(users, newItem, itemQuantity);

                                // Pass Purchase on ...
                                Intent output = new Intent();
                                output.putExtra("NEW_PURCHASE", (Parcelable)newPurchase);
                                setResult(RESULT_OK, output);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_purchase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void addUsers(View view)
    {
        Intent intent = new Intent(this, AddUsersActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == AddUsersActivity.RESULT_OK) {
                    try {
                        Bundle bundle = data.getExtras();
                        ArrayList<User> userArray = bundle.getParcelableArrayList("USER_SET");
                        users.clear();
                        for (User user : userArray) {
                            users.add(user);
                        }
                        String msg = "";
                        if (userArray == null || userArray.isEmpty()) {
                           msg = "Sorry! There are some problems adding users. Please try again!";
                        } else {
                            msg = "You have added " + Integer.toString(userArray.size()) + " " +
                                    "user(s)";
                        }
                        Snackbar.make(findViewById(android.R.id.content), msg,
                                Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        users.clear();
                                    }})
                                .setActionTextColor(getResources().getColor(R.color.blue))
                                .show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
}
