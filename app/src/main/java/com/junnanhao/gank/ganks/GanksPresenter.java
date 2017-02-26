package com.junnanhao.gank.ganks;


import com.junnanhao.gank.data.source.GankRepository;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

        gankRepository.getGanks(2017, 2, 17, forceUpdate)
                .subscribeOn(Schedulers.io())
                .map(responseReply -> responseReply.getData().results())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> view.showGanks(data), throwable -> {
                    Timber.e(throwable);
                    if(throwable instanceof IOException){
                        // todo: prompt network fail
                        view.showLoadingGanksError();
                    }
                    // todo: permission
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
