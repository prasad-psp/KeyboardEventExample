package com.psp.keyboardeventexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.psp.keyboardeventexample.keyboard.KeyboardEvent;

public class MainActivity extends AppCompatActivity implements KeyboardEvent.onKeyboardEventListener {

    private KeyboardEvent keyboardEvent;

    @Override
    protected void onResume() {
        super.onResume();
        logMsg("onResume");
        attachKeyboardEvent(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        logMsg("onPause");
        attachKeyboardEvent(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logMsg("onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyboardEvent = new KeyboardEvent(this);
    }

    @Override
    public void onKeypadStateChanged(boolean isOpen) {
        if(isOpen) {
            logMsg("keyboard opened");
        }
        else {
            logMsg("keyboard closed");
        }
    }

    private void attachKeyboardEvent(boolean isAttach) {
        if(isAttach) {
            keyboardEvent.attachKeypadEvent(this);
            logMsg("attach keyboard event");
        }
        else {
            keyboardEvent.removeKeypadEvent();
            logMsg("remove keyboard event");
        }
    }

    private void logMsg(String msg) {
        Log.d("KeyboardEventExample",msg);
    }
}