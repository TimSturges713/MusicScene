package com.soundscape.project.Services;


import org.springframework.stereotype.Service;

import com.soundscape.project.Entities.Following;
import java.util.*;
import com.soundscape.project.Repos.FollowingRepository;

import jakarta.transaction.Transactional;

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
        List<Following> followObjs = followingRepository.findByFollowedId(userId);
        List<Long> followerIds = new ArrayList<Long>();
        for (Following follower : followObjs) {
            followerIds.add(follower.getFollowingId());
        }
        return followerIds;
    }

    public Following findByFollowingAndFollowed(Long followingId, Long followedId){
        List<Following> followObjs = followingRepository.findByFollowingId(followingId);
        for (Following follower : followObjs) {
            if(follower.getFollowedId() == followedId){
                return follower;
            }
        }
        return null;
    }

    @Transactional
    public List<Following> deleteUsersFollowing(Long userId){
        List<Following> followings = getAllFollowingsContainingUser(userId);
        followingRepository.deleteAll(followings);
        return followings;
    }

    public List<Following> getAllFollowingsContainingUser(Long userId){
        List<Following> followObjs = followingRepository.findByFollowingId(userId);
        followObjs.addAll(followingRepository.findByFollowedId(userId));
        return followObjs;
    }

    /**
     * Retrieves the userIds of the users the inputted user follows
     * @param userId the Id of the user who's following list is being returned
     * @return A list of UserIds for User's the inputted user follows
     */
    public List<Long> getFollowing(Long userId) {
        List<Following> followObjs = followingRepository.findByFollowingId(userId);
        List<Long> followingIds = new ArrayList<Long>();
        for (Following follower : followObjs) {
            followingIds.add(follower.getFollowedId());
        }
        return followingIds;
    }

}
