package com.tangxb.pay.hero.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tangxb.pay.hero.R;
import com.tangxb.pay.hero.bean.BuyUnitBean;
import com.tangxb.pay.hero.bean.GoodsBean;
import com.tangxb.pay.hero.bean.GoodsCategoryBean;
import com.tangxb.pay.hero.bean.MBaseBean;
import com.tangxb.pay.hero.controller.GoodsCategoryController;
import com.tangxb.pay.hero.controller.GoodsEditorController;
import com.tangxb.pay.hero.controller.UploadPictureController;
import com.tangxb.pay.hero.event.ChooseDetailImgEvent;
import com.tangxb.pay.hero.event.ChooseHeadImgEvent;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.FileProvider7;
import com.tangxb.pay.hero.util.ImgUtil;
import com.tangxb.pay.hero.util.MDialogUtils;
import com.tangxb.pay.hero.util.MPinyinUtils;
import com.tangxb.pay.hero.util.SDCardFileUtils;
import com.tangxb.pay.hero.util.ToastUtils;
import com.tangxb.pay.hero.view.AlertProgressDialog;
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
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by tangxuebing on 2018/5/15.
 */

public class GoodsEditorActivity extends BaseActivityWithTitleRight {
    @BindView(R.id.et_goods_name)
    EditText mGoodsNameEt;
    @BindView(R.id.tv_on_sale)
    TextView mOnSaleTv;
    @BindView(R.id.tv_goods_category)
    TextView mGoodsCategoryTv;
    @BindView(R.id.et_goods_describe)
    EditText mGoodsDescEt;
    @BindView(R.id.tv_buy_unit)
    TextView mBuyUnitTv;
    @BindView(R.id.et_weight)
    EditText mWeightEt;
    @BindView(R.id.unit)
    TextView mUnitDetailTv;
    @BindView(R.id.tv_promotion)
    TextView mPromTv;
    @BindView(R.id.et_price)
    EditText mPriceEt;
    @BindView(R.id.et_freight)
    EditText mFreightEt;
    @BindView(R.id.layout_nine_grid2)
    MNineGridLayout2 gridLayout2;
    @BindView(R.id.layout_nine_grid3)
    MNineGridLayout3 gridLayout3;
    List<String> mHeaderImgs = new ArrayList<>();
    List<String> mDetailImgs = new ArrayList<>();
    int mChooseImgIndex = -1;
    LinkedBlockingQueue<String> mPhotoPathQueue = new LinkedBlockingQueue<>();
    UploadPictureController uploadPictureController;
    /**
     * 种类id
     */
    long categoryId;
    /**
     * 商品id
     */
    long goodsId;
    int promotion;
    int position;
    GoodsBean goodsBean;
    private final int RC_CAMERA = 0;
    private final int RC_ALBUM = 1;
    AlertDialog alertDialog;
    String headUrls;
    String detailUrls;
    GoodsEditorController editorController;
    GoodsCategoryController categoryController;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_editor;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        position = intent.getIntExtra("position", -1);
        goodsBean = intent.getParcelableExtra("goodsBean");
        if (goodsBean != null) {
            goodsId = goodsBean.getId();
            categoryId = goodsBean.getCategoryId();
        }
    }

    @Override
    protected void initData() {
        handleTitle();
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
        editorController = new GoodsEditorController(this);
        uploadPictureController = new UploadPictureController(this);
        categoryController = new GoodsCategoryController(this);
        showBeanInUI();
    }

    /**
     * 编辑商品
     */
    private void showBeanInUI() {
        String goodsName = mGoodsNameEt.getText().toString();
        String goodsCategory = mGoodsCategoryTv.getText().toString();
        String desc = mGoodsDescEt.getText().toString();
        String buyUnit = mBuyUnitTv.getText().toString();
        String weight = mWeightEt.getText().toString();
        String unitDetail = mUnitDetailTv.getText().toString();
        String prom = mPromTv.getText().toString();
        String freight = mFreightEt.getText().toString();
        List<String> headList = gridLayout2.getUrlList();
        List<String> detailList = gridLayout3.getUrlList();
        if (goodsBean == null) {
            setMiddleText(R.string.add_goods);
            return;
        }
        setMiddleText(goodsBean.getName());
        mGoodsNameEt.setText(goodsBean.getName());
        if (goodsBean.getStatus() == 0) {
            mOnSaleTv.setText("已下架");
            mOnSaleTv.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_red));
            mOnSaleTv.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red));
        } else if (goodsBean.getStatus() == 1) {
            mOnSaleTv.setText("售卖中");
            mOnSaleTv.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_main));
            mOnSaleTv.setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
        }
        mGoodsCategoryTv.setText(goodsBean.getCategoryName());
        mGoodsDescEt.setText(goodsBean.getSubtitle());
        mBuyUnitTv.setText(goodsBean.getUnit());
        mWeightEt.setText(goodsBean.getWeight());
        mPromTv.setText(goodsBean.getPromotion() == 1 ? "促销" : "正常");
        mPriceEt.setText(goodsBean.getPrice());
        mFreightEt.setText(goodsBean.getFreight());
        String subImages = goodsBean.getSubImages();
        String detailImages = goodsBean.getDetailImages();
        List<String> subImageList = new ArrayList<>();
        List<String> detailImageList = new ArrayList<>();
        if (subImages.contains(",")) {
            String[] split = subImages.split(",");
            for (String s : split) {
                subImageList.add(s);
            }
        } else {
            subImageList.add(subImages);
        }
        if (subImageList.size() < 6) {
            subImageList.add("");
        }
        gridLayout2.setUrlList(subImageList);
        if (TextUtils.isEmpty(detailImages)) return;
        if (subImages.contains(",")) {
            String[] split = detailImages.split(",");
            for (String s : split) {
                detailImageList.add(s);
            }
        } else {
            detailImageList.add(detailImages);
        }
        if (detailImageList.size() < 9) {
            detailImageList.add("");
        }
        gridLayout3.setUrlList(detailImageList);
    }

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
        SDCardFileUtils.creatDir2SDCard(fileDir);
        String fileName = DateFormat.format("yyyy_MM_dd_hh_mm_ss", new Date()) + ".jpg";
        File file = new File(fileDir, fileName);
        mPhotoPathQueue.offer(file.getAbsolutePath());
        Uri fileUri = FileProvider7.getUriForFile(this, file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
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
        }
        mChooseImgIndex = -1;
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
        if (goodsBean == null) {
            goodsBean = new GoodsBean();
            goodsBean.setStatus(1);
        }
        String goodsName = mGoodsNameEt.getText().toString();
        String goodsCategory = mGoodsCategoryTv.getText().toString();
        String desc = mGoodsDescEt.getText().toString();
        String buyUnit = mBuyUnitTv.getText().toString();
        String weight = mWeightEt.getText().toString();
        String unitDetail = mUnitDetailTv.getText().toString();
        String prom = mPromTv.getText().toString();
        String price = mPriceEt.getText().toString();
        String freight = mFreightEt.getText().toString();
        List<String> headList = gridLayout2.getUrlList();
        List<String> detailList = gridLayout3.getUrlList();
        if (TextUtils.isEmpty(goodsName)) {
            ToastUtils.t(mApplication, "名称不能为空");
            return;
        }
        goodsBean.setName(goodsName);
        if (TextUtils.isEmpty(goodsCategory)) {
            ToastUtils.t(mApplication, "种类不能为空");
            return;
        }
        goodsBean.setCategoryId(categoryId);
        goodsBean.setCategoryName(goodsCategory);
        if (TextUtils.isEmpty(desc)) {
            ToastUtils.t(mApplication, "描述不能为空");
            return;
        }
        goodsBean.setSubtitle(desc);
        if (TextUtils.isEmpty(buyUnit)) {
            ToastUtils.t(mApplication, "订购规格不能为空");
            return;
        }
        goodsBean.setUnit(buyUnit);
        if (TextUtils.isEmpty(weight)) {
            ToastUtils.t(mApplication, "重量不能为空");
            return;
        }
        goodsBean.setWeight(weight);
        if (TextUtils.isEmpty(price)) {
            ToastUtils.t(mApplication, "价格不能为空");
            return;
        }
        goodsBean.setPrice(price);
        if (TextUtils.isEmpty(prom)) {
            ToastUtils.t(mApplication, "选择促销与否");
            return;
        }
        goodsBean.setPromotion(prom.equals(mResources.getString(R.string.promotion)) ? 1 : 0);
        if (TextUtils.isEmpty(freight)) {
            ToastUtils.t(mApplication, "运费不能为空");
            return;
        }
        goodsBean.setFreight(freight);
        if (headList == null || headList.size() < 2) {
            ToastUtils.t(mApplication, "头图不能为空");
            return;
        }
        goodsBean.setMainImage(ConstUtils.BUCKET_PATH + headList.get(0));
        showAlertDialog();
        String categoryName = mGoodsNameEt.getText().toString();
        String productName = mGoodsCategoryTv.getText().toString();
        String userName = mApplication.getUserName();
        categoryName = MPinyinUtils.toPinyin(categoryName);
        productName = MPinyinUtils.toPinyin(productName);
        userName = MPinyinUtils.toPinyin(userName);

        if (detailList == null || detailList.size() < 2) {
            uploadPictureController.uploadPicturesSync(categoryName, productName, userName, headList, true)
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            headUrls = s;
                            uploadGoodsInfo();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            hideAlertDialog(false, throwable.getMessage());
                        }
                    });
        } else {
            Observable<String> headOb = uploadPictureController.uploadPicturesSync(categoryName, productName, userName, headList, true);
            Observable<String> detailOb = uploadPictureController.uploadPicturesSync(categoryName, productName, userName, detailList, true);
            Observable.zip(headOb, detailOb, new BiFunction<String, String, String>() {
                @Override
                public String apply(@NonNull String s, @NonNull String s2) throws Exception {
                    headUrls = s;
                    detailUrls = s2;
                    return s;
                }
            }).subscribe(new Consumer<String>() {
                @Override
                public void accept(String list) throws Exception {
                    uploadGoodsInfo();
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    hideAlertDialog(false, throwable.getMessage());
                }
            });
        }
    }

    /**
     * 上传数据
     */
    private void uploadGoodsInfo() {
        if (headUrls.contains(",")) {
            String[] split = headUrls.split(",");
            if (split.length > 0) {
                goodsBean.setMainImage(split[0]);
            }
        } else {
            goodsBean.setMainImage(headUrls);
        }
        goodsBean.setSubImages(headUrls);
        goodsBean.setDetailImages(detailUrls);
        addSubscription(editorController.uploadGoodsInfo(goodsBean), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                hideAlertDialog(true, baseBean.getMessage());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                hideAlertDialog(false, throwable.getMessage());
            }
        });
    }

    /**
     * 隐藏进度框
     */
    private void hideAlertDialog(boolean success, String msg) {
        ToastUtils.t(mApplication, msg);
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (success) {
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.putExtra("goodsBean", goodsBean);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 显示进度框
     */
    private void showAlertDialog() {
        if (alertDialog == null) {
            alertDialog = new AlertProgressDialog.Builder(mActivity)
                    .setView(R.layout.layout_alert_dialog)
                    .setCancelable(false)
                    .setMessage(R.string.commit_data_ing)
                    .show();
        }
        alertDialog.show();
    }

    /**
     * 点击上架
     *
     * @param view
     */
    @OnClick(R.id.tv_on_sale)
    public void clickOnSale(View view) {
        if (goodsBean == null) return;
        int status = goodsBean.getStatus();
        // 1、在售 0、已下架、-1、删除
        if (status == 1) {
            status = 0;
        } else if (status == 0) {
            status = 1;
        }
        addSubscription(editorController.changeStatus(goodsId, status), new Consumer<MBaseBean<String>>() {
            @Override
            public void accept(MBaseBean<String> baseBean) throws Exception {
                if (goodsBean.getStatus() == 1) {
                    goodsBean.setStatus(0);
                    mOnSaleTv.setText("已下架");
                    mOnSaleTv.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_red));
                    mOnSaleTv.setTextColor(ContextCompat.getColor(mActivity, R.color.color_red));
                } else {
                    goodsBean.setStatus(1);
                    mOnSaleTv.setText("售卖中");
                    mOnSaleTv.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.border_main));
                    mOnSaleTv.setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
                }
            }
        });

    }

    /**
     * 点击种类
     *
     * @param view
     */
    @OnClick(R.id.tv_goods_category)
    public void clickCategory(View view) {
        addSubscription(categoryController.getCategoryList(1), new Consumer<MBaseBean<List<GoodsCategoryBean>>>() {
            @Override
            public void accept(MBaseBean<List<GoodsCategoryBean>> baseBean) throws Exception {
                chooseCategory(baseBean.getData());
            }
        });
    }

    /**
     * 点击订购单位
     *
     * @param view
     */
    @OnClick(R.id.tv_buy_unit)
    public void clickBuyUnit(View view) {
        addSubscription(editorController.getUnits(), new Consumer<MBaseBean<List<BuyUnitBean>>>() {
            @Override
            public void accept(MBaseBean<List<BuyUnitBean>> baseBean) throws Exception {
                chooseBuyUnit(baseBean.getData());
            }
        });
    }

    /**
     * 点击促销
     *
     * @param view
     */
    @OnClick(R.id.tv_promotion)
    public void clickProm(View view) {
        final String[] items = new String[]{mResources.getString(R.string.normal), mResources.getString(R.string.promotion)};
        String title = "请选择类型";
        if (goodsBean != null) {
            title = "请选择(" + goodsBean.getName() + ")类型";
        }
        MDialogUtils.showSingleChoiceDialog(mActivity, title, items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                promotion = which;
                mPromTv.setText(items[which]);
            }
        });
    }

    /**
     * 选择种类
     *
     * @param list
     */
    private void chooseBuyUnit(List<BuyUnitBean> list) {
        int size = list.size();
        final String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            items[i] = list.get(i).getName();
        }
        String title = "请选择类型";
        if (goodsBean != null) {
            title = "请选择(" + goodsBean.getName() + ")类型";
        }
        MDialogUtils.showSingleChoiceDialog(mActivity, title, items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                mBuyUnitTv.setText(items[which]);
            }
        });
    }

    /**
     * 选择种类
     *
     * @param list
     */
    private void chooseCategory(List<GoodsCategoryBean> list) {
        int size = list.size();
        final long[] ids = new long[size];
        final String[] items = new String[size];
        for (int i = 0; i < size; i++) {
            ids[i] = list.get(i).getId();
            items[i] = list.get(i).getName();
        }
        String title = "请选择类型";
        if (goodsBean != null) {
            title = "请选择(" + goodsBean.getName() + ")类型";
        }
        MDialogUtils.showSingleChoiceDialog(mActivity, title, items, new MDialogUtils.OnCheckListener() {
            @Override
            public void onSure(int which, AlertDialog dialog) {
                dialog.dismiss();
                categoryId = ids[which];
                mGoodsCategoryTv.setText(items[which]);
            }
        });
    }
}
