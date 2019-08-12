package com.soyoung.component_base.util;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jmn on 2015/10/27.
 */
public class NumberUtils {


    /**
     * 验证手机号的有效性
     *
     * @param mobiles
     * @return
     * @author jmn
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][0123456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证身份证号的有效性
     *
     */

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String regX = "[0-9]{17}X";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(regX) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断邮编
     *
     * @param
     * @return
     */
    public static boolean isZipNO(String zipString) {
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }


    /**
     * 判断手机号是否有效
     *
     * @param phoneNumber 手机号码
     * @return
     */
    public static Boolean checkPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtils.showToast(Utils.getApp(),"手机号不能为空");
            return false;
        }
        if (phoneNumber.length() < 11) {
            ToastUtils.showToast(Utils.getApp(),"手机号不能少于11位");
            return false;
        }

        if (!NumberUtils.isMobileNO(phoneNumber)) {
            ToastUtils.showToast(Utils.getApp(),"该手机号无效");
            return false;
        }
        return true;

    }


    /**
     * 判断字符串是否为纯数字
     *
     * @param str
     * @return
     */
    public static Boolean isNumber(String str) {

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }


    /**
     * 判断字符串是否为纯数字
     *
     * @param str
     * @return
     */
    public static Boolean isNumeric(String str) {

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }


    /**
     * 判断字符串是否为纯字母
     *
     * @param str
     * @return
     */
    public static Boolean isLetter(String str) {

        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(str);
        m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }


    /**
     * 判断字符串是否为纯汉字
     *
     * @param str
     * @return
     */
    public static Boolean isChineseCharacters(String str) {

        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(str);
        m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 验证一段字符串是否为纯汉字
     *
     * @param str
     * @return
     */
    public static Boolean isChineseCharacter(String str) {

        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]{0,}");
        Matcher m = p.matcher(str);
        m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 通过符号替换加密文本
     *
     * @param text
     * @param start
     * @param end
     * @param encryptionText
     */
    public static String getEncryptText(String text, int start, int end, String encryptionText) {
        StringBuilder sb = new StringBuilder(text);
        sb.replace(start, end, encryptionText);
        return sb.toString();
    }

    //整数相除 保留一位小数
    public static String division(int a, int b) {
        String result = "";
        float num = (float) a / b;

        DecimalFormat df = new DecimalFormat("0.0");

        result = df.format(num);
        if (result.endsWith(".0") && result.length() > 2)
            return result.substring(0, result.length() - 2);
        return result;

    }


    //保留一位小数
    public static String division(String carPrice) {

        float mPrice = Float.valueOf(carPrice);
        DecimalFormat df = new DecimalFormat("0.0");
        String result = df.format(mPrice);

        return result;

    }

    /**
     * 只能是汉字
     *
     * @param chinese
     * @return
     */
    public static int getHanZiCount(String chinese) {

        String regex = "[\\u4e00-\\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chinese);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * 整数7位小数2位
     *
     * @param money
     * @return
     */
    public static boolean getMoney(String money) {
        String re = "([1-9]\\d{0,6}|0)(\\.\\d{1,2})?";
        return money.matches(re);
    }

    /**
     * 汉字6个字符之后省略号
     *
     * @param str
     * @return
     */
    public static String getWordCount(String str) {
        try {
            String symbol = "...";
            int len = 12;
            int counterOfDoubleByte = 0;
            byte b[] = str.getBytes("GBK");
            if (b.length <= len)
                return str;
            for (int i = 0; i < len; i++) {
                if (b[i] < 0)
                    counterOfDoubleByte++;
            }
            if (counterOfDoubleByte % 2 == 0)
                return new String(b, 0, len, "GBK") + symbol;
            else
                return new String(b, 0, len - 1, "GBK") + symbol;

        } catch (UnsupportedEncodingException e) {

            return "";
        }
    }

    public static int StringToInteger(String num) {
            int result=0;
            try {
                result=Integer.valueOf(num);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            return result;
    }

}
