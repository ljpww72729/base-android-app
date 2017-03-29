package com.ww.lp.base.modules.order.post;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ww.lp.base.BR;
import com.ww.lp.base.BaseActivity;
import com.ww.lp.base.BaseFragment;
import com.ww.lp.base.CustomApplication;
import com.ww.lp.base.R;
import com.ww.lp.base.components.rvrl.LPRecyclerViewAdapter;
import com.ww.lp.base.components.rvrl.SingleItemClickListener;
import com.ww.lp.base.data.project.ProjectImg;
import com.ww.lp.base.data.project.ProjectInfo;
import com.ww.lp.base.databinding.AddImgFooterBinding;
import com.ww.lp.base.databinding.PostFragBinding;
import com.ww.lp.base.entity.ImageInfo;
import com.ww.lp.base.utils.Constants;
import com.ww.lp.base.utils.SPUtils;
import com.ww.lp.base.utils.ToastUtils;
import com.ww.lp.base.utils.VerifyUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static android.app.Activity.RESULT_OK;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by LinkedME06 on 16/11/13.
 */

public class PostFragment extends BaseFragment implements PostContract.View, AdapterView.OnItemSelectedListener {

    private PostFragBinding binding;
    private PostContract.Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ImageInfo> mRVData = new ArrayList<>();
    private LPRecyclerViewAdapter<ImageInfo> lpRecyclerViewAdapter;
    private ArrayList<ProjectImg> imgCarouseList = new ArrayList<>();
    private boolean isAdd = true;
    private int classify = 4;
    private String classify_str = "其他";
    private ViewDataBinding footerBinding;

    public static PostFragment newInstance(Intent intent) {

        Bundle args = new Bundle();
        if (intent != null) {
            args.putParcelable("projectInfo", intent.getParcelableExtra("projectInfo"));
        }
        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
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
        View root = onCreateView(inflater, container, savedInstanceState, R.layout.post_frag, false);
        binding = PostFragBinding.bind(root);
        ProjectInfo projectInfo = new ProjectInfo();
        if (getArguments().getParcelable("projectInfo") != null) {
            projectInfo = getArguments().getParcelable("projectInfo");
            imgCarouseList = projectInfo.getProjectImgs();
            isAdd = false;
            binding.btnPost.setText("修改需求");
        }
        String describes = projectInfo.getDescribes();
        if (!TextUtils.isEmpty(describes)) {
            String[] describes_arr = describes.split(Constants.DES_SEPARATOR);
            if (describes_arr.length == 3) {
                //修改
                int classify = Integer.valueOf(describes_arr[0]);
                projectInfo.setClassify(classify);
                projectInfo.setDescribes(describes_arr[2]);
            }
        }
        String phoneNum = (String) SPUtils.get(CustomApplication.self(), SPUtils.PHONENUM, "");
        projectInfo.setPhoneNum(phoneNum);
        if (TextUtils.isEmpty(phoneNum)) {
            binding.phone.setEnabled(true);
        }
        binding.setProjectInfo(projectInfo);
        binding.lpRv.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.lpRv.setLayoutManager(mLayoutManager);
        lpRecyclerViewAdapter = new LPRecyclerViewAdapter<>(mRVData, R.layout.post_img_item, BR.lp_rv_item);
        binding.lpRv.setAdapter(lpRecyclerViewAdapter);
        footerBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.add_img_footer, null, false);
        ((AddImgFooterBinding) footerBinding).addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(4)
                        .setShowCamera(true)
                        .setPreviewEnabled(true)
                        .start(getActivity(), PostFragment.this, PhotoPicker.REQUEST_CODE);
            }
        });
        lpRecyclerViewAdapter.setmFooterBinding(footerBinding);
        binding.lpRv.addOnItemTouchListener(new SingleItemClickListener(binding.lpRv, new SingleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ArrayList<String> photos = new ArrayList<String>();
                for (ProjectImg imageInfo : imgCarouseList) {
                    photos.add(imageInfo.getImg());
                }
                PhotoPreview.builder()
                        .setPhotos(photos)
                        .setCurrentItem(position)
                        .setShowDeleteButton(false)
                        .start(getActivity());
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除？");
                builder.setTitle("提示信息");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteImg(position);
                        ToastUtils.toastLong("删除图片成功！");
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                builder.show();

            }
        }));
        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectInfo projectPostInfo = binding.getProjectInfo();
                if (VerifyUtils.isPhoneNum(projectPostInfo.getPhoneNum())) {
                    projectPostInfo.setProjectImgs(imgCarouseList);
                    String describes = classify + Constants.DES_SEPARATOR + classify_str + Constants.DES_SEPARATOR + projectPostInfo.getDescribes();
                    projectPostInfo.setDescribes(describes);
                    mPresenter.post(projectPostInfo, isAdd);
                }
            }
        });
        if (!isAdd) {
            showImgList();
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.arr_classify, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.classify.setAdapter(adapter);
        binding.classify.setOnItemSelectedListener(this);
        binding.classify.setSelection(projectInfo.getClassify());
        return root;
    }

    @Override
    public void setPresenter(@NonNull PostContract.Presenter presenter) {
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
    public void uploadFileResult(boolean result, ArrayList<String> imgList) {
        ((BaseActivity) getActivity()).removeProgressDialogLP();
        if (result) {
            for (int i = 0; i < imgList.size(); i++) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setUri(Uri.parse(imgList.get(i)));
                mRVData.add(imageInfo);
                ProjectImg carouselInfo = new ProjectImg();
                carouselInfo.setImg(imgList.get(i));
                imgCarouseList.add(carouselInfo);
            }
            lpRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addOrModifySuccess(boolean result) {
        if (result) {
            if (isAdd) {
                ToastUtils.toastShort("发布成功");
            } else {
                ToastUtils.toastShort("修改成功");
            }
            getActivity().finish();
        } else {
            if (isAdd) {
                ToastUtils.toastShort("发布失败，请重试~");
            } else {
                ToastUtils.toastShort("修改失败，请重试~");
            }
        }
    }


    public void showImgList() {
        for (int i = 0; i < imgCarouseList.size(); i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setUri(Uri.parse(imgCarouseList.get(i).getImg()));
            mRVData.add(imageInfo);
        }
        lpRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void deleteImg(int position) {
        mRVData.remove(position);
        imgCarouseList.remove(position);
        lpRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        classify_str = (String) parent.getSelectedItem();
        classify = parent.getSelectedItemPosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
