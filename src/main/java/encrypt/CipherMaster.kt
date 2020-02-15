package encrypt

import java.util.*

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
class CipherMaster private constructor() {
    private val keys: MutableMap<Int, String> = HashMap()

    private object CipherMasterHolder {
        internal val INSTANCE = CipherMaster()
    }

    private fun getKey(keyID: Int): String? {
        return keys[keyID]
    }

    fun decodeAES(keyID: Int, ivID: Int, cipherText: String?): String {
        return try {
            val key = getKey(keyID)
            val iv = getKey(ivID)
            val simplifyKeyAes = SimplifyKeyAES(key!!, 128, iv)
            simplifyKeyAes.decrypt(cipherText)
        } catch (e: Exception) {
            ""
        }
    }

    fun getSign(before: String, after: String, keyID: Int): String {
        return StringUtils.convertToMD5Format(before + getKey(keyID) + after)
    }

    fun encodeAES(keyID: Int, ivID: Int, plainText: String?): String {
        return try {
            val key = getKey(keyID)
            val iv = getKey(ivID)
            val simplifyKeyAes = SimplifyKeyAES(key!!, 128, iv)
            simplifyKeyAes.encrypt(plainText!!)
        } catch (e: Exception) {
            ""
        }
    }

    fun decodeAES(keyID: Int, ivID: Int, cipherBytes: ByteArray?): ByteArray? {
        return try {
            val key = getKey(keyID)
            val iv = getKey(ivID)
            val simplifyKeyAes = SimplifyKeyAES(key!!, 128, iv, SimplifyKeyAES.TRANSFORMATION_ANDROID_NOPADDING)
            simplifyKeyAes.decryptBytes(cipherBytes)
        } catch (e: Exception) {
            null
        }
    }

    fun encodeAES(keyID: Int, ivID: Int, plainBytes: ByteArray?): ByteArray? {
        return try {
            val key = getKey(keyID)
            val iv = getKey(ivID)
            val simplifyKeyAes = SimplifyKeyAES(key!!, 128, iv, SimplifyKeyAES.TRANSFORMATION_ANDROID_NOPADDING)
            simplifyKeyAes.encryptBytes(plainBytes)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val QUERY_DATE_KEY_ID = 1
        const val QUERY_DATE_IV_ID = 2
        const val QUERY_KEY_ID = 3
        const val QUERY_IV_ID = 4
        const val QUERY_TOKEN_ID = 5
        const val USER_SESSION_KEY_ID = 6
        const val USER_SESSION_IV_ID = 7
        const val LOCK_SCREEN_QUERY_KEY_ID = 8
        const val LOCK_SCREEN_QUERY_IV_ID = 9
        val instance: CipherMaster
            get() = CipherMasterHolder.INSTANCE

        @JvmStatic
        fun main(args: Array<String>) {
            val cipher = ""
            val str: String = instance.decodeAES(QUERY_KEY_ID, QUERY_IV_ID, cipher)
            println(str)
        }
    }

    init {
        keys[QUERY_DATE_KEY_ID] = "ydsecret://query.date/key/cuDF\$unYSKRYjuwjXaZI1oTgA^4&B8NgSm5cIWpGbEN!hR*wxGYiCRThz0jYlZiN"
        keys[QUERY_DATE_IV_ID] = "ydsecret://query.date/iv/%CFJ30H4vB14lE0BiX9YTdNsXs96XjSJlHWW^#6t^Ez8l3z0SjbPWsX0pwp1GDAW"
        keys[QUERY_KEY_ID] = "ydsecret://query/key/HhdIz&O3I4U7rigNIiP!IpidaFRqs@^b4N9l\$WJRLa\$hN3!v!*TldoiT3akf6iZl"
        keys[QUERY_IV_ID] = "ydsecret://query/iv/b&6x\$TFu0xxRZXRdB7UQSbVHHJnZF7z03esM%bvqw#ztA4wq0txOqi2!4viqmd87"
        keys[QUERY_TOKEN_ID] = "ydsecret://query.token/signkey/ioHOsiqdVRrm@T!h!b9@pBf&YrE5FPe0I7qAup6WL^9AV16J4J"
        keys[USER_SESSION_KEY_ID] = "ydsecret://user.session/key/OLnKpSL0zf2#fGMyU37cPoffn#^YijDaSxqzbGD*7pgEojA@0hu^aUCeX8^T"
        keys[USER_SESSION_IV_ID] = "ydsecret://user.session/iv/1qT0M$2pb5WjlUiKOt15WgUJbiig^lTccBcKGP67D9UwYswX5RGO6Kq^YEMG"
        keys[LOCK_SCREEN_QUERY_KEY_ID] = "ydsecret://lockscreen.query/key/2aFXKNnlRtQ2rnO8Uu%22H%gl2gc^^g6j3g^iBR6kVT*VsZpoE0a9KJ"
        keys[LOCK_SCREEN_QUERY_IV_ID] = "ydsecret://lockscreen.query/iv/0U6VFNp51YCsPoFP5q*ptZg$@fq3qS9Z1plDm\$uUANbcP2b7x4pJbw4"
    }
}