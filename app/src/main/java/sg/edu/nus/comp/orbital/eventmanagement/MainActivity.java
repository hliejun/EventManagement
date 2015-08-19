package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    Intent intent = getIntent();
    protected ArrayList<Group> myGroups = new ArrayList<Group>();
    final static private String GROUPS_FILENAME = "groups.xml";

    protected ArrayList<User> myUsers = new ArrayList<User>();
    final static private String USERS_FILENAME = "users.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void manageGroups(View view)
    {
        Intent intent = new Intent(this, GroupsActivity.class);
        intent.putExtra("FROM_ACTIVITY", "MainActivity");
        startActivityForResult(intent, 0);
    }

    public void manageUsers(View view)
    {
        Intent intent = new Intent(this, UsersActivity.class);
        intent.putExtra("FROM_ACTIVITY", "MainActivity");
        startActivityForResult(intent, 1);
    }

    public void manageInvites(View view)
    {
        Intent intent = new Intent(this, InvitesActivity.class);
        startActivity(intent);
    }

    public void manageEvents(View view)
    {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void manageBills(View view)
    {
        Intent intent = new Intent(this, BillsActivity.class);
        startActivity(intent);
    }

    public void manageDebts(View view)
    {
        Intent intent = new Intent(this, DebtsActivity.class);
        startActivity(intent);
    }

    public void manageSettings(View view)
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == GroupsActivity.RESULT_OK) {
                    try {
                        final String groupname = data.getStringExtra("NEW_GROUP");
                        Snackbar.make(findViewById(android.R.id.content), "You have successfully " +
                                        "created: " + groupname + "! ",
                                Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        readGroupsFromFile();
                                        int count = 0;
                                        for (Group group : myGroups) {
                                            if (group.getGroupName().equals(groupname)) {
                                                break;
                                            } else {
                                                count++;
                                            }
                                        }
                                        myGroups.remove(count);
                                        writeGroupsToFile();
                                    }})
                                .setActionTextColor(getResources().getColor(R.color.blue))
                                .show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "Group creation was " +
                                    "unsuccessful! Please try again!",
                            Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.blue))
                            .show();
                }
                break;
            }
            case (1) : {
                if (resultCode == UsersActivity.RESULT_OK) {
                    try {
                        final String username = data.getStringExtra("NEW_USER");
                        Snackbar.make(findViewById(android.R.id.content), "You have successfully " +
                                        "created: " + username + "! ",
                                Snackbar.LENGTH_LONG)
                                .setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        readUsersFromFile();
                                        int count = 0;
                                        for (User user : myUsers) {
                                            if (user.getUserName().equals(username)) {
                                                break;
                                            } else {
                                                count++;
                                            }
                                        }
                                        myUsers.remove(count);
                                        writeUsersToFile();
                                    }})
                                .setActionTextColor(getResources().getColor(R.color.blue))
                                .show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "User creation was " +
                                    "unsuccessful! Please try again!",
                            Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.blue))
                            .show();
                }
                break;
            }
        }
    }

    public boolean writeGroupsToFile( ) {
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
            fos = getApplicationContext().openFileOutput(GROUPS_FILENAME, Context
                    .MODE_PRIVATE);
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

    public boolean writeUsersToFile() {
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
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
