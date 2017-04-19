/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.junnanhao.gank.ganks;

import android.support.annotation.IdRes;

import com.google.common.collect.ImmutableList;
import com.junnanhao.gank.R;

/**
 * Used with the filter spinner in the tasks list.
 */
public class GankFilterType {
    final String name;
    final int resId;
    final int menuId;

    private GankFilterType(String name, int resId, int menuId) {
        this.name = name;
        this.resId = resId;
        this.menuId = menuId;
    }

    static final GankFilterType DAILY = new GankFilterType("Daily", R.drawable.ic_daily, R.id.nav_daily);
    static final GankFilterType ANDROID = new GankFilterType("Android", R.drawable.ic_android, R.id.nav_android);
    static final GankFilterType IOS = new GankFilterType("iOS", R.drawable.ic_ios, R.id.nav_ios);
    static final GankFilterType BONUS = new GankFilterType("福利", R.drawable.ic_bonus, R.id.nav_bonus);
    static final GankFilterType VIDEO = new GankFilterType("休息视频", R.drawable.ic_video, R.id.nav_video);
    static final GankFilterType WEB = new GankFilterType("前端", R.drawable.ic_web, R.id.nav_web);
    static final GankFilterType RESOURCE = new GankFilterType("拓展资源", R.drawable.ic_resource, R.id.nav_resources);

//    private static Map<Integer, GankFilterType> map = ImmutableMap.<Integer, GankFilterType>builder()
//            .put(R.id.nav_daily, DAILY)
//            .put(R.id.nav_android, ANDROID)
//            .put(R.id.nav_ios, IOS)
//            .put(R.id.nav_bonus, BONUS)
//            .put(R.id.nav_video, VIDEO)
//            .put(R.id.nav_wen, WEB)
//            .put(R.id.nav_resources, RESOURCE)
//            .build();

    private static ImmutableList<GankFilterType> list = ImmutableList.of(DAILY, ANDROID, IOS, WEB, RESOURCE, BONUS, VIDEO);


    public static GankFilterType of(@IdRes int menuId, GankFilterType defaultValue) {
        for (GankFilterType filterType : list) {
            if (filterType.menuId == menuId) {
                return filterType;
            }
        }
        return defaultValue;
    }

    public static GankFilterType of(String name, GankFilterType defaultValue) {
        for (GankFilterType filterType : list) {
            if (filterType.name == null) {
                return filterType;
            }
        }
        return defaultValue;
    }

}
