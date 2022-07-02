package com.project.kantinkejujuran.repository;

import com.project.kantinkejujuran.model.BalanceBox;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceBoxRepository extends JpaRepository<BalanceBox, String> {

    @Query(value = "SELECT * FROM balance_box ORDER BY date_created DESC LIMIT 1",
            nativeQuery = true)
    @NonNull
    BalanceBox findLast();

    @Query(value = "SELECT COUNT(*) FROM balance_box", nativeQuery = true)
    @NonNull
    Integer findCount();
}
