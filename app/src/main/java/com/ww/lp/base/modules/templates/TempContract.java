package com.ww.lp.base.modules.templates;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TempContract {
    interface View extends BaseView<TempContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
