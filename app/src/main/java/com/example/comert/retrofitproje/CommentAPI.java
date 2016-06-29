package com.example.comert.retrofitproje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Comert on 29.06.2016.
 */
public interface CommentAPI {

    @GET("comments")
    Call<List<Comment>> getCommit();
}
