package com.marcusjakobsson.aha;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    //TODO: Vid fel under uppkoppling till ORAL-Bs SDK, visa felmeddelande till användare
    //TODO: Erbjud möjligheten att försöka ansluta till ORAL-B på nytt


    //TODO: Berätta för anv. att ansluta tandborste (Vy)
    //TODO: Kolla om Wi-fi är på
    //TODO: Kolla om Bluetooth är på
    //TODO: Kolla om Tandborsten har kopplats, om den inte är så ska man via ett knapptryck koppla om och om igen
    //TODO: Om tandborste har kopplats sen tidigare, stoppa scan och starta vid larm

    //TODO: Gå tillbaka till föregående vyer för att ändra tid osv.

    MyOBTSdkAuthListener authListener;
    AlertActivity alertActivity;
    ImageView catImage;
    private static ImageButton refreshButton;

    //private static String TAG = FinalActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        catImage = (ImageView)findViewById(R.id.cat_image);
        refreshButton = (ImageButton)findViewById(R.id.button_refresh);

        TextView username = (TextView)findViewById(R.id.userName);
        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", ""); //finns inget värde att hämta blir det default värde ""
        username.setText(name);

        authListener = new MyOBTSdkAuthListener();
        alertActivity = new AlertActivity();

        mNavItems.add(new NavItem("Vaknar", "Ändra tiden när du vaknar", R.drawable.cancel));
        mNavItems.add(new NavItem("Sover", "Ändra tiden du går och lägger dig", R.drawable.checked));

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
                //Log.d(TAG, "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


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
            Intent intent = new Intent(getApplicationContext(), WakeUpActivity.class);
            startActivity(intent);
        }else if(position == 1)
        {
            Intent intent = new Intent(getApplicationContext(), SleepActivity.class);
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




    public void button_Refresh(View view)
    {
        OBTSDK.disconnectToothbrush();
        authorizeSDK();
    }



    public void button_back(View view){
        OBTSDK.disconnectToothbrush();
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }




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
