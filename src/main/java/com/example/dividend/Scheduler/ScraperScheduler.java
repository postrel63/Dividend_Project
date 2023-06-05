package com.example.dividend.Scheduler;

import com.example.dividend.model.Company;
import com.example.dividend.model.ScrapedResult;
import com.example.dividend.model.constants.CacheKey;
import com.example.dividend.persist.CompanyRepository;
import com.example.dividend.persist.DividendRepository;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.persist.entity.DividendEntity;
import com.example.dividend.scraper.Scraper;
import com.example.dividend.scraper.YahooFinanceScraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ScraperScheduler {
    //주기적으로 배당금을 가져오도록 하기 위함

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    private final Scraper yahooFinanceScraper;



    //                 초 분 시 일 월 요일
    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler is started");
        //저장된 회사 목록을 조회
        List<CompanyEntity> companyEntities = this.companyRepository.findAll();

        //회사마다 배당금 정보를 새로 스크래핑
        for (var company : companyEntities) {
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
                                            new Company(company.getTicker(),company.getName()));

        //스크래핑한 배당금 정보 중 데이버베이스에 없는 값은 저장
        scrapedResult.getDividends().stream()
                //Diviend 모델을 Diviend 엔티티에 매핑
                .map(e -> new DividendEntity(company.getId(), e))
            .forEach(e -> {
                boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                if (!exists){
                    this.dividendRepository.save(e);
                }
            });
        //연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }


        }

    }




}
