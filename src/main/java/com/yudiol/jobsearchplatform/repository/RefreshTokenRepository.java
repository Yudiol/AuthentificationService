package com.yudiol.jobsearchplatform.repository;

import com.yudiol.jobsearchplatform.dto.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String refreshToken);

    void deleteByUserId(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM refresh r USING users u WHERE r.user_id=u.id AND email = ?1", nativeQuery = true)
    void deleteByEmail(String email);
}
