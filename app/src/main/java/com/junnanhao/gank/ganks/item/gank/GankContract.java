package com.junnanhao.gank.ganks.item.gank;

import android.support.annotation.NonNull;

import com.junnanhao.gank.base.BasePresenter;
import com.junnanhao.gank.base.BaseView;
import com.junnanhao.gank.data.models.Gank;

/**
 * Created by Jonas on 2017/2/25.
 */

interface GankContract {
    interface View extends BaseView<Presenter> {
        /**
         * open browser or custom tab to show detail of gank
         */
        void showGankInBrowser();

        /**
         * show feedback after user successful mark a gank
         */
        void showGankMarked();
    }

    interface Presenter extends BasePresenter {
        void openGankDetails(@NonNull Gank requestedGank);

        void markGank(@NonNull Gank markedGank);
    }
}
