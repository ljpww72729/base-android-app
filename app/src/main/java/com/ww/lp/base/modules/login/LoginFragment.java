package com.ww.lp.base.modules.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.data.user.LoginResult;
import com.ww.lp.base.data.user.UserInfo;
import com.ww.lp.base.databinding.LoginFragBinding;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * 登录 Created by LinkedME06 on 16/10/27.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginFragBinding loginFragBinding;
    private LoginContract.Presenter mPresenter;
    //计时器
    private CountDownTimer timer;

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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.login_frag, false);
        loginFragBinding = LoginFragBinding.bind(root);
        UserInfo userInfo = new UserInfo();
//        if (com.ww.lp.base.BuildConfig.DEBUG) {
//            userInfo.setEmail("ljpww72729@163.com");
//            userInfo.setPassword("12345678");
//        }
        loginFragBinding.setUserInfo(userInfo);
        loginFragBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    mPresenter.login(loginFragBinding.getUserInfo());
                } else {
                    mPresenter.register(loginFragBinding.getUserInfo());
                }
            }
        });
        loginFragBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    loginFragBinding.phoneVerification.setVisibility(View.VISIBLE);
                    loginFragBinding.btnLogin.setText(getString(R.string.register));
                    loginFragBinding.btnRegister.setText(getString(R.string.has_account));
                } else {
                    if (timer != null){
                        timer.cancel();
                    }
                    loginFragBinding.phoneVerification.setVisibility(View.GONE);
                    loginFragBinding.btnVerification.setEnabled(true);
                    loginFragBinding.userPhone.setEnabled(true);
                    loginFragBinding.btnVerification.setBackgroundColor(ActivityCompat.getColor(getActivity(),R.color.primary));
                    loginFragBinding.btnVerification.setText("获取验证码");
                    loginFragBinding.btnLogin.setText(getString(R.string.login));
                    loginFragBinding.btnRegister.setText(getString(R.string.no_account));
                }
            }
        });
        loginFragBinding.btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.verification(loginFragBinding.getUserInfo());
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
        //请求成功
        SPUtils.put(CustomApplication.self(), SPUtils.TOKEN, loginResult.getData().getToken());
        SPUtils.put(CustomApplication.self(), SPUtils.EMAIL, TextUtils.isEmpty(loginFragBinding.getUserInfo().getEmail())?"":loginFragBinding.getUserInfo().getEmail());
        SPUtils.put(CustomApplication.self(), SPUtils.PHONENUM, TextUtils.isEmpty(loginFragBinding.getUserInfo().getPhoneNum())?"":loginFragBinding.getUserInfo().getPhoneNum());
        SPUtils.put(CustomApplication.self(), SPUtils.USER_ID, loginResult.getData().getUserId());
        SPUtils.put(CustomApplication.self(), SPUtils.IS_ADMIN, loginResult.getData().getIsAdmin());
        SPUtils.put(CustomApplication.self(), SPUtils.IS_DEVELOPER, loginResult.getData().getIsDeveloper());
        SPUtils.put(CustomApplication.self(), SPUtils.TEAM_ID, TextUtils.isEmpty(loginResult.getData().getTeamId())? "":loginResult.getData().getTeamId());
        SPUtils.put(CustomApplication.self(), SPUtils.ADMIN_EMAIL, loginResult.getData().getAdminEmail());
        int role = 0;
        if (loginResult.getData().getIsDeveloper() == 1) {
            //开发者
            role = 1;
        } else if (loginResult.getData().getIsAdmin() == 1) {
            //超级管理员
            role = 2;
        }
        SPUtils.put(CustomApplication.self(), SPUtils.ROLE, role);

        if (isLogin()) {
            ToastUtils.toastShort(getString(R.string.login_success));
        } else {
            ToastUtils.toastShort(getString(R.string.register_success));
        }
        getActivity().finish();

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

    @Override
    public void verificationResult(boolean result) {
        if (result) {
            loginFragBinding.btnVerification.setEnabled(false);
            loginFragBinding.userPhone.setEnabled(false);
            loginFragBinding.btnVerification.setBackgroundColor(ActivityCompat.getColor(getActivity(),R.color.counter_text_color));
            startCountDownTime(90);
        }
    }

    private void startCountDownTime(long time) {
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                loginFragBinding.btnVerification.setText(String.format(getString(R.string.count_down_str), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                loginFragBinding.btnVerification.setEnabled(true);
                loginFragBinding.userPhone.setEnabled(true);
                loginFragBinding.btnVerification.setBackgroundColor(ActivityCompat.getColor(getActivity(),R.color.primary));
                loginFragBinding.btnVerification.setText("获取验证码");
            }

        };
        timer.start();// 开始计时
    }

    public boolean isLogin() {
        return !loginFragBinding.btnLogin.getText().toString().equals(getString(R.string.register));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
        }
    }
}
