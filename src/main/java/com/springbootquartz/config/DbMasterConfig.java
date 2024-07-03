package com.springbootquartz.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.springbootquartz.mapper", sqlSessionFactoryRef="DbMasterSessionFactory")
public class DbMasterConfig {

    @Primary
    @Bean(name = "DbMasterSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dbMasterSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "DbMasterSessionFactory")
    public SqlSessionFactory dbMasterSessionFactory(@Qualifier("DbMasterSource") DataSource dbMasterDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dbMasterDataSource);
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatis-config/master-config.xml")); //mybatis 설정 xml 파일매핑
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.springbootquartz.domain"); //benas pakage에 dao나 vo 모아둘 때 구분하기 위해 쓰는 것도 좋음
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "DbMasterSessionTemplate")
    public SqlSessionTemplate dbMasterSessionTemplate(@Qualifier("DbMasterSessionFactory") SqlSessionFactory dbMasterSessionFactory) {
        return new SqlSessionTemplate(dbMasterSessionFactory);
    }

    @Primary
    @Bean(name = "DbMasterTransactionManager")
    public DataSourceTransactionManager dbMasterTransactionManager(@Qualifier("DbMasterSource") DataSource dbMasterDataSource) {
        return new DataSourceTransactionManager(dbMasterDataSource);
    }
}
