package com.junnanhao.gank.data.models;


import java.util.Date;
import java.util.List;


import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapter;

import javax.annotation.Generated;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

@Generated("com.robohorse.robopojogenerator")
@AutoValue
public abstract class Gank {

    @SerializedName("createdAt")
    public abstract Date createdAt();

    @Nullable
    @SerializedName("images")
    public abstract List<String> images();

    @SerializedName("publishedAt")
    public abstract String publishedAt();

    @SerializedName(value = "id", alternate = {"_id", "ganhuo_id"})
    public abstract String id();

    @SerializedName("source")
    public abstract String source();

    @SerializedName("used")
    public abstract boolean used();

    @SerializedName("type")
    public abstract String type();

    @SerializedName("url")
    public abstract String url();

    @SerializedName("desc")
    public abstract String desc();

    @Nullable
    @SerializedName("who")
    public abstract String who();

    public static TypeAdapter<Gank> typeAdapter(Gson gson) {
        return new AutoValue_Gank.GsonTypeAdapter(gson)
                .setDefaultWho("某人");
    }
}