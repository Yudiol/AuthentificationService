package com.yudiol.jobsearchplatform.repository;

import com.yudiol.jobsearchplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
