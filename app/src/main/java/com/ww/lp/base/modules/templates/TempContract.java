package com.ww.lp.base.modules.templates;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.entity.TempInfo;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TempContract {
    interface View extends BaseView<TempContract.Presenter> {
        void showInfo(String info);
    }

    interface Presenter extends BasePresenter {
        void testClick(TempInfo temp_info);
    }
}
