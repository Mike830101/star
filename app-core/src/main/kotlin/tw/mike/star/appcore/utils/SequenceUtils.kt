package tw.mike.star.appcore.utils

import com.fasterxml.uuid.EthernetAddress
import com.fasterxml.uuid.Generators
import com.fasterxml.uuid.TimestampSynchronizer
import java.io.IOException
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * 產生唯一鍵值的工具。
 */
object SequenceUtils {

  // UTC 2023/06/01 00:00:00
  private const val initTime = 1685548800000L

  private val lastSN = AtomicLong(1)
  private val sequenceUuidGenerator =
    Generators.timeBasedGenerator(EthernetAddress.fromInterface(), SimpleTimestampSynchronizer())

  /**
   * 產出唯一鍵值。
   * 格式：代表時間的long值（至milliseconds）
   * @return
   */
  @Synchronized
  fun produceSN(): Long {
    val currentTime = System.currentTimeMillis()
    val currentSN = currentTime - initTime
    if (currentSN == lastSN.get()) {
      lastSN.set(currentSN + 1)
    } else if (currentSN > lastSN.get()) {
      lastSN.set(currentSN)
    }
    return lastSN.get()
  }

  /**
   * 產生隨機唯一字串，長度32。
   * @return
   */
  @Synchronized
  fun uniqueString(): String {
    return UUID.randomUUID()
      .toString()
      .replace("-".toRegex(), "")
  }

  /**
   * Time based UUID
   * @return
   */
  fun sequenceUUID(): UUID {
    return sequenceUuidGenerator.generate()
  }

  class SimpleTimestampSynchronizer : TimestampSynchronizer() {
    private var lastValue: Long = 0
    private var serial = 1
    @Throws(IOException::class)
    override fun initialize(): Long {
      return System.currentTimeMillis()
    }

    @Throws(IOException::class)
    override fun deactivate() {
      serial = 1
      lastValue = 0
    }

    @Throws(IOException::class)
    override fun update(now: Long): Long {
      if (now == lastValue) {
        serial += 1
      } else {
        serial = 1
      }
      lastValue = now + serial
      return lastValue
    }
  }
}
