package com.msg91.sendotp.sample;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class AmayalockAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {

    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        // Inform parent of app being disabled.
    }
}