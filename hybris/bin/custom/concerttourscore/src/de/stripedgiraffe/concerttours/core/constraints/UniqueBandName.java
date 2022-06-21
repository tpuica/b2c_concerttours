package de.stripedgiraffe.concerttours.core.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target(
{ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueBandNameValidator.class)
@Documented
public @interface UniqueBandName
{
	String message() default "{de.stripedgiraffe.concerttours.core.constraints.UniqueBandName.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}