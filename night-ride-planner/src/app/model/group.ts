import { User } from "./user";

export class Group {
    public id: number;
    public name: string;
    public description: string;
    public usersInGroup: Array<User>;
    public events: Array<Event>;
}