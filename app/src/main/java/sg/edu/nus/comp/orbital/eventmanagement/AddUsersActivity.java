package sg.edu.nus.comp.orbital.eventmanagement;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class AddUsersActivity extends AppCompatActivity implements android.widget.SearchView.OnCloseListener{

    protected ArrayList<User> selected_contacts = new ArrayList<>();
    protected ViewPager viewPager;
    protected TabLayout tabLayout;
    protected Toolbar toolbar;
    protected AddUsersFragmentPagerAdapter adapter;
    protected RecyclerView recycler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // Remove title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_users);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        toolbar = (Toolbar) findViewById(R.id.add_users_toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.add_users_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.add_users_tabs);
        adapter = new AddUsersFragmentPagerAdapter(getSupportFragmentManager(),
                AddUsersActivity.this);

        adapter.addFragment(new AddUserGroupFragment(), "Group");
        adapter.addFragment(new AddUserSingleFragment(), "User");
        adapter.addFragment(new AddUserContactListFragment(), "Contacts");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                Toast toast = Toast.makeText(getApplicationContext(), tab.getText(), Toast
//                        .LENGTH_SHORT);
//                toast.show();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
//        final SearchView searchView =
//                (SearchView) MenuItemCompat.getActionView(searchMenuItem);
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//        return true;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         //Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_users, menu);
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
//        final SearchView searchView =
//                (SearchView) MenuItemCompat.getActionView(searchMenuItem);
//
//        final LinearLayout searchBar = (LinearLayout) searchView.findViewById(R.id.search_bar);
//
//        //Give the LinearLayout a transition animation.
//        searchBar.setLayoutTransition(new LayoutTransition());
//        ActionBar.LayoutParams p= new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.MATCH_PARENT);
//        searchView.setLayoutParams(p);
//
//
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        if (searchView != null) {
//            switch(viewPager.getCurrentItem()) {
//                case 0: {
//                    recycler = ((AddUserGroupFragment)(adapter.getItem(viewPager.getCurrentItem()
//                    ))).getRecyclerView();
//                    break;
//                }
//
//                case 1: {
//                    recycler = ((AddUserSingleFragment)(adapter.getItem(viewPager.getCurrentItem()
//                    ))).getRecyclerView();
//                    break;
//                }
//
//                case 2: {
//                    recycler = ((AddUserContactListFragment)(adapter.getItem(viewPager
//                                    .getCurrentItem()
//                    ))).getRecyclerView();
//                    break;
//                }
//            }
//            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View view, boolean queryTextFocused) {
//                    //Log.d("DEBUGGING", "FOCUS CHANGE is called");
//                    if (!queryTextFocused) {
//                        MenuItemCompat.collapseActionView(searchMenuItem);
//                    }
//                }
//            });
//
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    //Log.d("DEBUGGING", "onQueryTextSubmit is called");
//                    if (recycler != null && viewPager.getCurrentItem() == 0) {
//                        ((AddUsersAdapter) recycler.getAdapter()).setFilterGroup(query);
//                        recycler.scrollToPosition(0);
//                    } else if (recycler != null && viewPager.getCurrentItem() == 1) {
//                        ((AddUsersAdapter) recycler.getAdapter()).setFilterUser(query);
//                        recycler.scrollToPosition(0);
//                    } else if (recycler != null && viewPager.getCurrentItem() == 2) {
//                        ((AddUsersAdapter) recycler.getAdapter()).setFilterContact(query);
//                        recycler.scrollToPosition(0);
//                    }
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String query) {
//                    //Log.d("DEBUGGING", "onQueryTextChange is called");
//                    if (recycler != null && viewPager.getCurrentItem() == 0) {
//                        ((AddUsersAdapter) recycler.getAdapter()).setFilterGroup(query);
//                        recycler.scrollToPosition(0);
//                    } else if (recycler != null && viewPager.getCurrentItem() == 1) {
//                        ((AddUsersAdapter) recycler.getAdapter()).setFilterUser(query);
//                        recycler.scrollToPosition(0);
//                    } else if (recycler != null && viewPager.getCurrentItem() == 2) {
//                        ((AddUsersAdapter) recycler.getAdapter()).setFilterContact(query);
//                        recycler.scrollToPosition(0);
//                    }
//                    return true;
//                }
//            });
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
////
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void confirmUsers(View view) {
        // Add users to purchase
        Intent output = new Intent();
        ArrayList<User> users = new ArrayList<User>();
        AddUserGroupFragment groupFrag = (AddUserGroupFragment)adapter.getItem(0);
        AddUserSingleFragment singleFrag = (AddUserSingleFragment)adapter.getItem(1);
        AddUserContactListFragment contactFrag = (AddUserContactListFragment)adapter.getItem(2);

        for (User user: groupFrag.getSelection()) {
            users.add(user);
        }

        for (User user: singleFrag.getSelection()) {
            users.add(user);
        }

        for (User user: contactFrag.getSelection()) {
            users.add(user);
        }

        output.putExtra("USER_SET", users);
        setResult(RESULT_OK, output);

        for (User user : users) {
            Log.d("USER LIST", user.getUserName());
        }

        finish();
    }

    @Override
    public boolean onClose() {
        return false;
    }
}
