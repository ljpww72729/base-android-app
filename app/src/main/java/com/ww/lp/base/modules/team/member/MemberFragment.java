package com.ww.lp.base.modules.team.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.data.team.TeamMember;
import com.ww.lp.base.databinding.MemberDetailBinding;
import com.ww.lp.base.modules.team.member.add.MemberAddActivity;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MemberFragment extends BaseFragment implements MemberContract.View{

    public static final int MEMBER_REQUEST_CODE = 1000;

    public static MemberFragment newInstance(Bundle bundle) {

        MemberFragment fragment = new MemberFragment();
        fragment.setArguments(bundle);
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
        if ("mine".equals(getArguments().getString("flag"))) {
            setHasOptionsMenu(true);
        }
        binding.setMember((TeamMember) getArguments().getParcelable("member"));
        return root;
    }

    @Override
    public void setPresenter(@NonNull MemberContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.team_remove_member, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_member:
                Intent intent = new Intent(getActivity(), MemberAddActivity.class);
                intent.putExtras(getArguments());
                startActivityForResult(intent, MEMBER_REQUEST_CODE);
                return true;
            case R.id.remove_member:
                ((BaseActivity) getActivity()).showProgressDialogLP("正在移除成");
                mPresenter.removeMember((String) SPUtils.get(CustomApplication.self(), SPUtils.TEAM_ID, ""), binding.getMember());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == MEMBER_REQUEST_CODE){
                binding.setMember((TeamMember) data.getExtras().getParcelable("member"));
            }
        }
    }

    @Override
    public void removeResult(boolean result) {
        ((BaseActivity) getActivity()).removeProgressDialogLP();
        if (result) {
            ToastUtils.toastShort("移除成功！");
            getActivity().finish();
        }
    }
}
