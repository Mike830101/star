package tw.mike.star.appcore.dbencrypt

import org.jasypt.encryption.StringEncryptor
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES GCM 加解密
 * @param pd 加密金鑰
 */
class AesGcmStringEncryptor(
  private val pd: String
) : StringEncryptor {

  private val CIPHER_AES_GCM = "AES/GCM/NoPadding"

  private val base64Encoder: Base64.Encoder = Base64.getEncoder()

  /**
   * 加密
   * @param message 明文
   * @return 加密字串
   */
  override fun encrypt(message: String?): String {
    try {
      val spec = SecretKeySpec(pd.toByteArray(StandardCharsets.UTF_8), "AES")
      val iv = ByteArray(12)
      SecureRandom().nextBytes(iv)
      val ivSpec = GCMParameterSpec(128, iv)
      val cipher = Cipher.getInstance(CIPHER_AES_GCM)
      cipher.init(Cipher.ENCRYPT_MODE, spec, ivSpec)
      val cipherVal = cipher.doFinal(message?.toByteArray(StandardCharsets.UTF_8))
      val result = ByteArray(iv.size + cipherVal.size)
      System.arraycopy(iv, 0, result, 0, iv.size)
      System.arraycopy(cipherVal, 0, result, iv.size, cipherVal.size)
      return base64Encoder.encodeToString(result)
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  /**
   * 解密
   * @param encryptedMessage 加密字串
   * @return 明文
   */
  override fun decrypt(encryptedMessage: String?): String {
    val base64Decoder = Base64.getDecoder()
    try {
      val cipher = Cipher.getInstance(CIPHER_AES_GCM)
      val key: SecretKey = SecretKeySpec(pd.toByteArray(StandardCharsets.UTF_8), "AES")
      val message: ByteArray = base64Decoder.decode(encryptedMessage)
      if (message.size < 12 + 16) {
        return ""
      }
      val ivSpec = GCMParameterSpec(128, message, 0, 12)
      cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
      val decryptData = cipher.doFinal(message, 12, message.size - 12)
      return String(decryptData)
    } catch (e:Exception) {
      throw RuntimeException(e)
    }
  }
}