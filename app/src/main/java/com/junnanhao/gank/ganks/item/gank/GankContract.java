package com.junnanhao.gank.ganks.item.gank;

/**
 * Created by Jonas on 2017/2/25.
 */

interface GankContract {
    interface View   {
        /**
         * open browser or custom tab to show detail of gank
         */
        void showGankInBrowser();

        /**
         * show feedback after user successful mark a gank
         */
        void showGankMarked();
    }

}
