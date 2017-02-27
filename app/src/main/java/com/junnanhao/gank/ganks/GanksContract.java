package com.junnanhao.gank.ganks;

import com.junnanhao.gank.base.BasePresenter;
import com.junnanhao.gank.base.BaseView;
import com.junnanhao.gank.data.models.Gank;

import java.util.List;
import java.util.Map;

/**
 * Created by Jonas on 2017/2/22.
 * This specifies the contract between the view and the presenter.
 */

interface GanksContract {


    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showGanks(Map<String,List<Gank>> ganks);

        /**
         * show an interface to submit gank
         */
        void showSubmitGank();

        void showSuccessfullySubmitMessage();

        void showNetworkError();

        void showLoadingGanksError();

        void showNoGank();

        void showCalendarMenu();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);

        void loadGanks(boolean forceUpdate);

        void submitGank();

        void setFiltering(GankFilterType requestType);

        GankFilterType getFiltering();

    }
}
