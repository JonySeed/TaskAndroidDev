package com.jony.taskandroiddev.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jony.taskandroiddev.R;
import com.jony.taskandroiddev.fragment.AddRecordFragment;
import com.jony.taskandroiddev.fragment.ListFragment;
import com.jony.taskandroiddev.adapter.MyAdapter;
import com.jony.taskandroiddev.fragment.NavigationDrawerFragment;
import com.jony.taskandroiddev.model.HelperFactory;
import com.jony.taskandroiddev.model.entity.Record;

import java.sql.SQLException;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ListFragment.OnFragmentInteractionListener,
        AddRecordFragment.OnButtonClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private final String TAG = this.getClass().getName();


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    protected void onStart() {
        HelperFactory.setHelper(getApplicationContext());
        super.onStart();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_add_rec);
                break;
            case 2:
                mTitle = getString(R.string.title_show_rec);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            // Only show items in the action bar relevant to this screen
//            // if the drawer is not showing. Otherwise, let the drawer
//            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
//            restoreActionBar();
//            return true;
//        }
//        return super.onCreateOptionsMenu(menu);
        return false;

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


    @Override
    protected void onDestroy() {

        HelperFactory.releaseHelper();
        super.onDestroy();

    }

    //implement listener NavigationDrawerFragment
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        switch (position) {
            case 0:

                AddRecordFragment addRecordFragment = AddRecordFragment.newInstance(position + 1);
                fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fTransaction.replace(R.id.container, addRecordFragment);

                break;

            case 1:

                ListFragment listFragment = ListFragment.newInstance(position + 1);
                fTransaction.setCustomAnimations(R.transition.slide_in_left, R.transition.slide_in_right);
                fTransaction.replace(R.id.container, listFragment);

                break;

        }
//        fTransaction.addToBackStack(null);
        fTransaction.commit();


    }

    //implement listener ListFragment class
    @Override
    public void onFragmentInteraction(String id) {
        Log.i(TAG, (id));
    }

    //implement onClickListener ListFragment class
    @Override
    public void onButtonClickListenerListFragment(MyAdapter myAdapter, View v) {
        switch (v.getId()) {
            case R.id.buttoDeleteSelected:

                Log.i(TAG, "onClick buttoDeleteSelected");
                List<Record> selectedRecord = myAdapter.getSelectedItem();

                if (selectedRecord.size() != 0) {
                    try {
                        HelperFactory.getHelper().getRecordDao().deleteList(selectedRecord);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        onNavigationDrawerItemSelected(1);
                    }
                }
                break;

            case R.id.buttoClear:

                Log.i(TAG, "onClick buttonCleat");

                try {
                    HelperFactory.getHelper().getRecordDao().clearList();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    onNavigationDrawerItemSelected(1);
                }
                break;
        }
    }

    //implement listener AddRecordFragment class
    @Override
    public void onButtonClickListenerAddRecordFragment(View v) {
        switch (v.getId()) {
            case R.id.buttoAddRec:

                Log.i(TAG, "onClick buttonAddRec");
                EditText editTextRec = (EditText) findViewById(R.id.editTextRec);
                Record newRec = new Record(editTextRec.getText().toString());

                try {
                    HelperFactory.getHelper().getRecordDao().addRecord(newRec);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    editTextRec.setText("");
                }
                break;

        }
    }
}
