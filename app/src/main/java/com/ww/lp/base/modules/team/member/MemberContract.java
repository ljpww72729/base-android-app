package com.ww.lp.base.modules.team.member;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.team.TeamMember;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MemberContract {
    interface View extends BaseView<MemberContract.Presenter> {
        void removeResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void removeMember(String teamId, TeamMember member);
    }
}
