package com.example.familia.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.familia.R;
import com.example.familia.chat.adapter.ChatAdapter;
import com.example.familia.chat.api.ChatApiService;
import com.example.familia.chat.api.RetrofitClient;
import com.example.familia.chat.model.ChatRequest;
import com.example.familia.chat.model.ChatResponse;
import com.example.familia.chat.model.Message;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<String> messages = new ArrayList<>();
    private EditText messageEditText;
    private ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageEditText = findViewById(R.id.editTextMessage);
        sendButton = findViewById(R.id.buttonSend);

        chatAdapter = new ChatAdapter(this, messages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String userMessage = messageEditText.getText().toString().trim();
        if (userMessage.isEmpty()) return;

        messages.add(userMessage);
        chatAdapter.notifyDataSetChanged();
        messageEditText.setText("");

        // Send message to backend
        ChatApiService apiService = RetrofitClient.getApiService();
        Call<ChatResponse> call = apiService.sendMessage(new ChatRequest(userMessage));

        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messages.add(response.body().getResponse());
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}