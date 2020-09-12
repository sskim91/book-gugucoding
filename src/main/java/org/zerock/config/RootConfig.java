package org.zerock.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"org.zerock.sample"})
@MapperScan(basePackages = {"org.zerock.mapper"})
public class RootConfig {

    @Bean   //XML의 <bean>태그와 같음
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
        hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE");
        hikariConfig.setUsername("book_ex");
        hikariConfig.setPassword("book_ex");

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        return hikariDataSource;
    }

    @Bean
    public SqlSessionFactory sessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
    }

}
