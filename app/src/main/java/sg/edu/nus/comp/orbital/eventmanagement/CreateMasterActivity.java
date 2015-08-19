package sg.edu.nus.comp.orbital.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        final TextView welcomeMsg = (TextView)findViewById(R.id.welcome_text);
        final EditText usernameField = (EditText)findViewById(R.id.username_field);
        final EditText phonenumberField = (EditText)findViewById(R.id.phonenumber_field);
        final Button doneButton = (Button)findViewById(R.id.toMain);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/GoodDog.ttf");
        welcomeMsg.setTypeface(type);
        usernameField.setTypeface(type);
        phonenumberField.setTypeface(type);
        doneButton.setTypeface(type);
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


        return super.onOptionsItemSelected(item);
    }

    public void goToMain(View view) {
        final EditText usernameField = (EditText)findViewById(R.id.username_field);
        final EditText phonenumberField = (EditText)findViewById(R.id.phonenumber_field);
        TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        //mPhoneNumber = null;
        //mPhoneNumber = tMgr.getLine1Number();
        //if(mPhoneNumber == null) {
            // TODO: Cannot get number, obtain number manually using alert dialog

         if(usernameField.getText() == null || usernameField.getText().toString().length() ==
                0) {
            usernameField.requestFocus();
            usernameField.setError("Username cannot be left empty!");
        }
            else if(phonenumberField.getText() == null || phonenumberField.getText().toString()
                    .length
                    () <= 5) {
                phonenumberField.requestFocus();
                phonenumberField.setError("Phone number must be at least 6 digits long!");
            }
       // }

        else {
            String username = usernameField.getText().toString();
            mPhoneNumber = phonenumberField.getText().toString();
            Log.d("PHONE_NUM", mPhoneNumber);
            // TODO: Read from database to ensure no repeats
            master = new User(username, mPhoneNumber);
            writeMasterUserToFile(master);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
