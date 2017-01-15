package com.ww.lp.base.modules.order.list;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderListActivity extends BaseActivity {

    public static final String ORDER_FLAG = "order_flag";
    public static final String RELEASE = "release";
    public static final String ACCEPT = "accept";
    public static final String ISONLYQUERYMYPUBLIS = "isOnlyQueryMyPublis";
    public static final String PERSONAL = "1";
    public static final String ALL = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);
        if (getIntent() != null) {
            setTitle(getIntent().getStringExtra(Constants.TITLE));
        }

        OrderListFragment orderListFragment = (OrderListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (orderListFragment == null) {
            orderListFragment = OrderListFragment.newInstance(getIntent().getStringExtra(ORDER_FLAG), getIntent().getStringExtra(OrderListActivity.ISONLYQUERYMYPUBLIS));

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    orderListFragment, R.id.contentFrame);
        }

        // Create the presenter
        new OrderListPresenter(TAG, ServerImp.getInstance(getApplicationContext()), orderListFragment, SchedulerProvider.getInstance());
    }

}
