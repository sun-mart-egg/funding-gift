package com.d201.fundingift.friend.repository;

import com.d201.fundingift.friend.entity.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends CrudRepository<Friend, String> {

    List<Friend> findByConsumerId(Long consumerId);
    List<Friend> findByToConsumerId(Long consumerId);
    Optional<Friend> findByConsumerIdAndToConsumerId(Long consumerId, Long toConsumerId);
    List<Friend> findAllByToConsumerIdAndIsFavorite(Long toConsumerId, Boolean isFavorite);

}