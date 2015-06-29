package com.jony.taskandroiddev.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.jony.taskandroiddev.fragment.ShowRecordFragment;
import com.jony.taskandroiddev.adapter.ItemListAdapter;
import com.jony.taskandroiddev.fragment.NavigationDrawerFragment;
import com.jony.taskandroiddev.model.HelperFactory;
import com.jony.taskandroiddev.model.entity.Record;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;
import com.vk.sdk.dialogs.VKShareDialog;

import java.sql.SQLException;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        ShowRecordFragment.OnFragmentInteractionListener,
        AddRecordFragment.OnButtonClickListener,
        ItemListAdapter.OnVkShareListener {

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */


    private NavigationDrawerFragment mNavigationDrawerFragment;
    private final String TAG = this.getClass().getName();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }


    private CharSequence mTitle;
    private static final String VK_APP_ID = "4973499";
//    private VKRequest currentRequest;

    private final VKSdkListener sdkListener = new VKSdkListener() {

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            Log.d("VkDemoApp", "onAcceptUserToken " + token);
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            Log.d("VkDemoApp", "onReceiveNewToken " + newToken);
        }

        @Override
        public void onRenewAccessToken(VKAccessToken token) {
            Log.d("VkDemoApp", "onRenewAccessToken " + token);
        }

        @Override
        public void onCaptchaError(VKError captchaError) {
            Log.d("VkDemoApp", "onCaptchaError " + captchaError);
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            Log.d("VkDemoApp", "onTokenExpired " + expiredToken);
        }

        @Override
        public void onAccessDenied(VKError authorizationError) {
            Log.d("VkDemoApp", "onAccessDenied " + authorizationError);
        }

    };


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

        VKSdk.initialize(sdkListener, VK_APP_ID);
        VKUIHelper.onCreate(this);

        HelperFactory.setHelper(getApplicationContext());


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (HelperFactory.getHelper() == null) {
//            Log.i(TAG, "=============================================");
            HelperFactory.setHelper(getApplicationContext());
        }

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

        ActionBar actionBar = getSupportActionBar();
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


    @Override
    protected void onDestroy() {

        super.onDestroy();

        HelperFactory.releaseHelper();
        VKSdk.logout();
        VKUIHelper.onDestroy(this);

//        if (currentRequest != null) {
//            currentRequest.cancel();
//        }
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

                ShowRecordFragment showRecordFragment = ShowRecordFragment.newInstance(position + 1);
                fTransaction.setCustomAnimations(R.transition.slide_in_left, R.transition.slide_in_right);
                fTransaction.replace(R.id.container, showRecordFragment);

                break;

        }
        fTransaction.addToBackStack(null);
        fTransaction.commit();


    }


    //implement onClickListener ListFragment class
    @Override
    public void onButtonClickListenerListFragment( ItemListAdapter itemListAdapter, View v) {
        switch (v.getId()) {
            case R.id.buttoDeleteSelected:

                Log.i(TAG, "onClick buttoDeleteSelected");
                List<Record> selectedRecord = itemListAdapter.getSelectedItem();

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


    //implement share data to wall vk
    @Override
    public void vkShare(Record record) {


        Thread authorizeThread = new Thread() {

            @Override
            public void run() {
                if (!VKSdk.wakeUpSession()) {
                    VKSdk.authorize(VKScope.WALL);
                }
            }
        };
        authorizeThread.start();
        try {
            authorizeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            new VKShareDialog().setText(record.getStr())
                    .setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                        @Override
                        public void onVkShareComplete(int i) {
                            Log.i(TAG, "record shared complete");
                        }

                        @Override
                        public void onVkShareCancel() {
                            Log.i(TAG, "record shared error");
                        }
                    }).show(getSupportFragmentManager(), "VK_SHARE_DIALOG");
        }


//        currentRequest = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID,
//                VKSdk.getAccessToken().userId, VKApiConst.MESSAGE,
//                record.getStr()));
//
//        currentRequest.executeWithListener(new VKRequest.VKRequestListener() {
//            @Override
//            public void onComplete(VKResponse response) {
//                super.onComplete(response);
//                Log.i("==========", "onComplete!!!");
//            }
//
//            @Override
//            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
//                super.attemptFailed(request, attemptNumber, totalAttempts);
//            }
//
//            @Override
//            public void onError(VKError error) {
//                super.onError(error);
//                Log.i("==========","onError!!!" + error.toString());
//            }
//
//            @Override
//            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
//                super.onProgress(progressType, bytesLoaded, bytesTotal);
//            }
//        });

    }
}
