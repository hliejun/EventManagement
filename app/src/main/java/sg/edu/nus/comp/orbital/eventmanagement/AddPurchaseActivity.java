package sg.edu.nus.comp.orbital.eventmanagement;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);

        Button addPurchaseButton = (Button)findViewById(R.id.confirmCreatePurchase);
        ImageButton addUserButton = (ImageButton)findViewById(R.id.addUser);
        final EditText itemNameField = (EditText)findViewById(R.id.itemName);
        final EditText itemTypeField = (EditText)findViewById(R.id.itemType);
        final EditText itemCostField = (EditText)findViewById(R.id.itemCost);
        final EditText itemQuantityField = (EditText)findViewById(R.id.itemQuantity);

//        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        // Fetch users into users set

        addPurchaseButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        String itemName = itemNameField.getText().toString();
                        String itemType = itemTypeField.getText().toString();
                        double itemCost = Double.parseDouble(itemCostField.getText().toString());
                        int itemQuantity = Integer.parseInt(itemQuantityField.getText().toString());

                        if(itemName.length() == 0)
                        {
                            itemNameField.requestFocus();
                            itemNameField.setError("FIELD CANNOT BE EMPTY");
                        }
                        else if(!itemType.matches("[a-zA-Z ]+"))
                        {
                            itemTypeField.requestFocus();
                            itemTypeField.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                        }

                        // ... more validations to be added.

                        else
                        {
                            try {
                                // Build Item
                                Item newItem = new Item(itemName, itemType, itemCost);
                                // Create Purchase here ...
                                Purchase newPurchase = new Purchase(users, newItem, itemQuantity);

                                // Pass Purchase on ...
                                Intent output = new Intent();
                                output.putExtra("NEW_PURCHASE", newPurchase);
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
        if (id == R.id.action_settings) {
            return true;
        }

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
                        for (User user : userArray) {
                            users.add(user);
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
}
