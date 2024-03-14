package com.d201.fundingift.consumer.entity;

import com.d201.fundingift._common.entity.BaseTime;
import com.d201.fundingift.account.entity.Account;
import com.d201.fundingift.address.entity.Address;
import com.d201.fundingift.attendance.entity.Attendance;
import com.d201.fundingift.interest.entity.Interest;
import com.d201.fundingift.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consumer extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumer_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = true)
    private String profileImageUrl;

    @Column(nullable = true, length = 20)
    private String phoneNumber;

    @Column(nullable = true, length = 4)
    private String birthyear; // yyyy

    @Column(nullable = true, length = 4)
    private String birthday; // mmdd

    @Column(nullable = true, length = 6)
    private String gender;

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();

    @Builder
    private Consumer(String socialId, String email, String name, String profileImageUrl, String phoneNumber, String birthyear, String birthday, String gender) {
        this.socialId = socialId;
        this.email = email;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.phoneNumber = phoneNumber;
        this.birthyear = birthyear;
        this.birthday = birthday;
        this.gender = gender;
    }

}
