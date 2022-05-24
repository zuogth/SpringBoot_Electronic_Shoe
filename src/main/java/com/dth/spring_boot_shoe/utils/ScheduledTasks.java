package com.dth.spring_boot_shoe.utils;

import com.dth.spring_boot_shoe.entity.ProductDetailEntity;
import com.dth.spring_boot_shoe.entity.UserEntity;
import com.dth.spring_boot_shoe.repository.ProductDetailRepository;
import com.dth.spring_boot_shoe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;
    @Scheduled(cron = "0 0,3 4 * * ?")
    public void scheduleTask5hEveryDay(){
        log.info("Reset mỗi ngày lúc 5h sáng");
    }

//    @Scheduled(fixedRate = 5000)
//    public void scheduleTaskWithFixedRate() {
//        // call send email method here
//        List<ProductDetailEntity> entities = productDetailRepository.findAll();
//        entities.forEach(e->{
//            e.setDiscountId(0l);
//            productDetailRepository.save(e);
//        });
//        log.info("Send email to producers to inform quantity sold items");
//    }
}
