package com.kevin.pictureschoose;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kevin.pictureschoose.utils.AppEditText;
import com.kevin.pictureschoose.utils.FullyGridLayoutManager;
import com.kevin.pictureschoose.utils.GridImageAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.case_AET)
    AppEditText caseAET;
    @BindView(R.id.case_TIL)
    TextInputLayout caseTIL;
    @BindView(R.id.pic_RV)
    RecyclerView picRV;
    @BindView(R.id.submit_BT)
    Button submitBT;
    // 已经选过的图片
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter gridImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    /**
     * 初始化
     */
    private void initView() {
        picRV.setLayoutManager(new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        gridImageAdapter = new GridImageAdapter(this, onAddPicClickListener);
        gridImageAdapter.setList(selectList);
        gridImageAdapter.setSelectMax(3);
        picRV.setAdapter(gridImageAdapter);
        gridImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    PictureSelector.create(MainActivity.this)
                            .externalPicturePreview(position, selectList);
                }
            }
        });
        caseAET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caseTIL.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限 
     */
    @Override
    protected void onDestroy() {
        PictureFileUtils.deleteCacheDirFile(this);
        Fresco.getImagePipeline().clearCaches();
        super.onDestroy();
    }

    /**
     * 图片点击选择
     */
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(MainActivity.this)
                    .openGallery(PictureMimeType.ofImage()) //  只有图片
                    .theme(R.style.picture_default_style)
                    .maxSelectNum(3)    //  最多选几个
                    .minSelectNum(1)    //  最少选几个
                    .imageSpanCount(4)  //  每行显示多少个
                    .selectionMode(PictureConfig.MULTIPLE)  //  多选
                    .previewImage(true) // 是否可预览
                    .compressGrade(Luban.THIRD_GEAR)
                    .isCamera(true) // 是否显示拍照按钮
                    .enableCrop(false)  //  是否剪裁
                    .compress(true) //是否压缩
                    .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)
                    .isGif(false)   // 是否显示GIF
                    .selectionMedia(selectList) // 传入已选图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList = PictureSelector.obtainMultipleResult(data);
                    gridImageAdapter.setList(selectList);
                    gridImageAdapter.notifyDataSetChanged();
                    break;
            }

        }
    }

}
