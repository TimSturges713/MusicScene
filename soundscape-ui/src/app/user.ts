/**
 * The user format for database entries
 */
export interface User {
    userId?: number;
    username: string;
    bio: string;
    city: string;
    password: string;
    email: string;
    state: string;
    spotify: boolean;
}
