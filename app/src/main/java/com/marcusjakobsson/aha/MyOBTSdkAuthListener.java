package com.marcusjakobsson.aha;
import android.util.Log;

import com.oralb.sdk.OBTSDK;
import com.oralb.sdk.OBTSdkAuthorizationListener;

/**
 * Created by konstantin_ay on 2017-04-25.
 */

public class MyOBTSdkAuthListener implements OBTSdkAuthorizationListener{
    @Override
    public void onSdkAuthorizationSuccess() {
        Log.i("MyOBTSdkAuthListener", "success");

        try {
            OBTSDK.startScanning();
            Log.i("Scan", "Scanning...");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Scan", "Not scanning...");
        }
    }

    @Override
    public void onSdkAuthorizationFailed(int i) {
        Log.i("MyOBTSdkAuthListener", "failed"+i);
    }
}
