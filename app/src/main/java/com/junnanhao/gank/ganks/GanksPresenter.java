package com.junnanhao.gank.ganks;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.junnanhao.gank.data.gson.AutoValueGson_AutoValueGsonFactory;
import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.data.models.Response;
import com.junnanhao.gank.data.source.GankRepository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.Reply;
import io.rx_cache2.Source;
import timber.log.Timber;

/**
 * Created by Jonas on 2017/2/25.
 * Listens to user actions from the UI ({@link GanksContract.View}),
 * retrieves the data and updates the  UI as required.
 */

public class GanksPresenter implements GanksContract.Presenter {
    private GankRepository gankRepository;
    private GanksContract.View view;
    private boolean firstLoad = true;

    public GanksPresenter(GankRepository gankRepository, GanksContract.View view) {
        this.gankRepository = gankRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        // todo: check history
        if (firstLoad) {
            loadGanks(true);
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }


    @Override
    public void loadGanks(boolean forceUpdate) {
        // todo: specific error handling
        // todo: check if today has update

        view.setLoadingIndicator(true);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoValueGson_AutoValueGsonFactory()).create();

        gankRepository.getGanks(2017, 2, 17, forceUpdate)
                .subscribeOn(Schedulers.io())
                .map(responseReply -> {
                    Timber.d("source from: %s", responseReply.getSource());
                    if (responseReply.getData().results() instanceof LinkedTreeMap) {
                        LinkedTreeMap<?, ?> yourMap = (LinkedTreeMap<?, ?>) responseReply.getData().results();
                        JsonObject jsonObject = gson.toJsonTree(yourMap).getAsJsonObject();
                        Type type = new TypeToken<Map<String, List<Gank>>>() {
                        }.getType();
                        return gson.fromJson(jsonObject, type);
                    }
                    return responseReply.getData().results();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> view.showGanks(data), throwable -> {
                    Timber.e(throwable);
                    if (throwable instanceof IOException) {
                        view.showNetworkError();
                    }
                    view.setLoadingIndicator(false);
                    view.showLoadingGanksError();
                }, () -> {
                    firstLoad = false;
                    view.setLoadingIndicator(false);
                });

    }


    @Override
    public void submitGank() {
        view.showSubmitGank();
    }

    @Override
    public void setFiltering(GankFilterType requestType) {

    }

    @Override
    public GankFilterType getFiltering() {
        return null;
    }
}
