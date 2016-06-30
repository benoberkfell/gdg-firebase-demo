package com.example.firebasefun;

import android.app.Application;

import com.example.firebasefun.modules.FirebaseModule;

import dagger.ObjectGraph;

public class FirebaseFunApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        setupObjectGraph();
    }

    private void setupObjectGraph() {
        if (objectGraph == null) {
            objectGraph = ObjectGraph.create(new FirebaseModule());
        }
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }
}
