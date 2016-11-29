package com.ww.lp.base.modules.order.post;

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

public class PostActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("需求发布");

        PostFragment postFragment = (PostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (postFragment == null) {
            postFragment = PostFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    postFragment, R.id.contentFrame);
        }

        // Create the presenter
        new PostPresenter(TAG, ServerImp.getInstance(getApplicationContext()), postFragment, SchedulerProvider.getInstance());
    }

}
