package tw.mike.star.appcore

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import tw.mike.star.appcore.dbencrypt.AesGcmStringEncryptor
import kotlin.test.Test

@SpringBootTest
class AesGcmStringEncryptorTest(
    @Value("\${jasypt.encryptor.password}") private val pd: String
) {

    val encryptor = AesGcmStringEncryptor(pd)

    @Test
    fun testEncryptDecrypt() {
        val encText = "star123"
        val decText = "M4ak8pqiV8sVicHU4XscFE98aWaHTUJ06SrNcQJkAA3sJBY="

        val encryptedText = encryptor.encrypt(encText)
        val decryptedText = encryptor.decrypt(decText)
        println("encryptedText: $encryptedText")
        println("decryptedText: $decryptedText")
    }
}