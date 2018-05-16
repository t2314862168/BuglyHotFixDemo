package com.tangxb.pay.hero.util;

import java.util.regex.Pattern;

/**
 * 账户相关属性验证工具
 *
 */
public class AccountValidatorUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
 
    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";
 
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
 
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
 
    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
 
    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
 
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
 
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    public static boolean isName(String name){
        if(name == null||name.trim().length()<2){
            return false;
        }
        String[] surName = {
                "赵","钱","孙","李","周","吴","郑","王","冯","陈",
                "楮","卫","蒋","沈","韩","杨","朱","秦","尤","许",
                "何","吕","施","张","孔","曹","严","华","金","魏",
                "陶","姜","戚","谢","邹","喻","柏","水","窦","章",
                "云","苏","潘","葛","奚","范","彭","郎","鲁","韦",
                "昌","马","苗","凤","花","方","俞","任","袁","柳",
                "酆","鲍","史","唐","费","廉","岑","薛","雷","贺",
                "倪","汤","滕","殷","罗","毕","郝","邬","安","常",
                "乐","于","时","傅","皮","卞","齐","康","伍","余",
                "元","卜","顾","孟","平","黄","和","穆","萧","尹",
                "姚","邵","湛","汪","祁","毛","禹","狄","米","贝",
                "明","臧","计","伏","成","戴","谈","宋","茅","庞",
                "熊","纪","舒","屈","项","祝","董","梁","杜","阮",
                "蓝","闽","席","季","麻","强","贾","路","娄","危",
                "江","童","颜","郭","梅","盛","林","刁","锺","徐",
                "丘","骆","高","夏","蔡","田","樊","胡","凌","霍",
                "虞","万","支","柯","昝","管","卢","莫","经","房",
                "裘","缪","干","解","应","宗","丁","宣","贲","邓",
                "郁","单","杭","洪","包","诸","左","石","崔","吉",
                "钮","龚","程","嵇","邢","滑","裴","陆","荣","翁",
                "荀","羊","於","惠","甄","麹","家","封","芮","羿",
                "储","靳","汲","邴","糜","松","井","段","富","巫",
                "乌","焦","巴","弓","牧","隗","山","谷","车","侯",
                "宓","蓬","全","郗","班","仰","秋","仲","伊","宫",
                "宁","仇","栾","暴","甘","斜","厉","戎","祖","武",
                "符","刘","景","詹","束","龙","叶","幸","司","韶",
                "郜","黎","蓟","薄","印","宿","白","怀","蒲","邰",
                "从","鄂","索","咸","籍","赖","卓","蔺","屠","蒙",
                "池","乔","阴","郁","胥","能","苍","双","闻","莘",
                "党","翟","谭","贡","劳","逄","姬","申","扶","堵",
                "冉","宰","郦","雍","郤","璩","桑","桂","濮","牛",
                "寿","通","边","扈","燕","冀","郏","浦","尚","农",
                "温","别","庄","晏","柴","瞿","阎","充","慕","连",
                "茹","习","宦","艾","鱼","容","向","古","易","慎",
                "戈","廖","庾","终","暨","居","衡","步","都","耿",
                "满","弘","匡","国","文","寇","广","禄","阙","东",
                "欧","殳","沃","利","蔚","越","夔","隆","师","巩",
                "厍","聂","晁","勾","敖","融","冷","訾","辛","阚",
                "那","简","饶","空","曾","毋","沙","乜","养","鞠",
                "须","丰","巢","关","蒯","相","查","后","荆","红",
                "游","竺","权","逑","盖","益","桓","公","仉","督",
                "晋","楚","阎","法","汝","鄢","涂","钦","归","海",
                "岳","帅","缑","亢","况","后","有","琴","商","牟",
                "佘","佴","伯","赏","墨","哈","谯","笪","年","爱",
                "阳","佟",
                "万俟","司马","上官","欧阳","夏侯",
                "诸葛","闻人","东方","赫连","皇甫",
                "尉迟","公羊","澹台","公冶","宗政",
                "濮阳","淳于","单于","太叔","申屠",
                "公孙","仲孙","轩辕","令狐","锺离",
                "宇文","长孙","慕容","鲜于","闾丘",
                "司徒","司空","丌官","司寇","南宫",
                "子车","颛孙","端木","巫马","公西",
                "漆雕","乐正","壤驷","公良","拓拔",
                "夹谷","宰父","谷梁","段干","百里",
                "东郭","南门","呼延","羊舌","微生",
                "梁丘","左丘","东门","西门"
        };
        String firstName = name.substring(0,1);
        for(String s : surName){
            String s1 = s.substring(0,1);
            if(s.contains(firstName)){
                return true;
            }
        }
        return false;
    }
}