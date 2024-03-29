package com.abdelrahmman.movieshows.util;

import androidx.lifecycle.LiveData;

import com.abdelrahmman.movieshows.network.responses.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class){
            return null;
        }

        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);

        Type rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if (rawObservableType != ApiResponse.class){
            throw new IllegalArgumentException("Type must be a defined resource");
        }

        if (!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("Type must be a parameterized");
        }

        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<Type>(bodyType);
    }
}
