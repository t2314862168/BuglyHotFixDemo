package com.chaychan.buglydemo;


/**
 * @author ChayChan
 * @description: TODO
 * @date 2017/11/17  21:52
 */

/**
 * 一些相关参考资料 : <a href="https://v.qq.com/x/page/z0393rt2cmq.html">腾讯Bugly07 渠道包的热更新</a><br>
 * <a href="https://www.jianshu.com/p/3027965a4e98">Bugly热修复 - 接入篇</a>
 */
public class BugClass {
    /**
     * 测试的时候使用,是否是基线版本(注意当打补丁版本的时候记得设置为false)
     */
    static boolean isBaseVersion = true;

    public static String getString() {
        String str = null;
        if (!isBaseVersion) {
            str = "我是补丁版本,渠道名字是" + BuildConfig.FLAVOR + "版本号1.0.7。。。。";
        } else {
            str = "我是基线版本,渠道名字是" + BuildConfig.FLAVOR + "版本号1.0.7。。。。";
        }
        int length = str.length();
        return str;
    }
}
