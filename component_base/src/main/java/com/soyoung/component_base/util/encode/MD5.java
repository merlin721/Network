package com.soyoung.component_base.util.encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/9/27
 * description   : xxxx描述
 */

public class MD5 {

    /**
     * @param plainText 明文
     * @return 32位密文
     */
    public static String md5_32(String plainText) {
        String re_md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}
