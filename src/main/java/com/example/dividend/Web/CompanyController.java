package com.example.dividend.Web;

import com.example.dividend.model.Company;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company") //공통 부분
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 자동완성
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autoComplete(@RequestParam String keyword){
        var result = this.companyService.getCompanyNameByKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    //회사 검색
    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageable){
        Page<CompanyEntity> companies = this.companyService.getAllCompany(pageable);
        return ResponseEntity.ok(companies);
    }

    //회사 추가
    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request){
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)){
            throw new RuntimeException("ticker is empty");
        }
       Company company = this.companyService.save(ticker);
        this.companyService.addAutocompleteKeyword(company.getName());
        return ResponseEntity.ok(company);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany(){
        return  null;
    }

}
