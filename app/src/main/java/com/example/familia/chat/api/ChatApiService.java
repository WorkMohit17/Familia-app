package com.example.familia.chat.api;

import com.example.familia.chat.model.ChatRequest;
import com.example.familia.chat.model.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatApiService {
    @POST("/api/chatbot") // Adjust this to match your API endpoint
    Call<ChatResponse> sendMessage(@Body ChatRequest request);
}
