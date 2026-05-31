package io.github.DekkerDing.ecommerce.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis 配置类
 * MyBatis Configuration Class
 * <p>
 * 当配置中选择 MyBatis 作为持久化方式时，启用 MyBatis 支持
 * Enables MyBatis support when MyBatis is selected as persistence type
 * </p>
 */
@Configuration
@MapperScan(basePackages = "io.github.DekkerDing.ecommerce.repository.mybatis")
@ConditionalOnProperty(prefix = "e-commerce.persistence", name = "type", havingValue = "mybatis")
public class MybatisConfig {

    /**
     * 配置 SqlSessionFactory
     * Configure SqlSessionFactory
     *
     * @param dataSource 数据源 / data source
     * @return SqlSessionFactory
     * @throws Exception 异常 / exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 配置 Mapper XML 文件位置
        // Configure Mapper XML file location
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));

        // 配置类型别名包
        // Configure type aliases package
        factoryBean.setTypeAliasesPackage("io.github.DekkerDing.ecommerce.domain");

        return factoryBean.getObject();
    }
}
