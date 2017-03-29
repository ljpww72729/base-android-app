package com.ww.lp.base.modules.main.setting;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.SettingFragBinding;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class SettingFragment extends BaseFragment implements SettingContract.View {

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SettingFragBinding binding;
    private SettingContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.setting_frag, false);
        binding = SettingFragBinding.bind(root);
        binding.checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).showProgressDialogLP("正在检查更新...");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ((BaseActivity) getActivity()).removeProgressDialogLP();
                                ToastUtils.toastShort("已是最新版本");
                            }
                        });
                    }
                }, 1000);
            }
        });
        try {
            binding.version.setText(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        binding.clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BaseActivity) getActivity()).showProgressDialogLP("正在清除缓存...");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ((BaseActivity) getActivity()).removeProgressDialogLP();
                                ToastUtils.toastShort("已清除缓存");
                            }
                        });
                    }
                }, 2000);
            }
        });
        binding.sAutoUpdate.setChecked((Boolean) SPUtils.get(CustomApplication.self(), SPUtils.AUTO_UPDATE, true));
        binding.sAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(CustomApplication.self(), SPUtils.AUTO_UPDATE, isChecked);
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull SettingContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
