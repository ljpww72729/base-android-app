package com.ww.lp.base.modules.order.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.databinding.CommentFragBinding;
import com.ww.lp.base.utils.ToastUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class CommentFragment extends BaseFragment implements CommentContract.View{

    public static CommentFragment newInstance(Intent intent) {

        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(intent.getExtras());
        return fragment;
    }

    private CommentFragBinding binding;
    private CommentContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container,savedInstanceState, R.layout.comment_frag, false);
        binding = CommentFragBinding.bind(root);
        binding.btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.score(binding.score.getRating(), getArguments().getString("projectId"));
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull CommentContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void scoreResult(boolean result) {
        if (result){
            ToastUtils.toastShort("评分成功！");
            getActivity().finish();
        }
    }
}
