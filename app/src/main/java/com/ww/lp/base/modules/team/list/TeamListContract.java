package com.ww.lp.base.modules.team.list;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.TeamResult;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TeamListContract {
    interface View extends BaseView<TeamListContract.Presenter> {
        void updateTeamList(ArrayList<TeamResult> arrayList);
    }

    interface Presenter extends BasePresenter {
    }
}
