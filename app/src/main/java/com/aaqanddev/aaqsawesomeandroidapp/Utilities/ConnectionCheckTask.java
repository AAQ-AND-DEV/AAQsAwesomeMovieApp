package com.aaqanddev.aaqsawesomeandroidapp.Utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionCheckTask extends AsyncTask<Void, Void, Boolean> {
    public Consumer mConsumer;
    public interface Consumer { void accept(Boolean internet);}

    public ConnectionCheckTask(Consumer consumer) {mConsumer = consumer; execute(); }

    @Override
    protected Boolean doInBackground(Void... voids) { try {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("8.8.8.8", 53),1500);
        socket.close();
        return true;
    } catch (IOException e){
        return false;
    }}

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        mConsumer.accept(aBoolean);
    }
}
