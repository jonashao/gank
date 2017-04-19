package com.junnanhao.gank.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.junnanhao.gank.BuildConfig;
import com.junnanhao.gank.data.gson.AutoValueGsonFactory;
import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.data.models.Response;
import com.junnanhao.gank.ganks.GankFilterType;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jonas on 2017/1/21.
 * encapsulate http requests
 */
public class GankRepository implements GanksDataSource {
    private static final long DEFAULT_TIMEOUT = 5;
    private static final long DEFAULT_READ_WRITE_TIMEOUT = 10;

    private static final String BASE_URL = "http://gank.io/api/";

    private final GankService gankService;
    private final GankCache gankCacheProvider;

    private static GankRepository INSTANCE;


    public synchronized static GankRepository getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GankRepository(context.getCacheDir());
        }
        return INSTANCE;
    }

    private GankRepository(File cacheDir) {

        GsonConverterFactory converterFactory = GsonConverterFactory.create(
                new GsonBuilder()
                        .registerTypeAdapterFactory(AutoValueGsonFactory.create())
                        .create());

        gankService = new Retrofit.Builder()
                .client(setupOkHttpClient())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL).build().create(GankService.class);

        gankCacheProvider = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(GankCache.class);
    }

    @NonNull
    private OkHttpClient setupOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        //设置超时
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_WRITE_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }

    @Override
    public Observable<Reply<Response<Map<String, List<Gank>>>>> getGanks(int year, int month, int day, boolean evict) {
        return gankCacheProvider.getGanks(gankService.getGanks(year, month, day), new EvictProvider(evict));
    }


    @Override
    public Observable<Reply<Response<Map<String, List<Gank>>>>> getGanks(Calendar today, boolean evict) {
        return getGanks(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH), evict);
    }


    @Override
    public Observable<Reply<List<Gank>>> getGanks(String type, int numPerPage, int pageNum, boolean evict) {
        Observable<List<Gank>> oListGank = gankService.getGanks(type, numPerPage, pageNum)
                .map(listResponse -> listResponse.results());
        return gankCacheProvider.getGanksOfType(oListGank, new EvictProvider(evict));
    }

    @Override
    public Observable<Reply<Response<List<String>>>> getHistory(boolean evict) {
        return gankCacheProvider.getHistory(gankService.getHistory(), new EvictProvider(evict));
    }

}
