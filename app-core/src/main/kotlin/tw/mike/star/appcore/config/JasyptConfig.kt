package tw.mike.star.appcore.config

import org.jasypt.encryption.StringEncryptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tw.mike.star.appcore.dbencrypt.AesGcmStringEncryptor

@Configuration
class JasyptConfig(
  @Value("\${jasypt.encryptor.password}") private val pd: String
){

  @Bean(name = ["encryptorBean"])
  fun stringEncryptor(): StringEncryptor {
    println("pd: $pd")
    return AesGcmStringEncryptor(pd)
  }
}

