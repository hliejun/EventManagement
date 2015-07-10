package sg.edu.nus.comp.orbital.eventmanagement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class AddUsersActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> name_ref;
    private ArrayList<String> phone_ref ;

    private ArrayList<User> selected_contacts = new ArrayList<>();

    public ArrayList<String> getContact(){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
        ArrayList<String> contacts = new ArrayList<String>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        return contacts;
    }

    public String matchPhoneByName(String name){
        int pos = -1;
        for(int c = 0; c<=name_ref.size()-1;c++){
            if(name.equals(name_ref.get(c))){
                pos = c;
                break;
            }
        }
        return phone_ref.get(pos);
    }

    public ArrayList<String> getNumber(){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
        ArrayList<String> numbers = new ArrayList<String>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                numbers.add(number);
            } while (cursor.moveToNext());
        }
        return numbers;
    }

    public void doStuff(View view){
        TextView tv = (TextView) view.findViewById(R.id.item);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        if(tv.isSelected()){
            String person_name = tv.getText().toString();
            String person_phone = matchPhoneByName(person_name);
            User user = new User(person_name, person_phone);
            selected_contacts.add(user);
        }else if(!tv.isSelected() && selected_contacts.size()!=0){
            String person_name = tv.getText().toString();
            for(int c = 0; c<= selected_contacts.size()-1;c++){
                if(selected_contacts.get(c).getUserName().equals(person_name)){
                    selected_contacts.remove(c);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
        name_ref = this.getContact();
        phone_ref=this.getNumber();
        recyclerView = (RecyclerView) findViewById(R.id.add_users_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddUsersAdapter(getContact());
        recyclerView.setAdapter(adapter);

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmUsers(View view) {
        // Add users to purchase
        Intent output = new Intent();
        output.putExtra("USER_SET", selected_contacts);
        setResult(RESULT_OK, output);
        finish();
    }
}
