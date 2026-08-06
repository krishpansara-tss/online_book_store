package com.online_bookstore.app.repositories;

import com.online_bookstore.app.dtos.users.UserBasicInformationResponseDTO;
import com.online_bookstore.app.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    User findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    Page<User> findByNameContainingIgnoreCase(String key, Pageable pageable);
    Page<User> findByIsActiveTrue(Pageable pageable);

    @Query("""
        SELECT new com.online_bookstore.app.dtos.users.UserBasicInformationResponseDTO (
            u.userId, u.name, u.email
        )
        FROM User u
""")
    Page<UserBasicInformationResponseDTO> getAllUserBasicInformation(Pageable pageable);

    @Query("""
        SELECT new com.online_bookstore.app.dtos.users.UserBasicInformationResponseDTO (
            u.userId, u.name, u.email
        )
        FROM User u
        WHERE u.isActive = true
""")
    Page<UserBasicInformationResponseDTO> getActiveUserBasicInformation(Pageable pageable);

}
