package com.ww.lp.base.modules.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.data.LoginResult;
import com.ww.lp.base.databinding.LoginFragBinding;
import com.ww.lp.base.entity.UserInfo;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/10/27.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginFragBinding loginFragBinding;
    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.login_frag, true);
        loginFragBinding = LoginFragBinding.bind(root);
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("ljpww72729@163.com");
        userInfo.setPassword("12345678");
        loginFragBinding.setUserInfo(userInfo);
        loginFragBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(loginFragBinding.getUserInfo());
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void success(LoginResult loginResult) {
        if (loginResult.getStatus() == 200) {
            //请求成功
            SPUtils.put(getActivity(), SPUtils.TOKEN, loginResult.getData().getToken());
            ToastUtils.toastShort(getString(R.string.dengluchenggong));
            getActivity().finish();
        } else {
            ToastUtils.toastShort(loginResult.getData().getErr_msg());
        }

    }

    @Override
    public void showProgressDialog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            ((BaseActivity) getActivity()).showProgressDialogLP();
        } else {
            ((BaseActivity) getActivity()).showProgressDialogLP(msg);
        }
    }

    @Override
    public void removeProgressDialog() {
        ((BaseActivity) getActivity()).removeProgressDialogLP();
    }


}
