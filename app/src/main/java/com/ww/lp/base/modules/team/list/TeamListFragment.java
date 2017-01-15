package com.ww.lp.base.modules.team.list;

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
import com.ww.lp.base.data.team.TeamInfo;
import com.ww.lp.base.databinding.TeamListFragBinding;
import com.ww.lp.base.modules.team.detail.TeamDetailActivity;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TeamListFragment extends BaseFragment implements TeamListContract.View {

    public static TeamListFragment newInstance() {

        Bundle args = new Bundle();

        TeamListFragment fragment = new TeamListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TeamListFragBinding binding;
    private TeamListContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<TeamInfo> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<TeamInfo> lpRecyclerViewAdapter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        lpRecyclerViewAdapter.setPageCurrentNum(lpRecyclerViewAdapter.getPageStartNum());
        mPresenter.loadTeamList(lpRecyclerViewAdapter.getPageStartNum());
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.team_list_frag, false);
        binding = TeamListFragBinding.bind(root);
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
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.team_list_item, BR.lp_rv_item, binding.lpScsr, binding.lpRv);
        lpRecyclerViewAdapter.setPageStartNum(1);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                intent.putExtra("teamId", mRVData.get(position).getTeamId());
                intent.putExtra("isOnlyQueryMyOwn", "0");
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
                mPresenter.loadTeamList(lpRecyclerViewAdapter.getPageCurrentNum() + 1);

            }
        });
        lpRecyclerViewAdapter.setOnRefreshListener(new LPRefreshLoadListener.onRefreshListener() {
            @Override
            public void onRefresh() {
                //数据刷新操作
                mPresenter.loadTeamList(lpRecyclerViewAdapter.getPageStartNum());
            }
        });
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);
        return root;
    }

    @Override
    public void setPresenter(@NonNull TeamListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updateTeamList(boolean result, ArrayList<TeamInfo> arrayList, int pageCount) {
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
