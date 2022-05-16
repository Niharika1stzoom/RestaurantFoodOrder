package com.zoom.happiestplacesrestaurant.di;

import com.zoom.happiestplacesrestaurant.network.ApiInterface;
import com.zoom.happiestplacesrestaurant.ui.login.data.LoginRepository;
import com.zoom.happiestplacesrestaurant.ui.order.OrderRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class APIModule {
   // String baseURL="https://mocki.io/v1/";
    String baseURL="https://restaurants.happiestplaces.com/api/";


    @Singleton
    @Provides
    public ApiInterface getRestApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }
    @Singleton
    @Provides
    public Retrofit getRetroInstance() {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @Singleton
    @Provides
    OrderRepository provideOrderRepository(ApiInterface apiInterface){
        return new OrderRepository(apiInterface);
    }
    @Singleton
    @Provides
    LoginRepository provideLoginRepository(ApiInterface apiInterface){
        return new LoginRepository(apiInterface);
    }
}
