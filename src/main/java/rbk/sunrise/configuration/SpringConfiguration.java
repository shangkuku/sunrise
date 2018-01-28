package rbk.sunrise.configuration;


import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rbk.sunrise.shiro.MyRealm;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;

@Configuration
public class SpringConfiguration {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("rbk.sunrise.dao");
        return mapperScannerConfigurer;
    }

    @Bean
    public Realm realm(@Qualifier("dataSource") DataSource dataSource) {
        JdbcRealm realm = new MyRealm();
        realm.setDataSource(dataSource);
        realm.setUserRolesQuery();
    }
}
