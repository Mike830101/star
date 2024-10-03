package tw.mike.star.appcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppCoreApplication

fun main(args: Array<String>) {
    runApplication<AppCoreApplication>(*args)
}
