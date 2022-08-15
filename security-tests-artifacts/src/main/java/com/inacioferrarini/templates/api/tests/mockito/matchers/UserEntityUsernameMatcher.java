package com.inacioferrarini.templates.api.tests.mockito.matchers;

import com.inacioferrarini.templates.api.security.models.entities.UserEntity;
import lombok.Data;
import org.mockito.ArgumentMatcher;
import org.springframework.data.domain.Example;

@Data
public class UserEntityUsernameMatcher implements ArgumentMatcher<Example<UserEntity>> {

    private Example<UserEntity> left;

    public UserEntityUsernameMatcher(Example<UserEntity> left) {
        this.left = left;
    }

    @Override
    public boolean matches(Example<UserEntity> right) {
        if (left == null) return false;
        left.getProbe();
        return left.getProbe()
                   .getUsername() != null && right != null && right.getProbe() != null && right.getProbe()
                                                                                               .getUsername() != null && left.getProbe()
                                                                                                                             .getUsername()
                                                                                                                             .equals(right.getProbe()
                                                                                                                                          .getUsername());
    }

}



