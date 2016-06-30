package com.example.firebasefun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.firebasefun.annotations.ChatsRef;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    static final String TAG = MainActivity.class.getCanonicalName();

    @Inject @ChatsRef
    DatabaseReference chatsDatabaseReference;

    @BindView(R.id.text_entry)
    EditText entryText;

    @BindView(R.id.send_button)
    Button sendButton;

    @BindView(R.id.messages_list)
    ListView listView;

    FirebaseListAdapter<ChatMessage> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((FirebaseFunApplication) getApplication()).inject(this);

        listAdapter = new FirebaseListAdapter<ChatMessage>(this,
                ChatMessage.class,
                R.layout.message_list_item,
                chatsDatabaseReference) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                CharSequence time = DateUtils.getRelativeDateTimeString(v.getContext(),
                        model.timestamp,
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.DAY_IN_MILLIS,
                        0
                );

                ((TextView)v.findViewById(R.id.message_text)).setText(model.message);
                ((TextView)v.findViewById(R.id.message_time)).setText(time);
            }
        };
        listView.setAdapter(listAdapter);
        entryText.addTextChangedListener(this);
        updateSendButton();
    }

    @OnClick(R.id.send_button)
    protected void sendClicked() {
        String text = entryText.getText().toString();

        if (text.length() > 0) {
            Log.v(TAG, "Text: " + text);

            ChatMessage message = new ChatMessage(text, System.currentTimeMillis());

            String key = chatsDatabaseReference.push().getKey();
            chatsDatabaseReference.child(key).setValue(message);
            entryText.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        updateSendButton();
    }

    private void updateSendButton() {
        sendButton.setEnabled(entryText.length() > 0);
    }
}
