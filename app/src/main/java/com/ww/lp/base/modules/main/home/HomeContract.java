package com.ww.lp.base.modules.main.home;

import com.ww.lp.base.BasePresenter;
import com.ww.lp.base.BaseView;
import com.ww.lp.base.data.CarouselInfo;
import com.ww.lp.base.data.ProjectInfo;

import java.util.ArrayList;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class HomeContract {
    interface View extends BaseView<HomeContract.Presenter> {
        void updateCarouselView(ArrayList<CarouselInfo> carouselList);
        void updateProjectList(ArrayList<ProjectInfo> arrayList);
    }

    interface Presenter extends BasePresenter {

    }
}
