package tw.mike.star.appcore.utils

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

class ValidatorUtils {
}

/**
 * 不可為空字串(可null)
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [StringNotBlankValidator::class])
annotation class StringNotBlank(
    val message: String = "不可為空字串",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
class StringNotBlankValidator : ConstraintValidator<StringNotBlank, String?> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value == null || (value.isNotEmpty())// 不能為 null、空字串或只有空白
    }
}