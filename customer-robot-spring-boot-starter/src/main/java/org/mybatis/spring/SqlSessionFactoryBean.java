package org.mybatis.spring;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * Stub class for MyBatis SqlSessionFactoryBean
 * MyBatis SqlSessionFactoryBean 类的存根
 */
public class SqlSessionFactoryBean {
    private DataSource dataSource;
    private Resource[] mapperLocations;
    private String typeAliasesPackage;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public SqlSessionFactory getObject() throws Exception {
        return null;
    }
}
