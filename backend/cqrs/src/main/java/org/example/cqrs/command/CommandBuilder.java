package org.example.cqrs.command;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import java.util.Set;

public abstract class CommandBuilder<T, S> {
    public Set<ConstraintViolation<T>> validate() {
        return Validation.buildDefaultValidatorFactory().getValidator().validate((T) this);
    }

    /**
     * @throws CommandValidationException if command is not valid
     */
    public S build() {
        if (!validate().isEmpty()) {
            throw new CommandValidationException();
        }

        return create();
    }

    protected abstract S create();
}
