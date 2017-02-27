package com.junnanhao.gank.ganks;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.junnanhao.gank.data.gson.AutoValueGson_AutoValueGsonFactory;
import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.data.source.GankRepository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

/**
 * Created by Jonas on 2017/2/25.
 * Listens to user actions from the UI ({@link GanksContract.View}),
 * retrieves the data and updates the  UI as required.
 */

class GanksPresenter implements GanksContract.Presenter {
    private GankRepository gankRepository;
    private GanksContract.View view;
    private boolean firstLoad = true;
    private Calendar latestDay;

    public static final int HISTORY_DAY_NUMBER = 60;

    GanksPresenter(GankRepository gankRepository, GanksContract.View view) {
        this.gankRepository = gankRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        if (firstLoad) {
            loadGanks(true);
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    private Subject<Calendar> fetchGankHistory(boolean refreshList) {
        Subject<Calendar> subject = AsyncSubject.create();
        gankRepository.getHistory(false)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(responseReply -> responseReply.getData().results())
                .filter(calendars -> calendars.size() > 0)
                .map(strings -> strings.subList(0, HISTORY_DAY_NUMBER))
                .map(strings -> {
                    List<Calendar> calendars = new ArrayList<>(strings.size());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    for (String s : strings) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(sdf.parse(s));
                        calendars.add(calendar);
                    }
                    return calendars;
                })
                .subscribe(calendars -> {
                    view.showCalendarMenu(calendars);
                    latestDay = calendars.get(0);
                    Calendar today = Calendar.getInstance();
                    if (today.get(Calendar.DAY_OF_YEAR) != latestDay.get(Calendar.DAY_OF_YEAR)) {
                        view.showNoTodayGank();
                    }
                    today.setTimeInMillis(System.currentTimeMillis());
                    if (refreshList) {
                        subject.onNext(latestDay);
                        subject.onComplete();
                    }
                });
        return subject;
    }


    @Override
    public void loadGanks(Calendar date, boolean forceUpdate) {
        view.setLoadingIndicator(true);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoValueGson_AutoValueGsonFactory()).create();

        gankRepository.getGanks(date, forceUpdate)
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
                .filter(stringListMap -> stringListMap.size() > 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> view.showGanks(data)
                        , throwable -> {
                            Timber.e(throwable);
                            if (throwable instanceof IOException
                                    || throwable instanceof CompositeException)
                                view.showNetworkError();
                            view.setLoadingIndicator(false);
                            view.showLoadingGanksError();
                        }, () -> {
                            firstLoad = false;
                            view.setLoadingIndicator(false);
                        });
    }


    @Override
    public void loadGanks(boolean forceUpdate) {
        if (latestDay == null) {
            fetchGankHistory(true)  .subscribe(calendar -> loadGanks(calendar, forceUpdate));
            return;
        }
        loadGanks(latestDay, forceUpdate);
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
