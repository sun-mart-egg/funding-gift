package com.d201.fundingift.friend.repository;

import com.d201.fundingift.friend.entity.Friend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friend, String> {
}