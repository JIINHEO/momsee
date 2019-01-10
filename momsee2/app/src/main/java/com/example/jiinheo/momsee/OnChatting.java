package com.example.jiinheo.momsee;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class OnChatting extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    EditText editText;
    Button sendButton;
    String userName;
    ArrayAdapter<String> adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public void onClick(View v) {
        ChatData chatData = new ChatData(userName, editText.getText().toString());
        databaseReference.child("message").push().setValue(chatData);
        editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_chatting);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.Text);
        sendButton = (Button) findViewById(R.id.Send);
        sendButton.setOnClickListener(this);
        userName = "user" + new Random().nextInt(10000);
        ArrayList<String> MyListView = new ArrayList<String>();
        ArrayAdapter<String> MyArrayAdapter;
        MyArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, MyListView);
        ListView MyList= (ListView)findViewById(R.id.listview1);
        MyList.setAdapter(MyArrayAdapter);
        MyList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        MyList.setDivider(new ColorDrawable(Color.GRAY));
        MyList.setDividerHeight(10);

    }

    public class ChatData {
        private String userName;
        private String message;

        public ChatData() { }

        public ChatData(String userName, String message) {
            this.userName = userName;
            this.message = message;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
