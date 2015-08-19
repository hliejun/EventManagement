package sg.edu.nus.comp.orbital.eventmanagement;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DebtsActivity extends ActionBarActivity {
    protected ViewPager viewPager;
    protected TabLayout tabLayout;
    protected Toolbar toolbar;
    protected DebtsFragmentPagerAdapter adapter;
    protected User masterUser;
    //protected RecyclerView recycler = null;
    final static private String USER_FILENAME = "masterUser.xml";
    final static private String DEBT_DATABASE = "debtData.xml";
    protected HashMap<String, ArrayList<Debt>> debtStorage = null;
    Intent intent = getIntent();
    DebtsActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts);
        thisActivity = this;
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        toolbar = (Toolbar) findViewById(R.id.debts_toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/GoodDog.ttf");
        mTitle.setText(this.getTitle());
        mTitle.setTypeface(type);

        //setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.debts_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.debts_tabs);
        adapter = new DebtsFragmentPagerAdapter(getSupportFragmentManager(), DebtsActivity.this);

        adapter.addFragment(new AssetFragment(), "Assets");
        adapter.addFragment(new DebtFragment(), "Debts");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                Toast toast = Toast.makeText(getApplicationContext(), tab.getText(), Toast
//                        .LENGTH_SHORT);
//                toast.show();
                TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
                Typeface type = Typeface.createFromAsset(getAssets(),"fonts/GoodDog.ttf");
                mTitle.setText(adapter.getTabTitle(tab.getPosition()).toString().toUpperCase());
                mTitle.setTypeface(type);
                //thisActivity.setTitle(adapter.getTabTitle(tab.getPosition()).toString().toUpperCase());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        if (debtStorage != null) {
//            for (String name : debtStorage.keySet()) {
//                Log.d("DEBT_LOGGING", name);
//                for (Debt debt : debtStorage.get(name)) {
//                    Log.d("DEBT_LOGGING", debt.getLoaner().getUserName());
//                    Log.d("DEBT_LOGGING", Double.toString(debt.getDebtAmt()));
//
//                }
//            }
//        } else {
//            Log.d("DEBT_LOGGING", "Error reading...");
//        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_debts, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private boolean readDebtDatabaseFromFile(){
        FileInputStream fin;
        ObjectInputStream ois=null;
        try{
            fin = getApplicationContext().openFileInput(DEBT_DATABASE);
            ois = new ObjectInputStream(fin);
            debtStorage =(HashMap<String, ArrayList<Debt>>)ois.readObject();
            ois.close();
            Log.d("DEBT_DATABASE", "Debt Database read successfully!");
            return true;
        }catch(Exception e){
            Log.d("DEBT_DATABASE", "Cant read debt database"+e.getMessage());
            return false;
        }
        finally{
            if(ois!=null)
                try{
                    ois.close();
                }catch(Exception e){
                    Log.d("DEBT_DATABASE", "Error in closing stream while reading debt database" + e.getMessage());
                }
        }
    }

    public ArrayList<Debt> getDebtList() {
        readMasterUserFromFile();
        readDebtDatabaseFromFile();

        ArrayList<Debt> debtList = new ArrayList<Debt>();

        if (debtStorage == null || debtStorage.get(masterUser.getUserName()) == null) {
            Log.d("DEBT_DATABASE", "Error reading debt database!");
            return debtList;
        } else {
            return debtStorage.get(masterUser.getUserName());
        }
    }

    public ArrayList<Debt> getAssetList() {
        readMasterUserFromFile();
        readDebtDatabaseFromFile();

        ArrayList<Debt> debtList = new ArrayList<Debt>();

        if (debtStorage == null) {
            Log.d("DEBT_DATABASE", "Error reading debt database!");
            //return debtList;
        }

        else {
            for (Map.Entry<String, ArrayList<Debt>> entry : debtStorage.entrySet()) {
                if (!entry.getKey().toLowerCase().equals(masterUser.getUserName().toLowerCase())) {
                    for (Debt debt : entry.getValue()) {
                        debtList.add(debt);
                    }
                }
            }
            //return debtList;
        }

        return debtList;

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
}
