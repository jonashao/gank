package com.junnanhao.gank.ganks;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.junnanhao.gank.BuildConfig;
import com.junnanhao.gank.R;
import com.junnanhao.gank.data.source.GankRepository;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GanksActivity extends AppCompatActivity {
    private GanksContract.Presenter mPresenter;

    @BindView(R.id.ganksView) GanksView ganksView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new GanksPresenter(GankRepository.getInstance(this), ganksView);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @OnClick(R.id.fab)
    public void submit(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        GankRepository.getInstance(this).getGanks(2017, 2, 22, false)
                .filter(predicate -> {
                    Log.d("SOURCE", predicate.getSource().toString());
                    return true;
                })
                .map(responseReply -> responseReply.getData().results())
                .subscribeOn(Schedulers.io())
                .subscribe(stringListMap -> Log.d("test", stringListMap.toString()), Throwable::printStackTrace);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
