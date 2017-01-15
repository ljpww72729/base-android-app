package com.ww.lp.base.modules.team.member.add;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.team.TeamMember;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MemberAddContract {
    interface View extends BaseView<MemberAddContract.Presenter> {
        void uploadFileResult(boolean result, String uploadImgUrl);
        void completeResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void uploadFile(ArrayList<String> arrayList);
        void complete(TeamMember teamMember, boolean isAdd);
    }
}
