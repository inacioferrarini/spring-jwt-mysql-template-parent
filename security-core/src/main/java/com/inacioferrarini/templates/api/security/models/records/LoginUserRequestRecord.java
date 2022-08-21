package com.inacioferrarini.templates.api.security.models.records;

import javax.validation.constraints.NotEmpty;

public record LoginUserRequestRecord(
        @NotEmpty(message = "{constraints.user_name.not_empty.message}") String username,
        @NotEmpty(message = "{constraints.password.not_empty.message}") String password
) {

}
