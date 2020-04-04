import { User } from './user';
import { Group } from './group';

export class JoinGroupRequest {
    user: User;
    group: Group;

    constructor(user, group) {
        this.user = user;
        this.group = group;
    }
}
