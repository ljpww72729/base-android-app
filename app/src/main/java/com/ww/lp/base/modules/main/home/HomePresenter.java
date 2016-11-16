package com.ww.lp.base.modules.main.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.ww.lp.base.entity.CarouselInfo;
import com.ww.lp.base.entity.CarouselList;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.network.ServerInterface;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final HomeContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public HomePresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                         @NonNull HomeContract.View contractView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        this.requestTag = requestTag;
        mServerImp = checkNotNull(serverImp, "serverImp cannot be null!");
        mContractView = checkNotNull(contractView, "loginView cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //此处为页面打开后开始加载数据时调用的方法
        loadCarouselImgList();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    /**
     * 请求轮播图列表
     */
    private void loadCarouselImgList() {
        ArrayList<CarouselInfo> arrayList = new ArrayList<CarouselInfo>();
        arrayList.add(new CarouselInfo("1", "http://h.hiphotos.baidu.com/zhidao/pic/item/aec379310a55b3196497de3140a98226cffc1703.jpg", "http://www.baidu.com"));
        arrayList.add(new CarouselInfo("2", "http://img1.imgtn.bdimg.com/it/u=2944150852,3575176219&fm=21&gp=0.jpg", "http://www.baidu.com"));

        mContractView.updateCarouselView(arrayList);
        Map<String, String> params = new HashMap<>();
        params.put("type", "test110");
        params.put("postid", "dd");
        Subscription subscription = mServerImp
                .common(requestTag, Request.Method.GET, ServerInterface.carousel, params, CarouselList.class)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<CarouselList>() {
                    @Override
                    public void onCompleted() {
                        //mTaskDetailView.setLoadingIndicator(false);
                        Log.d("ddd", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ddd", "onError: ");
                    }

                    @Override
                    public void onNext(CarouselList carouselList) {
                        Log.d("ddd", "onNext: ");
                        ArrayList<CarouselInfo> arrayList = new ArrayList<CarouselInfo>();
                        arrayList.add(new CarouselInfo("1", "http://h.hiphotos.baidu.com/zhidao/pic/item/aec379310a55b3196497de3140a98226cffc1703.jpg", "http://www.baidu.com"));
                        arrayList.add(new CarouselInfo("2", "http://img1.imgtn.bdimg.com/it/u=2944150852,3575176219&fm=21&gp=0.jpg", "http://www.baidu.com"));

                        mContractView.updateCarouselView(arrayList);
                    }
                });
        mSubscriptions.add(subscription);

    }


}
