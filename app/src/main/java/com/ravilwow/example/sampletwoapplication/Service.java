package com.ravilwow.example.sampletwoapplication;

import android.content.Intent;
import android.os.IBinder;

/**
 * Created by ravikwow
 * Date: 02.11.17.
 */
public class Service extends android.app.Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
