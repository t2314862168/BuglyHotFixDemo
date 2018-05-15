package com.tangxb.pay.hero.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.event.ChooseDetailImgEvent;
import com.tangxb.pay.hero.event.ChooseHeadImgEvent;
import com.tangxb.pay.hero.util.ImgUtil;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.tangxb.pay.hero.util.SDCardFileUtils;
import com.tangxb.pay.hero.view.MNineGridLayout2;
import com.tangxb.pay.hero.view.MNineGridLayout3;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsEditorActivity extends BaseActivityWithTitleRight {
    @BindView(R.id.et_goods_name)
    EditText mGoodsNameEt;
    @BindView(R.id.tv_on_sale)
    TextView mOnSaleTv;
    @BindView(R.id.et_goods_describe)
    EditText mGoodsDescEt;
    @BindView(R.id.et_stock)
    EditText mStockEt;
    @BindView(R.id.tv_no_limit)
    TextView mNoLimitTv;
    @BindView(R.id.tv_promotion)
    TextView mPromTv;
    @BindView(R.id.tv_buy_unit)
    TextView mButUnitTv;
    @BindView(R.id.et_unit_num)
    EditText mUnitNumEt;
    @BindView(R.id.tv_unit_detail)
    TextView mUnitDetailTv;
    @BindView(R.id.tv_price)
    TextView mPriceTv;
    @BindView(R.id.layout_nine_grid2)
    MNineGridLayout2 gridLayout2;
    @BindView(R.id.layout_nine_grid3)
    MNineGridLayout3 gridLayout3;
    List<String> mHeaderImgs = new ArrayList<>();
    List<String> mDetailImgs = new ArrayList<>();
    int mChooseImgIndex = -1;
    LinkedBlockingQueue<String> mPhotoPathQueue = new LinkedBlockingQueue<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_editor;
    }

    @Override
    protected void initData() {
        handleTitle();
        setMiddleText(R.string.add_goods);
        setRightText(R.string.upload_goods);
        setNeedOnCreateRegister();
        // 输入法不改变布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mHeaderImgs.add("");
        mDetailImgs.add("");
        gridLayout2.setUrlList(mHeaderImgs);
        gridLayout3.setUrlList(mDetailImgs);
        gridLayout2.setIsShowAll(false);
        gridLayout3.setIsShowAll(false);
    }

    private final int RC_CAMERA = 0;
    private final int RC_ALBUM = 1;

    /**
     * 选择添加头图
     *
     * @param event
     */
    @Subscribe
    public void onChooseHeadImgEvent(ChooseHeadImgEvent event) {
        mChooseImgIndex = 0;
        String[] item = new String[]{"相机", "相册"};
        MDialogUtils.showSingleChoiceDialog(mActivity, "选择添加头图", item, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                switch (which) {
                    case RC_CAMERA:
                        //相机
                        takePhotos();
                        break;
                    case RC_ALBUM:
                        //相册
                        Intent intent_album = new Intent(Intent.ACTION_PICK);
                        intent_album.setType("image/*");
                        startActivityForResult(intent_album, RC_ALBUM);
                        break;
                }
            }
        });
    }

    /**
     * https://www.jianshu.com/p/ba57444a7e69
     * 调用相机拍照
     */
    public void takePhotos() {
        String fileDir = SDCardFileUtils.getSDCardPath() + "DCIM/Camera/";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SDCardFileUtils.creatDir2SDCard(fileDir);
        String fileName = DateFormat.format("yyyy_MM_dd_hh_mm_ss", new Date()) + ".jpg";
        File file = new File(fileDir, fileName);
        mPhotoPathQueue.offer(file.getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri imageUri = FileProvider.getUriForFile(mActivity, mApplication.getPackageName() + ".provider", file);
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(intent, RC_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            mPhotoPathQueue.poll();
            return;
        }
        String imgUrl = null;
        switch (requestCode) {
            case RC_CAMERA:
                imgUrl = mPhotoPathQueue.poll();
                break;
            case RC_ALBUM:
                if (data != null) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        imgUrl = ImgUtil.handleImageOnKitKat(this, data);
                    } else {
                        imgUrl = ImgUtil.handleImageBeforeKitKat(this, data);
                    }
                }
                break;
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            if (mChooseImgIndex == 0) {
                gridLayout2.addImgUrl(imgUrl);
            } else if (mChooseImgIndex == 1) {
                gridLayout3.addImgUrl(imgUrl);
            }
            mChooseImgIndex = -1;
        }
    }

    /**
     * 选择添加详情图片
     *
     * @param event
     */
    @Subscribe
    public void onChooseDetailImgEvent(ChooseDetailImgEvent event) {
        mChooseImgIndex = 1;
        String[] item = new String[]{"相机", "相册"};
        MDialogUtils.showSingleChoiceDialog(mActivity, "选择详情图片", item, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                switch (which) {
                    case RC_CAMERA:
                        //相机
                        takePhotos();
                        break;
                    case RC_ALBUM:
                        //相册
                        Intent intent_album = new Intent(Intent.ACTION_PICK);
                        intent_album.setType("image/*");
                        startActivityForResult(intent_album, RC_ALBUM);
                        break;
                }
            }
        });
    }

    @Override
    public void clickRightBtn() {

    }

    /**
     * 点击无限
     *
     * @param view
     */
    @OnClick(R.id.tv_no_limit)
    public void clickNoLimit(View view) {
        mStockEt.setText(R.string.infinite);
    }
}
