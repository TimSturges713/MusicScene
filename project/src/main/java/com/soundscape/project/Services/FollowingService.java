package com.soundscape.project.Services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.soundscape.project.Entities.Following;
import java.util.*;
import com.soundscape.project.Repos.FollowingRepository;

@Service
public class FollowingService {
    private final FollowingRepository followingRepository;

    public FollowingService(FollowingRepository followingRepository) {
        this.followingRepository = followingRepository;
    }

    /**
     * Retrieves the userIds of the followers of the user inputted
     * @param userId the Id of the user who's follower's are listed
     * @return A list of UserIds for the followers of the User
     */
    public List<Long> getFollowers(Long userId) {
        Following[] followObjs = followingRepository.findByFollowingId(userId);
        List<Long> followerIds = new ArrayList<Long>();
        for (Following follower : followObjs) {
            followerIds.add(follower.getFollower_id());
        }
        return followerIds;
    }

    public Following findByFollowingAndFollower(Long followingId, Long followerId){
        Following[] followObjs = followingRepository.findByFollowingId(followingId);
        for (Following follower : followObjs) {
            if(follower.getFollower_id() == followerId){
                return follower;
            }
        }
        return null;
    }

    /**
     * Retrieves the userIds of the users the inputted user follows
     * @param userId the Id of the user who's following list is being returned
     * @return A list of UserIds for User's the inputted user follows
     */
    public List<Long> getFollowing(Long userId) {
        Following[] followObjs = followingRepository.findByFollowerId(userId);
        List<Long> followingIds = new ArrayList<Long>();
        for (Following follower : followObjs) {
            followingIds.add(follower.getFollowing_id());
        }
        return followingIds;
    }

}
