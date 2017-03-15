package com.ww.lp.base.modules.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.synnapps.carouselview.ViewListener;
import com.ww.lp.base.BR;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.components.rvrl.LPRefreshLoadListener;
import com.ww.lp.base.components.rvrl.SingleItemClickListener;
import com.ww.lp.base.data.ads.CarouselInfo;
import com.ww.lp.base.data.project.ProjectInfo;
import com.ww.lp.base.databinding.HomeFragBinding;
import com.ww.lp.base.modules.login.LoginActivity;
import com.ww.lp.base.modules.order.detail.OrderDetailActivity;
import com.ww.lp.base.modules.team.developer.DeveloperActivity;
import com.ww.lp.base.modules.team.list.TeamListActivity;
import com.ww.lp.base.modules.webview.NormalWVActvity;
import com.ww.lp.base.utils.SPUtils;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeFragBinding binding;
    private HomeContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ProjectInfo> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<ProjectInfo> lpRecyclerViewAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter == null){
            getActivity().finish();
        }else {
            mPresenter.subscribe();
            lpRecyclerViewAdapter.setPageCurrentNum(lpRecyclerViewAdapter.getPageStartNum());
            mPresenter.loadProjectList(lpRecyclerViewAdapter.getPageStartNum());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null){
            mPresenter.unsubscribe();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.home_frag, false);
        binding = HomeFragBinding.bind(root);
        binding.lpRv.setVisibility(View.GONE);
        binding.lpRv.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.lpRv.setLayoutManager(mLayoutManager);

        // Set up progress indicator
        binding.lpScsr.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.primary_dark),
                ContextCompat.getColor(getActivity(), R.color.accent),
                ContextCompat.getColor(getActivity(), R.color.primary)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        binding.lpScsr.setScrollUpChild(binding.lpRv);
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.project_info_item, BR.lp_rv_item, binding.lpScsr, binding.lpRv);
        lpRecyclerViewAdapter.setPageStartNum(1);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (TextUtils.isEmpty((String) SPUtils.get(CustomApplication.self(), SPUtils.TOKEN, ""))){
                    //登录后再操
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra(OrderDetailActivity.PROJECT_ID, mRVData.get(position).getProjectId());
                    intent.putExtra(OrderDetailActivity.IS_PERSONAL, false);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        lpRecyclerViewAdapter.setOnLoadMoreListener(new LPRefreshLoadListener.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                lpRecyclerViewAdapter.showLoadingMore(true);
                mPresenter.loadProjectList(lpRecyclerViewAdapter.getPageCurrentNum() + 1);

            }
        });
        lpRecyclerViewAdapter.setOnRefreshListener(new LPRefreshLoadListener.onRefreshListener() {
            @Override
            public void onRefresh() {
                //数据刷新操作
                mPresenter.loadProjectList(lpRecyclerViewAdapter.getPageStartNum());
            }
        });
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);

        binding.team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeamListActivity.class);
                startActivity(intent);

            }
        });
        binding.developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeveloperActivity.class);
                startActivity(intent);

            }
        });

        return root;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
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
                        if (!carouselList.get(position).getUrl().startsWith("empty") && carouselList.get(position).getUrl().startsWith("http")) {
                            Intent intent = new Intent(getActivity(), NormalWVActvity.class);
                            intent.putExtra(NormalWVActvity.LOADURL, carouselList.get(position).getUrl());
                            startActivity(intent);
                        }
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
    public void updateProjectList(boolean result, ArrayList<ProjectInfo> arrayList, int pageCount) {
        // TODO: 16/11/26 数据是否这样更新有待优化
        binding.lpRv.setVisibility(View.VISIBLE);
        if (result) {
            lpRecyclerViewAdapter.setPageCount(pageCount);
            // TODO: 16/11/26 数据是否这样更新有待优化
            if (lpRecyclerViewAdapter.isLoadingMore()) {
                lpRecyclerViewAdapter.showLoadingMore(false);
            } else {
                mRVData.clear();
                if (binding.lpScsr.isRefreshing()) {
                    lpRecyclerViewAdapter.setPageCurrentNum(lpRecyclerViewAdapter.getPageStartNum());
                }
            }
            mRVData.addAll(arrayList);
            lpRecyclerViewAdapter.notifyDataSetChanged();
        }
        if (lpRecyclerViewAdapter.isLoadingMore()) {
            lpRecyclerViewAdapter.loadMoreSuccess();
            lpRecyclerViewAdapter.setLoadingMore(false);
        }
        if (binding.lpScsr.isRefreshing()) {
            binding.lpScsr.setRefreshing(false);
        }

    }
}
