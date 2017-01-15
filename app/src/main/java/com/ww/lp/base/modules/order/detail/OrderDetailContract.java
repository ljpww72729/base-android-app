package com.ww.lp.base.modules.order.detail;

import android.app.Activity;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.project.ProjectInfo;

import java.util.Map;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderDetailContract {
    interface View extends BaseView<OrderDetailContract.Presenter> {
        void showDetail(ProjectInfo projectInfo);
        void deleteSuccess(boolean result);
        void acceptProjectResult(boolean result);

        void editSuccess(boolean result);
        void signResult(boolean result, String signStr);
        void aliPay(boolean result, Map<String, String> payMap);
        void payResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void getSign(String projectId);
        void pay(Activity activity, String signStr);
        void deleteProject(String projectId);
        void editProject(ProjectInfo projectInfo, int status);
        void loadProjectInfo(String string);
        void acceptProject(String projectId);
        void getPayResult(String payResult);
    }
}
