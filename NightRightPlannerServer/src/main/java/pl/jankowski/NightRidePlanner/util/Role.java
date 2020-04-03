package pl.jankowski.NightRidePlanner.util;

import lombok.Getter;

public enum Role {
    ADMINISTRATOR("ROLE_ADMINISTRATOR"), USER("ROLE_USER");

    @Getter
    private String roleValue;

    Role(String roleValue) {
        this.roleValue = roleValue;
    }
}
