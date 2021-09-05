package com.psp.keyboardeventexample.keyboard;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class KeyboardEvent {

    private boolean isKeyboardShowing = false;

    private final View activityRoot;

    private onKeyboardEventListener keyboardEventListener; // callback listener


    public KeyboardEvent(Activity activity) {
        activityRoot = getActivityRoot(activity);
    }

    public void attachKeypadEvent(onKeyboardEventListener keyboardEventListener) {

        if (keyboardEventListener == null) {
            return;
        }

        this.keyboardEventListener = keyboardEventListener;
        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    public void removeKeypadEvent() {
        activityRoot.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
    }


    private final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            activityRoot.getWindowVisibleDisplayFrame(r);
            int screenHeight = activityRoot.getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                if (!isKeyboardShowing) {  // keyboard is opened
                    isKeyboardShowing = true;
                    keyboardEventListener.onKeypadStateChanged(true);
                }
            } else {
                if (isKeyboardShowing) {   // keyboard is closed
                    isKeyboardShowing = false;
                    keyboardEventListener.onKeypadStateChanged(false);
                }
            }
        }
    };

    private static ViewGroup getContentRoot(Activity activity) {
        return activity.findViewById(android.R.id.content);
    }

    private static View getActivityRoot(Activity activity) {
        return getContentRoot(activity).getRootView();
    }


    public static interface onKeyboardEventListener {
        void onKeypadStateChanged(boolean isOpen);
    }
}