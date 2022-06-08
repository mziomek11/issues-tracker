package com.mateuszziomek.issuestracker.organizations.command.projection;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends CrudRepository<Member, UUID> {
    Optional<Member> findByEmail(String email);
}
