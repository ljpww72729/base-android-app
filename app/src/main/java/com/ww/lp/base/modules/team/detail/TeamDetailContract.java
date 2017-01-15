package com.ww.lp.base.modules.team.detail;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.team.TeamInfo;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TeamDetailContract {
    interface View extends BaseView<TeamDetailContract.Presenter> {
        void loadTeamResult(boolean result, ArrayList<TeamInfo> arrayList, int pageCount);
    }

    interface Presenter extends BasePresenter {
        void loadTeamList(String teamId, String isOnlyQueryMyOwn, int pageNum);
    }
}
