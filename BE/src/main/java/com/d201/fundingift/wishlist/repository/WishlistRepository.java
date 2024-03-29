package com.d201.fundingift.wishlist.repository;

import com.d201.fundingift.wishlist.entity.Wishlist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WishlistRepository extends CrudRepository<Wishlist, Long> {

    Optional<Wishlist> findByConsumerId(Long consumerId);
    Optional<Wishlist> findByConsumerIdAndProductIdAndProductOptionId(Long consumerId, Long productId, Long productOptionId);

}
