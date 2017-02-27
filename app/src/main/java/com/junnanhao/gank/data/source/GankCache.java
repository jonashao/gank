package com.junnanhao.gank.data.source;

import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.data.models.Response;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by Jonas on 2017/2/23.
 * Gank Cache Provider
 */
@SuppressWarnings("unused")
public interface GankCache {
    //这里设置缓存失效时间为2分钟。
    @LifeCache(duration = 2, timeUnit = TimeUnit.HOURS)
    Observable<Reply<Response<Map<String, List<Gank>>>>>
    getGanks(Observable<Response<Map<String, List<Gank>>>> oGanks, EvictProvider evictProvider);


    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Reply<Response<List<String>>>>
    getHistory(Observable<Response<List<String>>> oGanks, EvictProvider evictProvider);

}
