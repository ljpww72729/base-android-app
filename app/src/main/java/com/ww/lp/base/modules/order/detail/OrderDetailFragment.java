package com.ww.lp.base.modules.order.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.synnapps.carouselview.ViewListener;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.components.alipay.BizContent;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.data.CarouselInfo;
import com.ww.lp.base.data.ProjectDetail;
import com.ww.lp.base.data.ProjectInfo;
import com.ww.lp.base.databinding.OrderDetailFragBinding;
import com.ww.lp.base.modules.webview.NormalWVActvity;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderDetailFragment extends BaseFragment implements OrderDetailContract.View {

    private OrderDetailFragBinding binding;
    private OrderDetailContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ProjectInfo> mRVData = new ArrayList<>();
    ;
    private LPRecyclerViewAdapter<ProjectInfo> lpRecyclerViewAdapter;

    public static OrderDetailFragment newInstance(String projectId) {

        Bundle bundle = new Bundle();
        bundle.putString("projectId", projectId);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.order_detail_frag, false);
        binding = OrderDetailFragBinding.bind(root);
        mPresenter.loadData(getArguments().getString(OrderDetailActivity.PROJECT_ID));
        binding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BizContent bizContent = new BizContent(binding.getProjectDetail().getProjectInfo().getDescribe(),
                        binding.getProjectDetail().getProjectInfo().getTitle(),
                        binding.getProjectDetail().getProjectInfo().getProjectId(),
                        null,binding.getProjectDetail().getProjectInfo().getPrice(),
                        binding.getProjectDetail().getProjectInfo().getProjectId());
                mPresenter.pay(getActivity(),bizContent);
            }
        });
        return root;
    }

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void updateCarouselView(final ArrayList<CarouselInfo> carouselList) {
        ViewListener viewListener = new ViewListener() {
            @Override
            public View setViewForPosition(final int position) {
                View carouselView = LayoutInflater.from(getActivity()).inflate(R.layout.carousel_view, null);
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) carouselView.findViewById(R.id.carousel_img);
                simpleDraweeView.setImageURI(carouselList.get(position).getImg());
                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), NormalWVActvity.class);
                        intent.putExtra(NormalWVActvity.LOADURL, carouselList.get(position).getUrl());
                        startActivity(intent);
                    }
                });

                return carouselView;
            }
        };
        binding.homeCarouselView.setViewListener(viewListener);
        //setPageCount方法的调用需要放到setViewListener之后
        binding.homeCarouselView.setPageCount(carouselList.size());

    }

    @Override
    public void showDetail(ProjectDetail projectDetail) {
        binding.setProjectDetail(projectDetail);
        updateCarouselView(projectDetail.getCarouselInfo());
    }
}
