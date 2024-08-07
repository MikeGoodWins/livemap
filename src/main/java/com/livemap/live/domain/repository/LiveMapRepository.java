package com.livemap.live.domain.repository;

import com.livemap.live.domain.entity.LiveMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiveMapRepository extends JpaRepository<LiveMap, Long> {

    Optional<LiveMap> findById(Long id);

    Optional<LiveMap> findByPhone(Long phone);

}
