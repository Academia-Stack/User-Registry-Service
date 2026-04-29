package identityservice.entity.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    private int minAge, maxAge;

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.minValue();
        this.maxAge = constraintAnnotation.maxValue();
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
        if (dob == null) return true; // let @NotNull handle null
        return dob.isBefore(LocalDate.now().minusYears(minAge))
                && dob.isAfter(LocalDate.now().minusYears(maxAge));
    }
}