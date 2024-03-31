package com.d201.fundingift.wishlist.repository;

import com.d201.fundingift.wishlist.entity.Wishlist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WishlistRepository extends CrudRepository<Wishlist, String> {

    Optional<Wishlist> findByConsumerIdAndProductIdAndProductOptionId(Long consumerId, Long productId, Long productOptionId);
    Slice<Wishlist> findAllSliceByConsumerId(Long consumerId, Pageable pageable);

}
