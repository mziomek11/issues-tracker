package org.example.cqrs.query;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

public abstract class QueryBuilder<T, S> {
    public Set<ConstraintViolation<T>> validate() {
        return Validation.buildDefaultValidatorFactory().getValidator().validate((T) this);
    }

    /**
     * @throws QueryValidationException if query is not valid
     */
    public S build() {
        if (!validate().isEmpty()) {
            throw new QueryValidationException();
        }

        return create();
    }

    protected abstract S create();
}
