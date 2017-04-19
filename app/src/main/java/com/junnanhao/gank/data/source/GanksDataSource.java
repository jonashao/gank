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

package com.junnanhao.gank.data.source;


import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.data.models.Response;
import com.junnanhao.gank.ganks.GankFilterType;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.rx_cache2.Reply;

/**
 * Main entry point for accessing Ganks data.
 */
@SuppressWarnings("unused")
public interface GanksDataSource {


    Observable<Reply<Response<List<String>>>> getHistory(boolean evict);

    Observable<Reply<Response<Map<String, List<Gank>>>>> getGanks(Calendar today, boolean evict);

    Observable<Reply<Response<Map<String, List<Gank>>>>> getGanks(int year, int month, int day, boolean evict);

    Observable<Reply<List<Gank>>> getGanks(String type, int numPerPage, int pageNum, boolean evict);

}
