package com.ww.lp.base.modules.team.detail;

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
import com.ww.lp.base.data.TeamDetail;
import com.ww.lp.base.databinding.TeamListFragBinding;
import com.ww.lp.base.modules.team.member.MemberActivity;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class TeamDetailFragment extends BaseFragment implements TeamDetailContract.View{

    public static TeamDetailFragment newInstance(ArrayList<TeamDetail> arrayList) {

        Bundle args = new Bundle();
args.putParcelableArrayList("team_detail", arrayList);
        TeamDetailFragment fragment = new TeamDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TeamListFragBinding binding;
    private TeamDetailContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<TeamDetail> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<TeamDetail> lpRecyclerViewAdapter;

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
        mRVData = getArguments().getParcelableArrayList("team_detail");
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.lpRv.setLayoutManager(mLayoutManager);
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.team_detail_item, BR.lp_rv_item);
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), MemberActivity.class);
                intent.putExtra("member", mRVData.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return root;
    }

    @Override
    public void setPresenter(@NonNull TeamDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
