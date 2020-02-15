package encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * Created by xuyang on 15/11/19.
 */
public class StringUtils {

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 转化字符串到MD5格式
     *
     * @param value
     * @return
     */
    public static String convertToMD5Format(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(value.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte element: messageDigest) {
                hexString.append(String.format("%02x", 0xFF & element));
            }
            return hexString.toString();
        } catch (Exception e) {
            return value;
        }
    }
}
