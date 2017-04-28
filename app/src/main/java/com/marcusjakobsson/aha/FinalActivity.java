package com.marcusjakobsson.aha;


import android.content.Context;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oralb.sdk.OBTSDK;

import java.util.ArrayList;

public class FinalActivity extends MyOBTBrushListener{

    //TODO: Gå tillbaka till föregående vyer för att ändra tid osv.

    private MyOBTSdkAuthListener authListener;
    private AlertActivity alertActivity;
    private SharedPreferences sharedPreferences;
    private static ImageView catImage;
    private static ImageButton refreshButton;

    //private static String TAG = FinalActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    Toolbar myToolbar;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.bars, null);
                myToolbar.setNavigationIcon(d);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        catImage = (ImageView)findViewById(R.id.cat_image);
        refreshButton = (ImageButton)findViewById(R.id.button_refresh);

        TextView username = (TextView)findViewById(R.id.userName);
        sharedPreferences = Constants.getSharedPreferences();
        String name = sharedPreferences.getString("name", ""); //finns inget värde att hämta blir det default värde ""
        username.setText(name);

        controlConnections();

        authListener = new MyOBTSdkAuthListener();
        alertActivity = new AlertActivity();

        mNavItems.add(new NavItem("Vaknar "+Constants.getSharedPreferences().getString("wakeUpTime", ""), "Ändra tiden när du vaknar", R.drawable.sun));
        mNavItems.add(new NavItem("Sover "+Constants.getSharedPreferences().getString("sleepTime", ""), "Ändra tiden du går och lägger dig", R.drawable.moon));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,myToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        //Call to initialize the OBTSDK
        Log.i("main", "OBT SDK initialized");
        authorizeSDK();
    } //End onCreate





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private void selectItemFromDrawer(int position) {

        mDrawerList.setItemChecked(position, true);
        // Close the drawer
        //mDrawerLayout.closeDrawer(mDrawerPane);
        if (position == 0)
        {
            Constants.getSleepAlarm().stopAlarm();
            Constants.getWakeUpAlarm().stopAlarm();
            Intent intent = new Intent(getApplicationContext(), WakeUpActivity.class);
            startActivity(intent);
        }else if(position == 1)
        {
            Constants.getSleepAlarm().stopAlarm();
            Constants.getWakeUpAlarm().stopAlarm();
            Intent intent = new Intent(getApplicationContext(), SleepActivity.class);
            startActivity(intent);
        }

    }



    private void controlConnections() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        if(!(sharedPreferences.getBoolean("brushHasConnected", false))){
            Log.i("Has Connected", "FIRST TIME");
            catStatus("FIRST_TIME");
        }

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null) { // connected to the internet
            Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
            startActivity(intent);
        }
    }




    private void authorizeSDK()
    {
        OBTSDK.authorizeSdk(authListener);
    }




    public static void showButton(){
        refreshButton.setVisibility(View.VISIBLE);
    }




    public static void hideButton(){
        refreshButton.setVisibility(View.GONE);
    }




    public static void catStatus(String status){
        switch (status){
            case "FAILED":
                catImage.setImageResource(R.drawable.cat_failed);
                break;
            case "FIRST_TIME":
                catImage.setImageResource(R.drawable.cat_brush);
                break;
            default:
                catImage.setImageResource(R.drawable.cat);
                break;
        }
    }




    public void button_Refresh(View view)
    {
        OBTSDK.disconnectToothbrush();
        authorizeSDK();
    }




    /*public void button_back(View view){
        OBTSDK.disconnectToothbrush();
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }*/






    @Override
    public void onBackPressed() {
    }

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }



} //End FinalActivity
