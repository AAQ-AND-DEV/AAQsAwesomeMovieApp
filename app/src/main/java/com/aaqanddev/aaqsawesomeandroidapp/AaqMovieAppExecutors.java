package com.aaqanddev.aaqsawesomeandroidapp;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

//Supplemented by research, mostly From Android Architecture persistence demo project
public class AaqMovieAppExecutors {
    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final Executor mMainThread;

    //TODO(implement a timed behavior)to practice ScheduledThreadPoolExecutor
    //TODO(how to handle this situation) not-initialized if using
    //the non-scheduled based executors constructor

    private final ScheduledThreadPoolExecutor mScheduledIO;

    public AaqMovieAppExecutors(){
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor(), new ScheduledThreadPoolExecutor(3));
    }

    private AaqMovieAppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
        this.mScheduledIO = new ScheduledThreadPoolExecutor(3);
    }

    private AaqMovieAppExecutors(Executor diskIO, Executor networkIO,
                                 Executor mainThread,
                                 ScheduledThreadPoolExecutor scheduledIO) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
        this.mScheduledIO = scheduledIO;
    }



    public Executor getmDiskIO() {
        return mDiskIO;
    }

    public Executor getmMainThread() {
        return mMainThread;
    }

    public Executor getmNetworkIO() {
        return mNetworkIO;
    }

    public ScheduledThreadPoolExecutor getmScheduledIO() {
        return mScheduledIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
