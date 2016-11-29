package com.ww.lp.base.modules.team.member;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MemberContract {
    interface View extends BaseView<MemberContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
