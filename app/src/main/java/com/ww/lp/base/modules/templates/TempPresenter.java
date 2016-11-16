package com.ww.lp.base.modules.templates;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.ww.lp.base.entity.TempInfo;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.schedulers.BaseSchedulerProvider;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TempPresenter implements TempContract.Presenter {

    @NonNull
    private String requestTag;

    @NonNull
    private final ServerImp mServerImp;
    @NonNull
    private final TempContract.View mContractView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public TempPresenter(@NonNull String requestTag, @NonNull ServerImp serverImp,
                          @NonNull TempContract.View contractView,
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
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void testClick(TempInfo temp_info) {
        Logger.d("testClick: success. Info = " + temp_info.getInfo());
        mContractView.showInfo(temp_info.getInfo());
    }
}
