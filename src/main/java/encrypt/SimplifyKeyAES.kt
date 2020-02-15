package encrypt

import java.nio.charset.Charset
import java.security.Key
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class SimplifyKeyAES @JvmOverloads constructor(key: String, bit: Int = 128, iv: String? = null, transFormation: String = TRANSFORMATION_COMPATIBILITY) {
    /**
     * 取得AES加解密的秘钥
     */
    private var key: Key? = null
    /**
     * AES CBC模式使用的Initialization Vector
     */
    private var iv: IvParameterSpec? = null
    /**
     * Cipher
     */
    private var cipher: Cipher? = null

    /**
     * 初始化
     */
    private fun init(transformation: String) {
        cipher = try {
            Cipher.getInstance(transformation)
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
    }

    /**
     * 加密字符串
     *
     * @param str 要加密的字符串
     * @return 返回密文
     */
    fun encrypt(str: String): String {
        return try {
            encrypt(str.toByteArray(charset("UTF-8")))
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
    }

    /**
     * 加密字节数组
     *
     * @param data 传入要加密的字节数组
     * @return 返回密文
     */
    fun encrypt(data: ByteArray?): String {
        return try {
            cipher!!.init(Cipher.ENCRYPT_MODE, key, iv)
            val encryptData = cipher!!.doFinal(data)
            //                System.out.println("java.encrypt data = " + Arrays.toString(encryptData)); //调试信息
            Base64.getUrlEncoder().encodeToString(encryptData)
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
    }

    /**
     * 加密字节数组
     *
     * @param data 传入要加密的字节数组
     * @return 返回密文
     */
    fun encryptBytes(data: ByteArray?): ByteArray {
        return try {
            cipher!!.init(Cipher.ENCRYPT_MODE, key, iv)
            //                System.out.println("java.encrypt data = " + Arrays.toString(encryptData)); //调试信息
            cipher!!.doFinal(data)
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
    }

    /**
     * 解密字节数组
     *
     * @param data 要解密的字节数组
     * @return 返回解密后的明文
     */
    fun decryptBytes(data: ByteArray?): ByteArray {
        return try {
            cipher!!.init(Cipher.DECRYPT_MODE, key, iv)
            cipher!!.doFinal(data)
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
    }

    /**
     * 解密字符串
     *
     * @param str 传入要解密的字符串
     * @return 返回解密后的明文
     */
    fun decrypt(str: String?): String {
        return try {
            decrypt(Base64.getUrlDecoder().decode(str))
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
    }

    /**
     * 解密字节数组
     *
     * @param data 要解密的字节数组
     * @return 返回解密后的明文
     */
    fun decrypt(data: ByteArray?): String {
        return try {
            cipher!!.init(Cipher.DECRYPT_MODE, key, iv)
            val decryptData = cipher!!.doFinal(data)
            String(decryptData, Charset.defaultCharset())
        } catch (ex: Exception) {
            throw RuntimeException(ex.message)
        }
    }

    companion object {
        /**
         * 预设Initialization Vector，为16 Bits的0
         */
        private val DEFAULT_IV = IvParameterSpec(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
        /**
         * 加密算法使用AES
         */
        private const val ALGORITHM = "AES"
        /**
         * AES使用CBC模式与PKCS5Padding，默认变换
         */
        const val TRANSFORMATION_COMPATIBILITY = "AES/CBC/PKCS5Padding" //"AES/CBC/PKCS5Padding";
        /**
         * 该模式对于IOS 可能不支持，所以目前仅限于android平台自己使用
         */
        const val TRANSFORMATION_ANDROID_NOPADDING = "AES/OFB/NoPadding" //"AES/CBC/PKCS5Padding";

        /**
         * 取得字串的Hash值
         *
         * @param algorithm 传入Hash算法名称
         * @param text      传入要hash的字串
         * @return 返回对应的hash值
         */
        private fun getHash(algorithm: String, text: String): ByteArray {
            return try {
                getHash(algorithm, text.toByteArray(charset("UTF-8")))
            } catch (ex: Exception) {
                throw RuntimeException(ex.message)
            }
        }

        /**
         * 获取Hash值
         *
         * @param algorithm 传入Hash算法
         * @param data      传入被散列的data字节数组
         * @return 返回hash值
         */
        private fun getHash(algorithm: String, data: ByteArray): ByteArray {
            return try {
                val digest = MessageDigest.getInstance(algorithm)
                digest.update(data)
                digest.digest()
            } catch (ex: Exception) {
                throw RuntimeException(ex.message)
            }
        }
    }
    /**
     * 使用128 Bits或是256 Bits的AES密钥(计算任意长度密钥的MD5或是SHA256)，用MD5计算IV值
     *
     * @param key            传入任意长度的AES密钥
     * @param bit            传入AES密钥长度，数值可以是128、256 (Bits)
     * @param iv             传入任意长度的IV字串
     * @param transFormation 指定变换模式，使用 [SimplifyKeyAES.TRANSFORMATION_ANDROID_NOPADDING] 或 [SimplifyKeyAES.TRANSFORMATION_COMPATIBILITY]
     */
    /**
     * 使用128 Bits或是256 Bits的AES密钥(计算任意长度密钥的MD5或是SHA256)，用MD5计算IV值
     *
     * @param key 传入任意长度的AES密钥
     * @param bit 传入AES密钥长度，数值可以是128、256 (Bits)
     * @param iv  传入任意长度的IV字串
     */
    /**
     * 使用128 Bits或是256 Bits的AES秘钥(计算任意长度密钥的MD5或是SHA256)和预设IV
     *
     * @param key 传入任意長度的AES秘钥
     * @param bit 传入AES秘钥长度，数值可以是128、256 (Bits)
     */
    /**
     * 使用128 Bits的AES秘钥(计算任意长度秘钥的MD5)和预设IV
     *
     * @param key 传入任意长度的AES秘钥
     */
    init {
        if (bit == 256) {
            this.key = SecretKeySpec(getHash("SHA-256", key), ALGORITHM)
        } else {
            this.key = SecretKeySpec(getHash("MD5", key), ALGORITHM)
        }
        if (iv != null) {
            this.iv = IvParameterSpec(getHash("MD5", iv))
        } else {
            this.iv = DEFAULT_IV
        }
        //        {
//            byte[] hash = getHash("MD5", key);
//            byte[] md5s = getHash("MD5", iv);
//                System.out.println("key = " + Arrays.toString(hash) + ",iv = " + Arrays.toString(md5s));
//                System.out.println("key = " + SimpleEncryptDemo.HexEncoder.toHex(hash) + ",iv = " + SimpleEncryptDemo.HexEncoder.toHex(md5s));
//        }
        init(transFormation)
    }
}