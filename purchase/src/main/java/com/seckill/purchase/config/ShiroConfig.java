package com.seckill.purchase.config;

        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public MyRealm myRealm(){
        return new MyRealm();
    }


}
