package com.ww.lp.base.modules.team.list;

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

public class TeamListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("团队列表");

        TeamListFragment teamListFragment = (TeamListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (teamListFragment == null) {
            teamListFragment = TeamListFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    teamListFragment, R.id.contentFrame);
        }

        // Create the presenter
        new TeamListPresenter(TAG, ServerImp.getInstance(getApplicationContext()), teamListFragment, SchedulerProvider.getInstance());
    }

}
