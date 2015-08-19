package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class UsersActivity extends ActionBarActivity {

    protected HashSet<User> users = new HashSet<User>();
    protected UsersActivity thisActivity = null;

    protected ArrayList<User> myUsers = new ArrayList<User>();
    final static private String USERS_FILENAME = "users.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        thisActivity = this;
        Button createUserButton = (Button)findViewById(R.id.confirmCreateUser);
        final EditText userNameField = (EditText)findViewById(R.id.userName);
        final EditText phoneNumberField = (EditText)findViewById(R.id.phoneNumber);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.ttf");

        userNameField.setTypeface(type);
        phoneNumberField.setTypeface(type);
        createUserButton.setTypeface(type);

//        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        // Fetch users into users set

        createUserButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        if(userNameField.getText() == null || userNameField.getText().toString()
                                .length() == 0)
                        {
                            userNameField.requestFocus();
                            userNameField.setError("User name cannot be left empty!");
                        }
                        else if(phoneNumberField.getText() == null || phoneNumberField.getText()
                                .toString()
                                .length() <= 5)
                        {
                            phoneNumberField.requestFocus();
                            phoneNumberField.setError("Phone number cannot be left empty or " +
                                    "shorter than 6 digits!");
                        }
                        else
                        {
                            String userName = userNameField.getText().toString();
                            String phoneNumber = phoneNumberField.getText().toString();

                            try {
                                User user = new User(userName, phoneNumber);

                                // Write User
                                readUsersFromFile();
                                writeUsersToFile(user);
                                Intent output = new Intent();
                                output.putExtra("NEW_USER", user.getUserName());
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
        getMenuInflater().inflate(R.menu.menu_users, menu);
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

    public boolean writeUsersToFile(User user) {
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
            if (myUsers == null) {
                myUsers = new ArrayList<User>();
            }
            myUsers.add(user);
            fos = getApplicationContext().openFileOutput(USERS_FILENAME, Context
                    .MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(myUsers);
            oos.close();
            return true;
        }catch(Exception e){
            Log.d("USERS", "Can't save users" + e.getMessage());
            return false;
        }
        finally{
            if(oos!=null)
                try{
                    oos.close();
                }catch(Exception e){
                    Log.d("USERS", "Error while closing stream " + e.getMessage());
                }
        }
    }

    private boolean readUsersFromFile(){
        FileInputStream fin;
        ObjectInputStream ois=null;
        try{
            fin = getApplicationContext().openFileInput(USERS_FILENAME);
            ois = new ObjectInputStream(fin);
            myUsers =(ArrayList<User>)ois.readObject();
            ois.close();
            Log.d("USERS", "Users read successfully");
            return true;
        }catch(Exception e){
            Log.d("USERS", "Cant read saved users"+e.getMessage());
            return false;
        }
        finally{
            if(ois!=null)
                try{
                    ois.close();
                }catch(Exception e){
                    Log.d("USERS", "Error in closing stream while reading users" + e
                            .getMessage());
                }
        }
    }
}
