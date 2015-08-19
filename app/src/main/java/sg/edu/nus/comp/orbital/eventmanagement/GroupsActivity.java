package sg.edu.nus.comp.orbital.eventmanagement;

import android.app.Activity;
import android.content.Context;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class GroupsActivity extends ActionBarActivity {

    protected HashSet<User> users = new HashSet<User>();
    protected GroupsActivity thisActivity = null;

    protected ArrayList<Group> myGroups = new ArrayList<Group>();
    final static private String GROUPS_FILENAME = "groups.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        thisActivity = this;
        Button addGroupButton = (Button)findViewById(R.id.confirmCreateGroup);
        Button addUserButton = (Button)findViewById(R.id.addUsersButton);
        final EditText groupNameField = (EditText)findViewById(R.id.groupName);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.ttf");

        groupNameField.setTypeface(type);
        addGroupButton.setTypeface(type);
        addUserButton.setTypeface(type);

//        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        // Fetch users into users set

        addGroupButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        if(groupNameField.getText() == null || groupNameField.getText().toString()
                                .length() == 0)
                        {
                            groupNameField.requestFocus();
                            groupNameField.setError("Group name cannot be left empty!");
                        }
                        else if (users == null || users.isEmpty()) {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(thisActivity);
                            alertDialog.setMessage("Oh no! You cannot create a group without " +
                                    "specifying user(s)! Click " +
                                    "the ADD USERS button to add users!");
                            alertDialog.setTitle("No users detected...");
                            alertDialog.setPositiveButton("Okay, got it!", null);
                            alertDialog.setCancelable(true);
                            alertDialog.create().show();
                        }
                        else
                        {
                            String groupName = groupNameField.getText().toString();

                            try {
                                Group group = new Group(groupName);
                                for (User user : users) {
                                    group.addUser(user);
                                }
                                // Write Group
                                readGroupsFromFile();
                                writeGroupsToFile(group);
                                Intent output = new Intent();
                                output.putExtra("NEW_GROUP", group.getGroupName());
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
        getMenuInflater().inflate(R.menu.menu_groups, menu);
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

    public boolean writeGroupsToFile(Group group) {
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
            if (myGroups == null) {
                myGroups = new ArrayList<Group>();
            }
            myGroups.add(group);
            fos = getApplicationContext().openFileOutput(GROUPS_FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(myGroups);
            oos.close();
            return true;
        }catch(Exception e){
            Log.d("GROUPS", "Can't save groups" + e.getMessage());
            return false;
        }
        finally{
            if(oos!=null)
                try{
                    oos.close();
                }catch(Exception e){
                    Log.d("GROUPS", "Error while closing stream " + e.getMessage());
                }
        }
    }

    private boolean readGroupsFromFile(){
        FileInputStream fin;
        ObjectInputStream ois=null;
        try{
            fin = getApplicationContext().openFileInput(GROUPS_FILENAME);
            ois = new ObjectInputStream(fin);
            myGroups =(ArrayList<Group>)ois.readObject();
            ois.close();
            Log.d("GROUPS", "Groups read successfully");
            return true;
        }catch(Exception e){
            Log.d("GROUPS", "Cant read saved groups"+e.getMessage());
            return false;
        }
        finally{
            if(ois!=null)
                try{
                    ois.close();
                }catch(Exception e){
                    Log.d("GROUPS", "Error in closing stream while reading groups" + e
                            .getMessage());
                }
        }
    }
}
