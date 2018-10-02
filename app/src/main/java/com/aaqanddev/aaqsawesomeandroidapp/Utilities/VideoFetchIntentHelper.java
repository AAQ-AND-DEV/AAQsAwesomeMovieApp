package com.aaqanddev.aaqsawesomeandroidapp.Utilities;

import android.content.Context;

public interface VideoFetchIntentHelper {
    void watchVideo(Context context, int pos);
    //TODO (rf) want to make helper method (to refactor for generalized interface)
    //String getTargetKeyFromPos(int pos);

}
