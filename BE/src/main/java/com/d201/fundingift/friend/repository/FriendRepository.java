package com.d201.fundingift.friend.repository;

import com.d201.fundingift.friend.entity.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends CrudRepository<Friend, String> {
    List<Friend> findByConsumerId(Long consumerId);
    void deleteByConsumerId(Long consumerId);
    void deleteByToConsumerId(Long consumerId);

}