package com.ww.lp.base.modules.team.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BR;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.components.rvrl.SingleItemClickListener;
import com.ww.lp.base.data.TeamResult;
import com.ww.lp.base.databinding.TeamListFragBinding;
import com.ww.lp.base.modules.team.detail.TeamDetailActivity;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TeamListFragment extends BaseFragment implements TeamListContract.View{

    public static TeamListFragment newInstance() {

        Bundle args = new Bundle();

        TeamListFragment fragment = new TeamListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TeamListFragBinding binding;
    private TeamListContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<TeamResult> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<TeamResult> lpRecyclerViewAdapter;

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
        View root = onCreateView(inflater, container,savedInstanceState, R.layout.team_list_frag, false);
        binding = TeamListFragBinding.bind(root);
        binding.lpRv.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.lpRv.setLayoutManager(mLayoutManager);
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.team_list_item, BR.lp_rv_item);
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                intent.putParcelableArrayListExtra("team_detail", mRVData.get(position).getTeamDetail());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return root;
    }

    @Override
    public void setPresenter(@NonNull TeamListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updateTeamList(ArrayList<TeamResult> arrayList) {
        mRVData.clear();
        mRVData.addAll(arrayList);
        lpRecyclerViewAdapter.notifyDataSetChanged();
    }
}
