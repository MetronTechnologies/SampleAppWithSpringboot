package com.fcmb.mainapplication.service;

import com.fcmb.shared.dto.ApiResponse;
import com.fcmb.shared.entity.User;
import com.fcmb.shared.enums.Role;
import com.fcmb.mainapplication.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    public ApiResponse<Page<User>> fetchUsers(
            String username,
            Boolean enabled,
            Set<String> roleStrings,
            Instant createdAfter,
            Instant createdBefore,
            Pageable pageable,
            String path
    ) {
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (username != null) {
                predicates.add(cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
            }
            if (enabled != null) {
                predicates.add(cb.equal(root.get("enabled"), enabled));
            }
            if (roleStrings != null && !roleStrings.isEmpty()) {
                Set<Role> roles = roleStrings.stream()
                        .map(String::trim)
                        .map(Role::valueOf)
                        .collect(Collectors.toSet());
                predicates.add(root.join("roles").in(roles));
            }
            if (createdAfter != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdAfter));
            }
            if (createdBefore != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), createdBefore));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<User> users = userRepository.findAll(spec, pageable);
        return ApiResponse.success(users, "Users fetched successfully", 200, path);
    }
}

