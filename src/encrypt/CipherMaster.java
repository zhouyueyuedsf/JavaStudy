package encrypt;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangll on 2017/10/25.
 */

/*

查词日期：
key:  ydsecret://query.date/key/cuDF$unYSKRYjuwjXaZI1oTgA^4&B8NgSm5cIWpGbEN!hR*wxGYiCRThz0jYlZiN
iv : ydsecret://query.date/iv/%CFJ30H4vB14lE0BiX9YTdNsXs96XjSJlHWW^#6t^Ez8l3z0SjbPWsX0pwp1GDAW

查词：
key :  ydsecret://query/key/HhdIz&O3I4U7rigNIiP!IpidaFRqs@^b4N9l$WJRLa$hN3!v!*TldoiT3akf6iZl
iv : ydsecret://query/iv/b&6x$TFu0xxRZXRdB7UQSbVHHJnZF7z03esM%bvqw#ztA4wq0txOqi2!4viqmd87

词典身份验证的key :  ydsecret://query.token/signkey/ioHOsiqdVRrm@T!h!b9@pBf&YrE5FPe0I7qAup6WL^9AV16J4J
*/
public class CipherMaster {

    public static final int QUERY_DATE_KEY_ID = 1;
    public static final int QUERY_DATE_IV_ID = 2;
    public static final int QUERY_KEY_ID = 3;
    public static final int QUERY_IV_ID = 4;
    public static final int QUERY_TOKEN_ID = 5;
    public static final int USER_SESSION_KEY_ID = 6;
    public static final int USER_SESSION_IV_ID = 7;
    public static final int LOCK_SCREEN_QUERY_KEY_ID = 8;
    public static final int LOCK_SCREEN_QUERY_IV_ID = 9;

    private Map<Integer, String> keys = new HashMap<>();

    private CipherMaster() {
        keys.put(QUERY_DATE_KEY_ID, "ydsecret://query.date/key/cuDF$unYSKRYjuwjXaZI1oTgA^4&B8NgSm5cIWpGbEN!hR*wxGYiCRThz0jYlZiN");
        keys.put(QUERY_DATE_IV_ID, "ydsecret://query.date/iv/%CFJ30H4vB14lE0BiX9YTdNsXs96XjSJlHWW^#6t^Ez8l3z0SjbPWsX0pwp1GDAW");
        keys.put(QUERY_KEY_ID, "ydsecret://query/key/HhdIz&O3I4U7rigNIiP!IpidaFRqs@^b4N9l$WJRLa$hN3!v!*TldoiT3akf6iZl");
        keys.put(QUERY_IV_ID, "ydsecret://query/iv/b&6x$TFu0xxRZXRdB7UQSbVHHJnZF7z03esM%bvqw#ztA4wq0txOqi2!4viqmd87");
        keys.put(QUERY_TOKEN_ID, "ydsecret://query.token/signkey/ioHOsiqdVRrm@T!h!b9@pBf&YrE5FPe0I7qAup6WL^9AV16J4J");
        keys.put(USER_SESSION_KEY_ID, "ydsecret://user.session/key/OLnKpSL0zf2#fGMyU37cPoffn#^YijDaSxqzbGD*7pgEojA@0hu^aUCeX8^T");
        keys.put(USER_SESSION_IV_ID, "ydsecret://user.session/iv/1qT0M$2pb5WjlUiKOt15WgUJbiig^lTccBcKGP67D9UwYswX5RGO6Kq^YEMG");
        keys.put(LOCK_SCREEN_QUERY_KEY_ID, "ydsecret://lockscreen.query/key/2aFXKNnlRtQ2rnO8Uu%22H%gl2gc^^g6j3g^iBR6kVT*VsZpoE0a9KJ");
        keys.put(LOCK_SCREEN_QUERY_IV_ID, "ydsecret://lockscreen.query/iv/0U6VFNp51YCsPoFP5q*ptZg$@fq3qS9Z1plDm$uUANbcP2b7x4pJbw4");
    }

    private static class CipherMasterHolder {
        private static CipherMaster INSTANCE = new CipherMaster();
    }

    public static CipherMaster getInstance() {
        return CipherMasterHolder.INSTANCE;
    }

    private String getKey(int keyID) {
        return keys.get(keyID);
    }

    public String decodeAES(int keyID, int ivID, String cipherText) {
        try {
            String key = getKey(keyID);
            String iv = getKey(ivID);
            SimplifyKeyAES simplifyKeyAes = new SimplifyKeyAES(key, 128, iv);
            return simplifyKeyAes.decrypt(cipherText);
        } catch (Exception e) {
            return "";
        }
    }

    public String getSign(String before, String after, int keyID) {
        final String sign = StringUtils.convertToMD5Format(before + getKey(keyID) + after);
        return sign;
    }

    public String encodeAES(int keyID, int ivID, String plainText) {
        try {
            String key = getKey(keyID);
            String iv = getKey(ivID);
            SimplifyKeyAES simplifyKeyAes = new SimplifyKeyAES(key, 128, iv);
            return simplifyKeyAes.encrypt(plainText);
        } catch (Exception e) {
            return "";
        }
    }

    public byte[] decodeAES(int keyID, int ivID, byte[] cipherBytes) {
        try {
            String key = getKey(keyID);
            String iv = getKey(ivID);
            SimplifyKeyAES simplifyKeyAes = new SimplifyKeyAES(key, 128, iv, SimplifyKeyAES.TRANSFORMATION_ANDROID_NOPADDING);
            return simplifyKeyAes.decryptBytes(cipherBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] encodeAES(int keyID, int ivID, byte[] plainBytes) {
        try {
            String key = getKey(keyID);
            String iv = getKey(ivID);
            SimplifyKeyAES simplifyKeyAes = new SimplifyKeyAES(key, 128, iv, SimplifyKeyAES.TRANSFORMATION_ANDROID_NOPADDING);
            return simplifyKeyAes.encryptBytes(plainBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String cipher = new String("");
        String str = CipherMaster.getInstance().decodeAES(CipherMaster.QUERY_KEY_ID, CipherMaster.QUERY_IV_ID, cipher);

        System.out.println(str);
    }

}
