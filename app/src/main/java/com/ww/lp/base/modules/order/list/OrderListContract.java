package com.ww.lp.base.modules.order.list;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.ProjectInfo;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderListContract {
    interface View extends BaseView<OrderListContract.Presenter> {
        void updateOrdertList(ArrayList<ProjectInfo> arrayList);
    }

    interface Presenter extends BasePresenter {
        void loadOrderList(String flag);
    }
}
