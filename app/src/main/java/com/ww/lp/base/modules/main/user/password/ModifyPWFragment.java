package com.ww.lp.base.modules.main.user.password;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.data.user.UserInfo;
import com.ww.lp.base.databinding.ModifyPwFragBinding;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class ModifyPWFragment extends BaseFragment implements ModifyPWContract.View {

    public static ModifyPWFragment newInstance() {

        Bundle args = new Bundle();

        ModifyPWFragment fragment = new ModifyPWFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ModifyPwFragBinding binding;
    private ModifyPWContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.modify_pw_frag, false);
        binding = ModifyPwFragBinding.bind(root);
        final UserInfo userInfo = new UserInfo();
        userInfo.setEmail((String) SPUtils.get(CustomApplication.self(), SPUtils.EMAIL, ""));
        userInfo.setPhoneNum((String) SPUtils.get(CustomApplication.self(), SPUtils.PHONENUM, ""));
        userInfo.setAvatarImg((String) SPUtils.get(CustomApplication.self(), SPUtils.AVATAR_IMG, ""));
        binding.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.setPassword(binding.userPassword.getText().toString());
                mPresenter.modify(userInfo);
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull ModifyPWContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void success(boolean result) {
        //请求成功
        if (result) {
            ToastUtils.toastShort("密码修改成功");
            getActivity().finish();
        } else {
            ToastUtils.toastShort("密码修改失败，请重试！");
        }

    }

}
