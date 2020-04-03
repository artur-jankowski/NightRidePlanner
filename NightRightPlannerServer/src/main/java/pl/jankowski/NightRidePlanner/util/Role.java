package pl.jankowski.NightRidePlanner.util;

import lombok.Getter;

public enum Role {
    ADMINISTRATOR("ADMINISTRATOR"), USER("USER");

    @Getter
    private String roleValue;

    Role(String roleValue) {
        this.roleValue = roleValue;
    }
}
