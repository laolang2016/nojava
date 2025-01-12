package com.laolang.zuke.framework.mybatis.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.laolang.zuke.framework.mybatis.interceptor.MybatisPrintSqlInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@MapperScan("com.laolang.zuke.persist.**.mapper")
public class ZukeMyBatisAutoConfiguration {

    /**
     * 打印 sql 拦截器.
     *
     * @return Interceptor
     */
    @Profile("dev")
    @Bean
    public Interceptor mybatisPrintSqlInterceptor() {
        return new MybatisPrintSqlInterceptor();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public IKeyGenerator keyGenerator(){
        return new OracleKeyGenerator();
    }
}
