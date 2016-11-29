package com.ww.lp.base.modules.team.member;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.data.TeamDetail;
import com.ww.lp.base.databinding.MemberDetailBinding;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MemberFragment extends BaseFragment implements MemberContract.View{

    public static MemberFragment newInstance(TeamDetail teamDetail) {

        Bundle args = new Bundle();
        args.putParcelable("member", teamDetail);
        MemberFragment fragment = new MemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MemberDetailBinding binding;
    private MemberContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container,savedInstanceState, R.layout.member_detail, false);
        binding = MemberDetailBinding.bind(root);
        binding.setMember((TeamDetail) getArguments().getParcelable("member"));
        return root;
    }

    @Override
    public void setPresenter(@NonNull MemberContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
