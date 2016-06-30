package com.example.firebasefun.modules;

import com.example.firebasefun.MainActivity;
import com.example.firebasefun.annotations.ChatsRef;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {MainActivity.class},
        library = true
)
public class FirebaseModule {

    @Provides @Singleton
    FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides @Singleton @ChatsRef
    DatabaseReference provideChatRef(FirebaseDatabase firebaseDatabase) {
        return firebaseDatabase.getReference("chats");
    }
}