package com.ww.lp.base.modules.team.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.R;
import com.ww.lp.base.data.TeamDetail;
import com.ww.lp.base.network.ServerImp;
import com.ww.lp.base.utils.ActivityUtils;
import com.ww.lp.base.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TeamDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);

        setTitle("成员列表");
        ArrayList<TeamDetail> arrayList = new ArrayList<>();
        arrayList = getIntent().getParcelableArrayListExtra("team_detail");

        TeamDetailFragment teamListFragment = (TeamDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (teamListFragment == null) {
            teamListFragment = TeamDetailFragment.newInstance(arrayList);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    teamListFragment, R.id.contentFrame);
        }

        // Create the presenter
        new TeamDetailPresenter(TAG, ServerImp.getInstance(getApplicationContext()), teamListFragment, SchedulerProvider.getInstance());
    }

}
