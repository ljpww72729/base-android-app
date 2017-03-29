package com.ww.lp.base.modules.main.about;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.AboutFragBinding;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class AboutFragment extends BaseFragment implements AboutContract.View{

    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private AboutFragBinding binding;
    private AboutContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container,savedInstanceState, R.layout.about_frag, false);
        binding = AboutFragBinding.bind(root);
        binding.introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.companyInfo.getVisibility() == View.GONE){
                    binding.more.setImageResource(R.drawable.va_arrow_show);
                    binding.companyInfo.setVisibility(View.VISIBLE);
                }else{
                    binding.more.setImageResource(R.drawable.va_arrow);
                    binding.companyInfo.setVisibility(View.GONE);
                }
            }
        });
        try {
            binding.version.setText(getString(R.string.version, getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void setPresenter(@NonNull AboutContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
