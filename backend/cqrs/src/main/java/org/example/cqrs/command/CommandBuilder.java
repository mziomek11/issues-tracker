package org.example.cqrs.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

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
