package com.hsnachos.config;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * packageName    : com.hsnachos.config
 * fileName       : RootConfig
 * author         : banghansol
 * date           : 2023/04/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/19        banghansol       최초 생성
 */
@Configuration
@ComponentScan({"com.hsnachos.domain", "com.hsnachos.service", "com.hsnachos.task"})
@MapperScan({"com.hsnachos.mapper"})
@Log4j
@PropertySource({"classpath:/jdbc2.properties"})
public class RootConfig {

    @Value("${db.classname}")
    private String driverClassName;
    @Value("${db.url}")
    private String jdbcUrl;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource dataSource(){

        HikariConfig config = new HikariConfig();

        //properties.load(new ClassPathResource("jdbc.properties").getInputStream());

        // config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        // config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@//localhost:1521/xe");
        // config.setJdbcUrl("jdbc:log4jdbc:mariadb://np.hsnachos.com:3306/spring_db");
        // config.setUsername("SAMPLE");
        // config.setPassword("1234");
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);


        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setTypeAliasesPackage("com.hsnachos.domain");
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
