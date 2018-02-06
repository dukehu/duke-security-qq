package com.duke.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.io.IOException;

/**
 * Created by duke on 2017/12/23
 */
@Configuration
public class MybatisConfig implements TransactionManagementConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisConfig.class);

    @Bean("sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactoryBean sqlSessionFactoryBean(HikariDataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        try {
            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:/*Mapper.xml"));
            LOGGER.info("文件读取成功");
        } catch (IOException e) {
            LOGGER.error("文件读取失败");
        }

        return sqlSessionFactoryBean;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return null;
    }
}
