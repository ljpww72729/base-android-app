package com.ww.lp.base.modules.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.synnapps.carouselview.ViewListener;
import com.ww.lp.base.BR;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.components.rvrl.SingleItemClickListener;
import com.ww.lp.base.data.CarouselInfo;
import com.ww.lp.base.data.ProjectInfo;
import com.ww.lp.base.databinding.HomeFragBinding;
import com.ww.lp.base.modules.order.detail.OrderDetailActivity;
import com.ww.lp.base.modules.team.developer.DeveloperActivity;
import com.ww.lp.base.modules.team.list.TeamListActivity;
import com.ww.lp.base.modules.webview.NormalWVActvity;

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
        mPresenter.subscribe();
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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.home_frag, false);
        binding = HomeFragBinding.bind(root);
        binding.lpRv.setVisibility(View.GONE);
        binding.lpRv.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.lpRv.setLayoutManager(mLayoutManager);
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.project_info_item, BR.lp_rv_item);
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra(OrderDetailActivity.PROJECT_ID, mRVData.get(position).getProjectId());
                intent.putExtra("isMe", false);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
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
    public void updateProjectList(ArrayList<ProjectInfo> arrayList) {
        // TODO: 16/11/26 数据是否这样更新有待优化
        binding.lpRv.setVisibility(View.VISIBLE);
        mRVData.clear();
        mRVData.addAll(arrayList);
        lpRecyclerViewAdapter.notifyDataSetChanged();
    }
}
