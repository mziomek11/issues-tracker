package org.example.cqrs.event;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import java.util.Set;

public abstract class EventBuilder<T, S extends BaseEvent> {
    public Set<ConstraintViolation<T>> validate() {
        return Validation.buildDefaultValidatorFactory().getValidator().validate((T) this);
    }

    /**
     * @throws EventValidationException if event is not valid
     */
    public S build() {
        if (!validate().isEmpty()) {
            throw new EventValidationException();
        }

        return create();
    }

    protected abstract S create();
}
