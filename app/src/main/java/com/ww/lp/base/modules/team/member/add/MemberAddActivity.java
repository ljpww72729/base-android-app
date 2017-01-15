package com.ww.lp.base.modules.team.member.add;

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

public class MemberAddActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_act, true, true, false);
        if (getIntent().getExtras().getParcelable("member") != null) {
            setTitle("修改成员信息");
        } else {
            setTitle("添加成员");
        }

        MemberAddFragment memberAddFragment = (MemberAddFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (memberAddFragment == null) {
            memberAddFragment = MemberAddFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    memberAddFragment, R.id.contentFrame);
        }

        // Create the presenter
        new MemberAddPresenter(TAG, ServerImp.getInstance(getApplicationContext()), memberAddFragment, SchedulerProvider.getInstance());
    }

}
