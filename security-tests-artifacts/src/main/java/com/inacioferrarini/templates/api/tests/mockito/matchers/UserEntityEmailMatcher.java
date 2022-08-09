package com.inacioferrarini.templates.api.tests.mockito.matchers;

import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import lombok.Data;
import org.mockito.ArgumentMatcher;
import org.springframework.data.domain.Example;

@Data
public class UserEntityEmailMatcher implements ArgumentMatcher<Example<UserEntity>> {

    private Example<UserEntity> left;

    public UserEntityEmailMatcher(Example<UserEntity> left) {
        this.left = left;
    }

    @Override
    public boolean matches(Example<UserEntity> right) {
        return left != null && left.getProbe() != null && left.getProbe().getEmail() != null
                && right != null && right.getProbe() != null && right.getProbe().getEmail() != null
                && left.getProbe().getEmail().equals(right.getProbe().getEmail());
    }

}
