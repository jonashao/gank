package com.junnanhao.gank.ganks;

import android.util.Log;

import com.airbnb.epoxy.EpoxyAdapter;
import com.junnanhao.gank.R;
import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.ganks.item.gank.GankEpoxyModel;
import com.junnanhao.gank.ganks.item.header.HeaderModel_;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by Jonas on 2017/2/23.
 * Epoxy adapter
 */

public class GanksAdapter extends EpoxyAdapter {

    public GanksAdapter() {
        enableDiffing();
    }

    private static final Map<String, Integer> drawables = new HashMap<String, Integer>() {{
        put("Android", R.drawable.ic_android);
        put("App", R.drawable.ic_app);
        put("iOS", R.drawable.ic_ios);
        put("休息视频", R.drawable.ic_video);
        put("拓展资源", R.drawable.ic_web);
        put("瞎推荐", R.drawable.ic_hint);
        put("福利", R.drawable.ic_bonus);
    }};


    public void setData(Map<String, List<Gank>> ganks) {
        models.clear();
        ganks.keySet().forEach(s -> {
            models.add(new HeaderModel_().icon(drawableOf(s)).text(s));
            ganks.get(s).forEach(gank -> {
                Timber.d("gank: %s", gank);
                models.add(new GankEpoxyModel(gank));
            });
        });
        notifyModelsChanged();
    }

    private int drawableOf(String type) {
        return drawables.getOrDefault(type, R.drawable.ic_bar_chart);
    }

}
