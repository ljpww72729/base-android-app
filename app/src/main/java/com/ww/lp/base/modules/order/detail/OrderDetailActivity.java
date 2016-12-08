package com.ww.lp.base.modules.order.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderDetailActivity extends BaseActivity {

    public static final String PROJECT_ID = "projectId";
    public static final String SHOW_PAY = "showPay";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("项目详情");

        OrderDetailFragment orderDetailFragment = (OrderDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (orderDetailFragment == null) {
            orderDetailFragment = OrderDetailFragment.newInstance(getIntent());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    orderDetailFragment, R.id.contentFrame);
        }

        // Create the presenter
        new OrderDetailPresenter(TAG, ServerImp.getInstance(getApplicationContext()), orderDetailFragment, SchedulerProvider.getInstance());
    }



}
