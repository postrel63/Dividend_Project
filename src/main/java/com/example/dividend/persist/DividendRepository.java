package com.example.dividend.persist;

import com.example.dividend.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

    List<DividendEntity> findAllByCompanyId(Long companyId);

    // companyId와 date 로 유니크키 지정을 해줘서 인덱스를 사용하여 더 빠른 검색 가능
    // 성별 같이 중복이 많으면 인덱스 효과 X (카디널리티 낮음)
    // 주민등록번호같이 중복이 없으면 인덱스 효과 O (카디널리티 높음)
    boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);


}
