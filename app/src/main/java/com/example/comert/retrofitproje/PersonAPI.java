package com.example.comert.retrofitproje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Comert on 28.06.2016.
 */
public interface PersonAPI {

    @GET("users")
    Call<List<Person>> getPerson();

    /**
     * @return
     */
    @GET("users")
    Call<Geo> getGeo();

    @GET("users")
    Call<Company> getCompany();

    @GET("users")
    Call<Address> getAddress();
}
