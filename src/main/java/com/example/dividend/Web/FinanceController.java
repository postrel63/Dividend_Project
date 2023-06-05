package com.example.dividend.Web;

import com.example.dividend.service.FinanceService;
import lombok.AllArgsConstructor;
import org.hibernate.engine.jdbc.cursor.spi.RefCursorSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//저장된 특정 회사의 배당금과 정보를 조회
@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @GetMapping("/dividend/{companyName}")
    public ResponseEntity<?> searchFinance(@PathVariable String companyName){
            var result = this.financeService.getDividendByCompanyName(companyName);
        return ResponseEntity.ok(result);
    }

}
