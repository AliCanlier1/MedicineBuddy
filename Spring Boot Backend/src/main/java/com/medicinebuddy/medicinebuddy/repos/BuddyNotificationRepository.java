package com.medicinebuddy.medicinebuddy.repos;

import com.medicinebuddy.medicinebuddy.entities.BuddyNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuddyNotificationRepository extends JpaRepository<BuddyNotification, Integer> {
}
