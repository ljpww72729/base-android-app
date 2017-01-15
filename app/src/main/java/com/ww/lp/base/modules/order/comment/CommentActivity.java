package com.ww.lp.base.modules.order.comment;

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

public class CommentActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("评分");

        CommentFragment tempFragment = (CommentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (tempFragment == null) {
            tempFragment = CommentFragment.newInstance(getIntent());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tempFragment, R.id.contentFrame);
        }

        // Create the presenter
        new CommentPresenter(TAG, ServerImp.getInstance(getApplicationContext()), tempFragment, SchedulerProvider.getInstance());
    }

}
