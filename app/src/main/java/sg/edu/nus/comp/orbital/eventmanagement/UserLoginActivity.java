package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UserLoginActivity extends AppCompatActivity {

    final static private String USER_FILENAME = "masterUser.xml";
    protected User masterUser = null;

    public boolean writeMasterUserToFile(User user){
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
            fos = getApplicationContext().openFileOutput(USER_FILENAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
            return true;
        }catch(Exception e){
            Log.d("USER_LOGIN", "Can't save master user"+e.getMessage());
            return false;
        }
        finally{
            if(oos!=null)
                try{
                    oos.close();
                }catch(Exception e){
                    Log.d("USER_LOGIN", "Error while closing stream " + e.getMessage());
                }
        }
    }

    private boolean readMasterUserFromFile(){
        FileInputStream fin;
        ObjectInputStream ois=null;
        try{
            fin = getApplicationContext().openFileInput(USER_FILENAME);
            ois = new ObjectInputStream(fin);
            masterUser =(User)ois.readObject();
            ois.close();
            Log.d("USER_LOGIN", "Master user read successfully");
            return true;
        }catch(Exception e){
            Log.d("USER_LOGIN", "Cant read saved user"+e.getMessage());
            return false;
        }
        finally{
            if(ois!=null)
                try{
                    ois.close();
                }catch(Exception e){
                    Log.d("USER_LOGIN", "Error in closing stream while reading user" + e
                            .getMessage());
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        readMasterUserFromFile();

        Runnable nextAct = new Runnable() {
            @Override
            public void run(){
                if (masterUser == null) {
                    goCreateMaster();
                }
                else {
                    goMain();
                }
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(nextAct, 2000);

//        User user01 = new User("Larry", "91159407");
//        User user02 = new User("Renfred", "26263460");
//        ArrayList<User> newArray = new ArrayList<User>();
//        newArray.add(user01);
//        newArray.add(user02);
//        writeUsersToFile(newArray);
//        readUsersFromFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
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

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void goCreateMaster() {
        Intent intent = new Intent(this, CreateMasterActivity.class);
        startActivity(intent);
        finish();
    }

}
