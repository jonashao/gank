package com.junnanhao.gank.ganks.item.gank;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.junnanhao.gank.R;
import com.junnanhao.gank.data.models.Gank;

/**
 * Created by Jonas on 2017/2/25.
 */
@EpoxyModelClass(layout = R.layout.gank_item)
public class GankEpoxyModel extends EpoxyModel<GankView> {
    @EpoxyAttribute Gank gank;

    public GankEpoxyModel(Gank gank) {
        this.gank = gank;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.gank_item;
    }

    @Override
    public void bind(GankView view) {
        view.setData(gank);
    }
}
