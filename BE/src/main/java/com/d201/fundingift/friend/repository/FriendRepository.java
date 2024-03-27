package com.d201.fundingift.friend.repository;

import com.d201.fundingift.friend.entity.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friend, String> {
}