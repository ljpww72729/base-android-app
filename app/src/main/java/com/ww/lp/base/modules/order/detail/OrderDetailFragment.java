package com.ww.lp.base.modules.order.detail;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.synnapps.carouselview.ViewListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.components.alipay.BizContent;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.data.CarouselInfo;
import com.ww.lp.base.data.ProjectDetail;
import com.ww.lp.base.data.ProjectInfo;
import com.ww.lp.base.databinding.OrderDetailFragBinding;
import com.ww.lp.base.modules.order.post.PostActivity;

import java.util.ArrayList;

import rx.functions.Action1;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;
import static com.ww.lp.base.modules.order.detail.OrderDetailActivity.PROJECT_ID;
import static com.ww.lp.base.modules.order.detail.OrderDetailActivity.SHOW_PAY;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderDetailFragment extends BaseFragment implements OrderDetailContract.View {

    private OrderDetailFragBinding binding;
    private OrderDetailContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ProjectInfo> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<ProjectInfo> lpRecyclerViewAdapter;
    RxPermissions rxPermissions;

    public static OrderDetailFragment newInstance(Intent intent) {

        Bundle bundle = new Bundle();
        bundle.putString("projectId", intent.getStringExtra(PROJECT_ID));
        bundle.putBoolean("showPay", intent.getBooleanExtra(SHOW_PAY, true));
        bundle.putBoolean("isMe", intent.getBooleanExtra("isMe", false));
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData(getArguments().getString(PROJECT_ID));
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rxPermissions = new RxPermissions(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.order_detail_frag, false);
        binding = OrderDetailFragBinding.bind(root);
        if (getArguments().getBoolean("isMe")){
            setHasOptionsMenu(true);
        }
        if (!getArguments().getBoolean("showPay", true)) {
            binding.payLayout.setVisibility(View.GONE);
        }
        mPresenter.loadData(getArguments().getString(PROJECT_ID));
        binding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BizContent bizContent = new BizContent(binding.getProjectDetail().getProjectInfo().getDescribe(),
                        binding.getProjectDetail().getProjectInfo().getTitle(),
                        binding.getProjectDetail().getProjectInfo().getProjectId(),
                        null, binding.getProjectDetail().getProjectInfo().getPrice(),
                        binding.getProjectDetail().getProjectInfo().getProjectId());
                mPresenter.pay(getActivity(), bizContent);
            }
        });
        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) { // Always true pre-M
                            // I can control the camera now
                            String number = binding.phone.getText().toString();
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + number));
                            startActivity(intent);
                        } else {
                            // Oups permission denied
                        }
                    }
                });
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
//                simpleDraweeView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), NormalWVActvity.class);
//                        intent.putExtra(NormalWVActvity.LOADURL, carouselList.get(position).getUrl());
//                        startActivity(intent);
//                    }
//                });

                return carouselView;
            }
        };
        binding.homeCarouselView.setViewListener(viewListener);
        //setPageCount方法的调用需要放到setViewListener之后
        binding.homeCarouselView.setPageCount(carouselList.size());

    }

    @Override
    public void showDetail(ProjectDetail projectDetail) {
        projectDetail.getProjectInfo().setProjectId(getArguments().getString(PROJECT_ID));
        binding.setProjectDetail(projectDetail);
        updateCarouselView(projectDetail.getCarouselInfo());
    }

    @Override
    public void deleteSuccess() {
        ((BaseActivity)getActivity()).removeProgressDialogLP();
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.project_operate, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modify:
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("projectDetail", binding.getProjectDetail());
                startActivity(intent);
                return true;
            case R.id.delete:
                ((BaseActivity)getActivity()).showProgressDialogLP("正在删除...");
                mPresenter.deleteProject(binding.getProjectDetail().getProjectInfo().getProjectId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
