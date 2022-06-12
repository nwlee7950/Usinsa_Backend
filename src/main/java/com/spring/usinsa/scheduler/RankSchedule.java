package com.spring.usinsa.scheduler;

import com.spring.usinsa.dto.product.RankDto;
import com.spring.usinsa.dto.product.ViewedProductDto;
import com.spring.usinsa.service.RankService;
import com.spring.usinsa.service.ViewedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RankSchedule {

    @Autowired
    private ViewedProductService viewedProductService;
    @Autowired
    private RankService rankService;

    // Rank 테이블 사용 이유
    // 일일 랭크, 시간 단위 랭크는 데이터 row가 매우 많이 쌓이는 viewedProduct 테이블에
    // count 및 group by, order 등의 연산 등이 사용되어 성능이 안 좋다. 때문에 사용자가 요청할 때마다 실행하기엔 무리가 갈 것으로 판단
    // 반정규화를 통홰 Rank table을 따로 두고 scheduler에 의해 정해진 시간마다 Rank table에 Rank 갱신하여 시스템 성능 향상을 목적으로 함


    // scheduler에서 호출하는 service의 method들엔 @Transactional이 걸려있다.
    // @Transactional을 안걸어둬도 controller에서 받아 사용할 땐 문제가 없지만 scheduler에서 사용할때 could not initialize proxy가 뜬다
    // Lazy 로딩한 객체는 임시 프록시 객체로 채워뒀다 필요할 때 데이터를 불러오는 구조인데 데이터를 불러오는 위치가 영속성 컨텍스트의 생명주기가 끝나지 않았을 때만 가능하다.
    // 영속성 컨텍스트는 트랜잭션과 생명주기를 같이 하므로 메서드 위에 @Transactional을 적어줬다.
    // @Transactional은 선언적 트랜잭선이라고도 하며 method 범위로 트랜잭션이 되도록 보장해준다.

    // 일단 lazy로딩을 떠나서 기본 필드(컬럼)들도 로딩이 안된 것은 좀 더 확인해 봐야할 문제이다.

    /**
     * 매일 오전 9시 최근 24시간 동안의 랭킹 계산 및 저장
     */
    @Scheduled(cron = "0 0 9 * * ?")    //  초 분 시간 월 요일
    public void scheduleDailyRankTask(){
        List<RankDto> result = viewedProductService.getDailyLank();

        rankService.save(result, "DAILY");
    }

    /**
     * 매시마다 랭킹 계산 및 저장
     */
    @Scheduled(cron = "0 * * * * ?")    //  초 분 시간 월 요일
    public void scheduleEveryHourRankTask(){
        List<RankDto> result = viewedProductService.getEveryTimeLank();

        rankService.save(result, "TIME");
    }



}
