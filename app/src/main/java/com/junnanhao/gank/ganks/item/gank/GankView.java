package com.junnanhao.gank.ganks.item.gank;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;


import com.junnanhao.gank.R;
import com.junnanhao.gank.data.models.Gank;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import org.ocpsoft.prettytime.PrettyTime;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jonas on 2017/2/23.
 */

public class GankView extends CardView implements GankContract.View {
    @BindView(R.id.writer) TextView textWriter;
    @BindView(R.id.date) TextView textDate;
    @BindView(R.id.source) TextView textSource;
    @BindView(R.id.description) TextView textDescription;
    @BindView(R.id.toggle_mark) ToggleImageButton toggleMark;

    private String url;
    private PrettyTime p;
    private GankContract.Presenter mPresenter;

    public GankView(Context context) {
        super(context);
        init();
    }

    public GankView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.gank_item_content, this);
        ButterKnife.bind(this);
        p = new PrettyTime();
    }

    public void setData(Gank gank) {
        textWriter.setText(gank.who());
        textDate.setText(p.format(gank.createdAt()));
        textDescription.setText(gank.desc());
        textSource.setText(gank.source());
        url = gank.url();
    }


    @OnClick(R.id.description)
    @Override
    public void showGankInBrowser() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();

        customTabsIntent.launchUrl(getContext(), Uri.parse(url));
    }

    @Override
    public void showGankMarked() {

    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
