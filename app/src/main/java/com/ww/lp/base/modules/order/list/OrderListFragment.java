package com.ww.lp.base.modules.order.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BR;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.components.rvrl.LPRefreshLoadListener;
import com.ww.lp.base.components.rvrl.SingleItemClickListener;
import com.ww.lp.base.data.project.ProjectInfo;
import com.ww.lp.base.databinding.OrderListBinding;
import com.ww.lp.base.modules.order.detail.OrderDetailActivity;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class OrderListFragment extends BaseFragment implements OrderListContract.View {

    private LinearLayoutManager mLayoutManager;
    private ArrayList<ProjectInfo> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<ProjectInfo> lpRecyclerViewAdapter;

    public static OrderListFragment newInstance(String flag, String isOnlyQueryMyPublis) {

        Bundle args = new Bundle();
        args.putString(OrderListActivity.ORDER_FLAG, flag);
        args.putString(OrderListActivity.ISONLYQUERYMYPUBLIS, isOnlyQueryMyPublis);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private OrderListBinding binding;
    private OrderListContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        lpRecyclerViewAdapter.setPageCurrentNum(lpRecyclerViewAdapter.getPageStartNum());
        mPresenter.loadProjectList(getArguments().getString(OrderListActivity.ORDER_FLAG), getArguments().getString(OrderListActivity.ISONLYQUERYMYPUBLIS), lpRecyclerViewAdapter.getPageStartNum());
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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.order_list, false);
        binding = OrderListBinding.bind(root);
        binding.lpRv.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
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
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra(OrderDetailActivity.PROJECT_ID, mRVData.get(position).getProjectId());
                intent.putExtra(OrderDetailActivity.SHOW_PAY, false);
                intent.putExtra(OrderListActivity.ORDER_FLAG, getArguments().getString(OrderListActivity.ORDER_FLAG));
                intent.putExtra(OrderDetailActivity.IS_PERSONAL, true);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        lpRecyclerViewAdapter.setOnLoadMoreListener(new LPRefreshLoadListener.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                lpRecyclerViewAdapter.showLoadingMore(true);
                mPresenter.loadProjectList(getArguments().getString(OrderListActivity.ORDER_FLAG), getArguments().getString(OrderListActivity.ISONLYQUERYMYPUBLIS), lpRecyclerViewAdapter.getPageCurrentNum() + 1);

            }
        });
        lpRecyclerViewAdapter.setOnRefreshListener(new LPRefreshLoadListener.onRefreshListener() {
            @Override
            public void onRefresh() {
                //数据刷新操作
                mPresenter.loadProjectList(getArguments().getString(OrderListActivity.ORDER_FLAG), getArguments().getString(OrderListActivity.ISONLYQUERYMYPUBLIS), lpRecyclerViewAdapter.getPageStartNum());
            }
        });
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);

        return root;
    }

    @Override
    public void setPresenter(@NonNull OrderListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updateOrderList(boolean result, ArrayList<ProjectInfo> arrayList, int pageCount) {
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
