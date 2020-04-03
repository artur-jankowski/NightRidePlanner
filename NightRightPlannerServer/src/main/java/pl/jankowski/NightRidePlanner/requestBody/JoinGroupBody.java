package pl.jankowski.NightRidePlanner.requestBody;

import lombok.Getter;
import lombok.Setter;
import pl.jankowski.NightRidePlanner.entity.GroupEntity;
import pl.jankowski.NightRidePlanner.entity.UserEntity;

public class JoinGroupBody {
    @Getter
    @Setter
    private GroupEntity group;

    @Getter
    @Setter
    private UserEntity user;
}
