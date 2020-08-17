import { Group } from './group';
import { User } from './user';
import { Localization } from './localization';

export class Event {
    public attendants: Array<User>;
    public description: string;
    public group: Group;
    public id: number;
    public localizations: Array<Localization>;
    public name: string;
    public type: string;
}
