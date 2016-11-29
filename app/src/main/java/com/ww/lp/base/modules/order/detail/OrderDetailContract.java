package com.ww.lp.base.modules.order.detail;

import android.app.Activity;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.components.alipay.BizContent;
import com.ww.lp.base.data.ProjectDetail;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderDetailContract {
    interface View extends BaseView<OrderDetailContract.Presenter> {
        void showDetail(ProjectDetail projectDetail);
    }

    interface Presenter extends BasePresenter {
        void loadData(String projectId);

        void pay(Activity activity, BizContent bizContent);
    }
}
