package com.ww.lp.base.modules.team.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BR;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.data.team.TeamInfo;
import com.ww.lp.base.data.team.TeamMember;
import com.ww.lp.base.databinding.TeamListFragBinding;
import com.ww.lp.base.modules.team.member.MemberActivity;
import com.ww.lp.base.modules.team.member.add.MemberAddActivity;
import com.ww.lp.rvrl_lib.LPRecyclerViewAdapter;
import com.ww.lp.rvrl_lib.LPRefreshLoadListener;
import com.ww.lp.rvrl_lib.SingleItemClickListener;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TeamDetailFragment extends BaseFragment implements TeamDetailContract.View {

    public static TeamDetailFragment newInstance(String flag, String teamId, String isOnlyQueryMyOwn) {

        Bundle args = new Bundle();
        args.putString("flag", flag);
        args.putString("teamId", teamId);
        args.putString("isOnlyQueryMyOwn", isOnlyQueryMyOwn);
        TeamDetailFragment fragment = new TeamDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TeamListFragBinding binding;
    private TeamDetailContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<TeamMember> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<TeamMember> lpRecyclerViewAdapter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        lpRecyclerViewAdapter.setPageCurrentNum(lpRecyclerViewAdapter.getPageStartNum());
        mPresenter.loadTeamList(getArguments().getString("teamId"), getArguments().getString("isOnlyQueryMyOwn"), lpRecyclerViewAdapter.getPageStartNum());
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
        if ("mine".equals(getArguments().getString("flag"))) {
            setHasOptionsMenu(true);
        }
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
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.team_detail_item, BR.lp_rv_item, binding.lpScsr, binding.lpRv);
        lpRecyclerViewAdapter.setPageStartNum(1);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), MemberActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("member", mRVData.get(position));
                intent.putExtra("flag", getArguments().getString("flag"));
                intent.putExtras(bundle);
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
                mPresenter.loadTeamList(getArguments().getString("teamId"), getArguments().getString("isOnlyQueryMyOwn"), lpRecyclerViewAdapter.getPageCurrentNum() + 1);

            }
        });
        lpRecyclerViewAdapter.setOnRefreshListener(new LPRefreshLoadListener.onRefreshListener() {
            @Override
            public void onRefresh() {
                //数据刷新操作
                mPresenter.loadTeamList(getArguments().getString("teamId"), getArguments().getString("isOnlyQueryMyOwn"), lpRecyclerViewAdapter.getPageStartNum());
            }
        });
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);
        return root;
    }

    @Override
    public void setPresenter(@NonNull TeamDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.team_add_member, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_member:
                Intent intent = new Intent(getActivity(), MemberAddActivity.class);
                intent.putExtra("teamId", getArguments().getString("teamId"));
                intent.putExtra("teamName", getArguments().getString("teamName"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadTeamResult(boolean result, ArrayList<TeamInfo> arrayList, int pageCount) {
        ArrayList<TeamMember> teamMembers = new ArrayList<>();
        if (arrayList != null && arrayList.size() > 0) {
            teamMembers = arrayList.get(0).getTeamMembers();
        }
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
            mRVData.addAll(teamMembers);
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
