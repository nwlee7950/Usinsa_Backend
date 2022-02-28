package com.spring.usinsa.repository;

import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.product.DeliveryInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest    // JPA Repository 들에 대한 빈들을 등록 -> EmbeddedDatabase 를 사용해버리기 때문에, mysql 설정 불가.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // (replace = Replace.NONE)를 통해서 TestDatabaseAutoConfiguration 에서 DataSource 가 bean 으로 등록되지 않게 하면 DataSourceAutoConfiguration 에 의해서 DataSource 가 등록되게 된다.
public class DeliveryInfoRepositoryTest {

    @Autowired
    DeliveryInfoRepository deliveryInfoRepository;

    private static String deliverCompany = "로젠택배";
    private static String domesticOrOverseas = "국내배송";
    private static String storeDelivery = "직영점배송";
    private static int deliveryFee = 2500;
    private static int howMuchMorefreeFee = 50000;
    private long savedId = 0;
    @Test
    public void save(){
        DeliveryInfo deliveryInfo = DeliveryInfo.builder()
                .deliveryCompany(deliverCompany)
                .domesticOrOverseas(domesticOrOverseas)
                .storeDelivery(storeDelivery)
                .deliveryFee(deliveryFee)
                .howMuchMorefreeFee(howMuchMorefreeFee).build();

        DeliveryInfo saveDeliveryInfo = deliveryInfoRepository.save(deliveryInfo);

        assertThat(saveDeliveryInfo.getId()).isNotNull();
        savedId = saveDeliveryInfo.getId();
    }

    @Test
    public void findById(){
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findById(savedId)
                .orElseThrow(() -> new ApiException(ApiErrorCode.BRAND_NOT_FOUND));

        assertThat(deliveryInfo.getId()).isEqualTo(savedId);
    }
}
