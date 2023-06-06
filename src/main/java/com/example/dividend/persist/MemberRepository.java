package com.example.dividend.persist;

import com.example.dividend.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
