package org.example.rest.v1;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RestValidationErrorsMapper {
    private RestValidationErrorsMapper() {}

    public static Map<String, Set<String>> toDtoErrors(
            Set<? extends ConstraintViolation> violations,
            Map<String, String> keyMap
    ) {
        var dtoErrors = new HashMap<String, Set<String>>();

        violations.forEach(violation -> {
            var iterator = violation.getPropertyPath().iterator();
            Path.Node pathNode = null;
            while (iterator.hasNext()) {
                pathNode = iterator.next();
            }

            if (pathNode == null) {
                return;
            }

            var builderFieldName = pathNode.getName();
            if (keyMap.containsKey(builderFieldName)) {
                var dtoFieldName = keyMap.get(builderFieldName);
                if (!dtoErrors.containsKey(dtoFieldName)) {
                    dtoErrors.put(dtoFieldName, new HashSet<>());
                }

                dtoErrors.get(dtoFieldName).add(violation.getMessage());
            }
        });

        return dtoErrors;
    }
}
