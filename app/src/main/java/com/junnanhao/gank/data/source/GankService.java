package com.junnanhao.gank.data.source;

import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.data.models.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by Jonas on 2017/1/21.
 * Retrofit interface, requesting data from server.
 */

public interface GankService {
    /**
     * 每日干货
     */
    @GET("day/{year}/{month}/{day}")
    Observable<Response<Map<String, List<Gank>>>> getGanks(@Path("year") int year, @Path("month") int month, @Path("day") int day);


    @GET("day/history")
    Observable<Response<List<String>>> getHistory();

    /**
     * 技术文章列表
     */
    @GET("data/{tech}/{num}/{page}")
    Observable<Response<List<Gank>>> getGanks(@Path("tech") String tech, @Path("num") int num, @Path("page") int page);

    /**
     * 妹纸列表
     */
    @GET("data/福利/{num}/{page}")
    Observable<Response<List<Gank>>> getGirlList(@Path("num") int num, @Path("page") int page);

    /**
     * 随机妹纸图
     */
    @GET("random/data/福利/{num}")
    Observable<Response<List<Gank>>> getRandomGirl(@Path("num") int num);

    /**
     * 搜索
     */
    @GET("search/query/listview/category/{type}/count/{count}/page/{page}")
    Observable<Response<List<Gank>>> getSearchList(@Path("type") String type, @Path("count") int num, @Path("page") int page);


}
