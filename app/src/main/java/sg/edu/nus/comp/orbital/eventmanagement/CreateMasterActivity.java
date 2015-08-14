package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class CreateMasterActivity extends AppCompatActivity {

    final static private String USER_FILENAME = "masterUser.xml";
    protected User master = null;
    protected String mPhoneNumber = null;

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
            Log.d("USER_LOGIN", "Can't save master user" + e.getMessage());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_master);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_master, menu);
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

    public void goToMain(View view) {
        final EditText usernameField = (EditText)findViewById(R.id.username_field);
        final EditText phonenumberField = (EditText)findViewById(R.id.phonenumber_field);
        String username = usernameField.getText().toString();
        TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();
        if(mPhoneNumber == null) {
            mPhoneNumber = phonenumberField.getText().toString();
            // TODO: Cannot get number, obtain number manually using alert dialog
            if(mPhoneNumber == null || mPhoneNumber.length() == 0) {
                phonenumberField.requestFocus();
                phonenumberField.setError("PHONE NUMBER FIELD CANNOT BE EMPTY");
            }
        } else if(username == null || username.length() == 0) {
            usernameField.requestFocus();
            usernameField.setError("USERNAME FIELD CANNOT BE EMPTY");
        } else {
            Log.d("PHONE_NUM", mPhoneNumber.toString());
            // TODO: Read from database to ensure no repeats
            master = new User(username, mPhoneNumber);
            writeMasterUserToFile(master);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
