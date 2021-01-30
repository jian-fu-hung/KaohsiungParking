package com.jerryhong.kaohsiungparking.util;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public final class PermissionUtils {

    private PermissionUtils(){

    }

    public static boolean checkPermission(Context context, String... permissions){
        //檢查是否取得權限
        if (permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
