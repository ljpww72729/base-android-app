package com.ww.lp.base.modules.team.member.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.R;
import com.ww.lp.base.data.team.TeamMember;
import com.ww.lp.base.databinding.MemberAddFragBinding;
import com.ww.lp.base.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

import static android.app.Activity.RESULT_OK;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;
import static com.ww.lp.base.R.id.position;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class MemberAddFragment extends BaseFragment implements MemberAddContract.View {

    private String imgUrl = "";
    private boolean isAdd = true;

    public static MemberAddFragment newInstance(Bundle bundle) {
        MemberAddFragment fragment = new MemberAddFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private MemberAddFragBinding binding;
    private MemberAddContract.Presenter mPresenter;

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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.member_add_frag, false);
        binding = MemberAddFragBinding.bind(root);
        TeamMember teamMember = new TeamMember();
        if (getArguments().getParcelable("member") != null) {
            teamMember = getArguments().getParcelable("member");
            isAdd = false;
            imgUrl = teamMember.getImg();
            binding.img.setVisibility(View.VISIBLE);
            binding.img.setImageURI(imgUrl);
        }
        binding.setMember(teamMember);
        binding.imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(true)
                        .start(getActivity(), MemberAddFragment.this, PhotoPicker.REQUEST_CODE);
            }
        });
        binding.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                删除照片
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除？");
                builder.setTitle("提示信息");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.img.setVisibility(View.GONE);
                        binding.img.setImageURI("");
                        imgUrl = "";
                        ToastUtils.toastLong("删除图片成功！");
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                builder.show();
                return true;
            }
        });
        binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdd) {
                    ((BaseActivity) getActivity()).showProgressDialogLP("正在添加成员...");
                } else {
                    ((BaseActivity) getActivity()).showProgressDialogLP("正在更新成员信息...");
                }
                TeamMember teamMember = binding.getMember();
                teamMember.setImg(imgUrl);
                mPresenter.complete(teamMember, isAdd);
            }
        });
        return root;
    }

    @Override
    public void setPresenter(@NonNull MemberAddContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                ((BaseActivity) getActivity()).showProgressDialogLP("正在上传图片，请稍后...");
                mPresenter.uploadFile((ArrayList<String>) photos);
            }
        }
    }

    @Override
    public void uploadFileResult(boolean result, String uploadImgUrl) {
        ((BaseActivity) getActivity()).removeProgressDialogLP();
        if (result) {
            binding.img.setVisibility(View.VISIBLE);
            imgUrl = uploadImgUrl;
            binding.img.setImageURI(uploadImgUrl);
        }
    }

    @Override
    public void completeResult(boolean result) {
        ((BaseActivity) getActivity()).removeProgressDialogLP();
        if (result) {
            if (isAdd) {
                ToastUtils.toastShort("添加成功");
            } else {
                ToastUtils.toastShort("信息修改成功");
                Bundle bundle = new Bundle();
                TeamMember teamMember = binding.getMember();
                teamMember.setImg(imgUrl);
                bundle.putParcelable("member", teamMember);
                getActivity().setResult(RESULT_OK, getActivity().getIntent().putExtras(bundle));
            }
            getActivity().finish();
        }
    }
}
