package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulService.class);
        StatefulService statefuleService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefuleService2 = ac.getBean("statefulService", StatefulService.class);

        //ThreadA: A사용자 10000원 주문
        int userAprice = statefuleService1.order("userA", 10000);
        //ThreadB: B사용자 20000원 주문
        int userBprice = statefuleService1.order("userB", 20000);

        //ThreadA: A사용자 주문 금액 조회
//        int price = statefuleService1.getPrice();
        System.out.println("userAprice = " + userAprice);
        System.out.println("userBprice = " + userBprice);

        Assertions.assertThat(userAprice).isNotSameAs(userBprice);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}