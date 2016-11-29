package com.ww.lp.base.modules.order.post;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.entity.ProjectPostInfo;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class PostContract {
    interface View extends BaseView<PostContract.Presenter> {
       void uploadFileSuccess(ArrayList<String> imgList);
    }

    interface Presenter extends BasePresenter {
        //发布需求
        void post(ProjectPostInfo projectPostInfo);
        void uploadFile(ArrayList<String> arrayList);
    }
}
