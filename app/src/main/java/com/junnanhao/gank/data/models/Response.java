package com.junnanhao.gank.data.models;

import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapter;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Generated("com.robohorse.robopojogenerator")
@AutoValue
public abstract class Response<T> {

    @SerializedName("error")
    public abstract boolean error();

    @SerializedName("results")
    public abstract T results();

    public static <T> TypeAdapter<Response<T>> typeAdapter
            (Gson gson, TypeToken<? extends Response<T>> typeToken) {
        return new AutoValue_Response.GsonTypeAdapter(gson, typeToken);
    }

}