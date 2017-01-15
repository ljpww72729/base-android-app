package com.ww.lp.base.modules.order.comment;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class CommentContract {
    interface View extends BaseView<CommentContract.Presenter> {
        void scoreResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void score(float score, String projectId);
    }
}
