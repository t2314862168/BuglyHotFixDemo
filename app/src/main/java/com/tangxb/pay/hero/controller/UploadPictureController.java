package com.tangxb.pay.hero.controller;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.facebook.stetho.server.http.HttpStatus;
import com.tangxb.pay.hero.activity.BaseActivity;
import com.tangxb.pay.hero.util.ConstUtils;
import com.tangxb.pay.hero.util.FileNameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.xiaosai.imagecompress.ImageCompress;
import me.xiaosai.imagecompress.utils.BitmapUtil;

/**
 * Created by tangxuebing on 2018/5/18.
 */

public class UploadPictureController extends BaseControllerWithActivity {
    /**
     * 压缩之后的图片路径
     */
    private final String externalStorageDirectory;
    private File targetDir;
    private String targetDirName = "A_Lichuang";
    // 表示压缩之后的最大图片大小,单位为kb
    private int maxSize = 1024;
    private OSSClient ossClient;

    public UploadPictureController(BaseActivity baseActivity) {
        super(baseActivity);
        targetDir = new File(Environment.getExternalStorageDirectory(), targetDirName);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        externalStorageDirectory = targetDir.getAbsolutePath();
        mApplication.initOSS(baseActivity);
        ossClient = mApplication.getOssClient();
    }

    private String checkSuffix(String path) {
        if (TextUtils.isEmpty(path) || !path.contains(".")) {
            return ".jpg";
        }
        return path.substring(path.lastIndexOf("."), path.length());
    }

    private String getImageCacheFile(String suffix) {
        return targetDir + "/" +
                System.currentTimeMillis() +
                (int) (Math.random() * 1000) +
                (TextUtils.isEmpty(suffix) ? ".jpg" : suffix);
    }

    /**
     * 使用同步压缩方式
     *
     * @param filePaths
     */
    private Observable<List<String>> compressPictures(List<String> filePaths) {
        return Observable
                .just(filePaths)
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<List<String>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull List<String> list) throws Exception {
                        return Observable.just(compressPicturesSync(list));
                    }
                });
    }

    /**
     * 阿里云异步上传图片
     *
     * @param filePaths
     * @return
     */
    public Observable<List<String>> uploadPicturesASync(List<String> filePaths) {
        return compressPictures(filePaths)
                .concatMap(new Function<List<String>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull List<String> list) throws Exception {
                        List<String> tempList = new ArrayList<>();
                        for (String str : list) {
                            String uploadPictureSync = uploadPictureASync(str);
                            if (uploadPictureSync != null) {
                                tempList.add(uploadPictureSync);
                            }
                        }
                        return Observable.just(tempList);
                    }
                });
    }

    /**
     * 阿里云同步上传图片
     *
     * @param filePaths
     * @param needDeletePicture 上传之后是否需要删除本地压缩保存的图片
     * @return
     */
    public Observable<List<String>> uploadPicturesSync(List<String> filePaths, final boolean needDeletePicture) {
        return compressPictures(filePaths)
                .concatMap(new Function<List<String>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull List<String> list) throws Exception {
                        List<String> tempList = new ArrayList<>();
                        for (String str : list) {
                            String uploadPictureSync = uploadPictureSync(str);
                            if (uploadPictureSync != null) {
                                tempList.add(uploadPictureSync);
                            }
                        }
                        if (needDeletePicture) {
                            for (String str : list) {
                                File file = new File(str);
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                        }
                        return Observable.just(tempList);
                    }
                });
    }

    public String getServicePath(String category, String productName, String imageName) {
        return "product_image" + File.separator + category + File.separator + productName +
                File.separator + "唐小兵v1" + imageName;
    }

    /**
     * 阿里云异步上传图片
     *
     * @param filePaths
     * @return
     */
    public String uploadPictureASync(String filePaths) {
        String serviceImgPath = getServicePath("豆干", "豆腐干", FileNameUtils.getName(filePaths));
        try {
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentType("image/jpeg");
            // bucketName objectKey  uploadFilePath
            PutObjectRequest putObjectRequest = new PutObjectRequest(ConstUtils.BUCKET, serviceImgPath, filePaths);
            putObjectRequest.setMetadata(objectMeta);
            ossClient.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    int statusCode = result.getStatusCode();
                    System.out.println(statusCode);
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceImgPath;
    }

    /**
     * 阿里云同步上传图片
     *
     * @param filePaths
     * @return
     */
    public String uploadPictureSync(String filePaths) {
        try {
            String serviceImgPath = getServicePath("豆干", "豆腐干", FileNameUtils.getName(filePaths));
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentType("image/jpeg");
            // bucketName objectKey  uploadFilePath
            PutObjectRequest putObjectRequest = new PutObjectRequest(ConstUtils.BUCKET, serviceImgPath, filePaths);
            putObjectRequest.setMetadata(objectMeta);
            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
            int statusCode = putObjectResult.getStatusCode();
            if (statusCode != HttpStatus.HTTP_OK) {
                return null;
            }
            return serviceImgPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用同步压缩方式
     *
     * @param filePaths
     */
    private List<String> compressPicturesSync(List<String> filePaths) {
        List<String> list = new ArrayList<>();
        for (String filePath : filePaths) {
            String picture = compressPictureSync(filePath);
            if (picture != null) {
                list.add(picture);
            }
        }
        return list;
    }

    /**
     * 使用同步压缩方式
     *
     * @param filePath
     * @return
     */
    public String compressPictureSync(String filePath) {
        try {
            String targetCompressPath = getImageCacheFile(checkSuffix(filePath));
            String resultStr = BitmapUtil.compressBitmap(filePath, targetCompressPath, maxSize);
            if ("1".equals(resultStr)) {
                return targetCompressPath;
            } else {
                Log.e("compress", "onError==resultStr:" + resultStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("compress", "onError==msg:" + e.getMessage());
        }
        return null;
    }

    /**
     * 使用异步压缩方式
     *
     * @param filePath
     * @return
     */
    public void compressPictureASync(String filePath) {
        ImageCompress.with(baseActivity.get())
                .load(filePath)
                .setTargetDir(externalStorageDirectory)
                .ignoreBy(150)
                .setOnCompressListener(new ImageCompress.OnCompressListener() {
                    @Override
                    public void onStart() {
                        Log.e("compress", "onStart");
                    }

                    @Override
                    public void onSuccess(String filePath) {
                        Log.e("compress", "onSuccess=" + filePath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("compress", "onError");
                    }
                }).launch();
    }

}
