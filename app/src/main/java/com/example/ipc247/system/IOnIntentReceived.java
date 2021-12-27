package com.example.ipc247.system;

import android.content.Intent;

public interface IOnIntentReceived {
    void onIntent(Intent i, int resultCode);
}
