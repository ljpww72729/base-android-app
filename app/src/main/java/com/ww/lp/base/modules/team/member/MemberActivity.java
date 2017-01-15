package com.ww.lp.base.modules.team.member;

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

public class MemberActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("成员详细信息");

        MemberFragment teamListFragment = (MemberFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (teamListFragment == null) {
            teamListFragment = MemberFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    teamListFragment, R.id.contentFrame);
        }

        // Create the presenter
        new MemberPresenter(TAG, ServerImp.getInstance(getApplicationContext()), teamListFragment, SchedulerProvider.getInstance());
    }

}
