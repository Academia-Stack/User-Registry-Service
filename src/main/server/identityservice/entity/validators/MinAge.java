package identityservice.entity.validators;

import java.lang.annotation.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinAgeValidator.class)
@Documented
public @interface MinAge {
    String message() default "Age must be at least {minValue} years and less than {maxValue} years";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int minValue();
    int maxValue();
}
