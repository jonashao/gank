package com.junnanhao.gank;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.junnanhao.gank.data.models.Gank;
import com.junnanhao.gank.data.models.Response;
import com.junnanhao.gank.data.source.GankRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.rx_cache2.Reply;
import io.rx_cache2.Source;
import timber.log.Timber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class GankRepositoryTest {

    public static final String TEST_STRING = "This is a string";
    public static final long TEST_LONG = 12345678L;
    private GankRepository gankRepository;

    @Before
    public void setup() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        gankRepository = GankRepository.getInstance(
                InstrumentationRegistry.getTargetContext());
    }


    @Test
    public void testPreConditions() {
        assertNotNull(gankRepository);
    }


    @Test
    public void getGank_retrieveApiGanks() {
        Observable<Reply<Response<Map<String, List<Gank>>>>>
                ganks = gankRepository.getGanks(2017, 2, 20, true);
        assertNotNull(ganks);
        ganks.doOnNext(responseReply -> assertEquals(responseReply.getSource(), Source.CLOUD))
                .map(responseReply -> responseReply.getData().results())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringListMap -> {
                    assertTrue(stringListMap.containsKey("Android"));
                    assertEquals(stringListMap.get("Android").size(), 3);
                    stringListMap.get("Android")
                            .forEach(gank -> Timber.d("gank: %s", gank.toString()));
                }, Timber::e);
    }

    @Test
    public void getGank_retrieveCacheGanks() {
        Observable<Reply<Response<Map<String, List<Gank>>>>>
                ganks = gankRepository.getGanks(2017, 2, 20, false);
        assertNotNull(ganks);
        ganks.doOnNext(responseReply -> assertEquals(responseReply.getSource(), Source.MEMORY))
                .map(responseReply -> responseReply.getData().results())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringListMap -> {
                    assertTrue(stringListMap.containsKey("Android"));
                    assertEquals(stringListMap.get("Android").size(), 3);
                    stringListMap.get("Android")
                            .forEach(gank -> Timber.d("gank: %s", gank.toString()));
                }, Timber::e);
    }

}
