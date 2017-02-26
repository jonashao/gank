package com.junnanhao.gank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.junnanhao.gank.data.gson.AutoValueGsonFactory;
import com.junnanhao.gank.data.models.Gank;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GsonUnitTest {
    @Test
    public void autoValueGsonFactory_parseSuccess() throws Exception {
        String json = " {\n" +
                "                \"_id\": \"58ab81c7421aa93d3d15aa3e\",\n" +
                "                \"createdAt\": \"2017-02-21T07:54:47.295Z\",\n" +
                "                \"desc\": \"\\u4e00\\u6b3e\\u6f02\\u4eae\\u7684\\u6bcf\\u5468\\u65e5\\u5386\\u7ec4\\u4ef6\\u3002\",\n" +
                "                \"images\": [\n" +
                "                    \"http://img.gank.io/a708c59d-7949-4ac0-855d-c46a2d1825b2\"\n" +
                "                ],\n" +
                "                \"publishedAt\": \"2017-02-21T11:14:09.564Z\",\n" +
                "                \"source\": \"chrome\",\n" +
                "                \"type\": \"Android\",\n" +
                "                \"url\": \"https://github.com/nomanr/weekcalendar\",\n" +
                "                \"used\": true,\n" +
                "                \"who\": \"\\u4ee3\\u7801\\u5bb6\"\n" +
                "            }";

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(AutoValueGsonFactory.create()).create();
        Gank gank = gson.fromJson(json, Gank.class);
        assertNotNull(gank);
        assertEquals(gank.id(),"58ab81c7421aa93d3d15aa3e");
        assertEquals(gank.type(),"Android");
        assertEquals(4, 2 + 2);
    }

}