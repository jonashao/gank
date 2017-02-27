package com.junnanhao.gank.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.junnanhao.gank.BuildConfig;
import com.junnanhao.gank.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element versionElement = new Element();
        versionElement.setTitle(getString(R.string.versionName).concat(BuildConfig.VERSION_NAME));

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.about_description_text))
                .setImage(R.mipmap.ic_launcher)
                .addItem(versionElement)
                .addGroup(getString(R.string.about_contact_us))
                .addEmail("junnanhao@gmail.com")
                .addGitHub("jonashao/gank")
                .create();
        setContentView(aboutPage);
    }
}
