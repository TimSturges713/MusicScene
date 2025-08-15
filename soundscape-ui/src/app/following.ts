/**
 * The user format for database entries
 */
export interface Following {
    id?: number;
    followingId: number;
    followedId: number;
}