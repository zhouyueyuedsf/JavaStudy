package encrypt;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SimplifyKeyAES {
    /**
     * 预设Initialization Vector，为16 Bits的0
     */
    private static final IvParameterSpec DEFAULT_IV = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    /**
     * 加密算法使用AES
     */
    private static final String ALGORITHM = "AES";
    /**
     * AES使用CBC模式与PKCS5Padding，默认变换
     */
    public static final String TRANSFORMATION_COMPATIBILITY = "AES/CBC/PKCS5Padding";//"AES/CBC/PKCS5Padding";
    /**
     * 该模式对于IOS 可能不支持，所以目前仅限于android平台自己使用
     */
    public static final String TRANSFORMATION_ANDROID_NOPADDING = "AES/OFB/NoPadding";//"AES/CBC/PKCS5Padding";

    /**
     * 取得AES加解密的秘钥
     */
    private Key key;
    /**
     * AES CBC模式使用的Initialization Vector
     */
    private IvParameterSpec iv;
    /**
     * Cipher
     */
    private Cipher cipher;

    /**
     * 使用128 Bits的AES秘钥(计算任意长度秘钥的MD5)和预设IV
     *
     * @param key 传入任意长度的AES秘钥
     */
    public SimplifyKeyAES(final String key) {
        this(key, 128);
    }

    /**
     * 使用128 Bits或是256 Bits的AES秘钥(计算任意长度密钥的MD5或是SHA256)和预设IV
     *
     * @param key 传入任意長度的AES秘钥
     * @param bit 传入AES秘钥长度，数值可以是128、256 (Bits)
     */
    public SimplifyKeyAES(final String key, final int bit) {
        this(key, bit, null);
    }

    /**
     * 使用128 Bits或是256 Bits的AES密钥(计算任意长度密钥的MD5或是SHA256)，用MD5计算IV值
     *
     * @param key 传入任意长度的AES密钥
     * @param bit 传入AES密钥长度，数值可以是128、256 (Bits)
     * @param iv  传入任意长度的IV字串
     */
    public SimplifyKeyAES(final String key, final int bit, final String iv) {
        this(key, bit, iv, TRANSFORMATION_COMPATIBILITY);
    }

    /**
     * 使用128 Bits或是256 Bits的AES密钥(计算任意长度密钥的MD5或是SHA256)，用MD5计算IV值
     *
     * @param key            传入任意长度的AES密钥
     * @param bit            传入AES密钥长度，数值可以是128、256 (Bits)
     * @param iv             传入任意长度的IV字串
     * @param transFormation 指定变换模式，使用 {@link SimplifyKeyAES#TRANSFORMATION_ANDROID_NOPADDING} 或 {@link SimplifyKeyAES#TRANSFORMATION_COMPATIBILITY}
     */
    public SimplifyKeyAES(final String key, final int bit, final String iv, String transFormation) {
        if (bit == 256) {
            this.key = new SecretKeySpec(getHash("SHA-256", key), ALGORITHM);
        } else {
            this.key = new SecretKeySpec(getHash("MD5", key), ALGORITHM);
        }
        if (iv != null) {
            this.iv = new IvParameterSpec(getHash("MD5", iv));
        } else {
            this.iv = DEFAULT_IV;
        }
//        {
//            byte[] hash = getHash("MD5", key);
//            byte[] md5s = getHash("MD5", iv);
//                System.out.println("key = " + Arrays.toString(hash) + ",iv = " + Arrays.toString(md5s));
//                System.out.println("key = " + SimpleEncryptDemo.HexEncoder.toHex(hash) + ",iv = " + SimpleEncryptDemo.HexEncoder.toHex(md5s));
//        }

        init(transFormation);
    }

    /**
     * 取得字串的Hash值
     *
     * @param algorithm 传入Hash算法名称
     * @param text      传入要hash的字串
     * @return 返回对应的hash值
     */
    private static byte[] getHash(final String algorithm, final String text) {
        try {
            return getHash(algorithm, text.getBytes("UTF-8"));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 获取Hash值
     *
     * @param algorithm 传入Hash算法
     * @param data      传入被散列的data字节数组
     * @return 返回hash值
     */
    private static byte[] getHash(final String algorithm, final byte[] data) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data);
            return digest.digest();
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 初始化
     */
    private void init(String transformation) {
        try {
            cipher = Cipher.getInstance(transformation);
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 加密字符串
     *
     * @param str 要加密的字符串
     * @return 返回密文
     */
    public String encrypt(final String str) {
        try {
            return encrypt(str.getBytes("UTF-8"));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 加密字节数组
     *
     * @param data 传入要加密的字节数组
     * @return 返回密文
     */
    public String encrypt(byte[] data) {
        try {


            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            final byte[] encryptData = cipher.doFinal(data);
//                System.out.println("encrypt data = " + Arrays.toString(encryptData)); //调试信息
            return Base64.getUrlEncoder().encodeToString(encryptData);
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 加密字节数组
     *
     * @param data 传入要加密的字节数组
     * @return 返回密文
     */
    public byte[] encryptBytes(byte[] data) {
        try {


            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            final byte[] encryptData = cipher.doFinal(data);
//                System.out.println("encrypt data = " + Arrays.toString(encryptData)); //调试信息
            return encryptData;
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 解密字节数组
     *
     * @param data 要解密的字节数组
     * @return 返回解密后的明文
     */
    public byte[] decryptBytes(final byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decryptData = cipher.doFinal(data);

            return decryptData;
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    /**
     * 解密字符串
     *
     * @param str 传入要解密的字符串
     * @return 返回解密后的明文
     */
    public String decrypt(final String str) {
        try {
            return decrypt(Base64.getUrlDecoder().decode(str));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 解密字节数组
     *
     * @param data 要解密的字节数组
     * @return 返回解密后的明文
     */
    public String decrypt(final byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decryptData = cipher.doFinal(data);

            return new String(decryptData, "UTF-8");
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}