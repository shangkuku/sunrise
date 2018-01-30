package rbk.sunrise.configuration;


import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class SpringConfiguration {

    /**
     * mybatis mapper扫描配置
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("rbk.sunrise.dao");
        return mapperScannerConfigurer;
    }

    /**
     * 密码验证，采用默认的
     * @return
     */
    @Bean
    public PasswordMatcher credentialsMatcher() {
        return new PasswordMatcher();
    }

    /**
     * 加密用的，与验证的成对
     * @return
     */
    @Bean
    public PasswordService passwordService() {
        return this.credentialsMatcher().getPasswordService();
    }

    /**
     * 配置shiro realm缓存管理
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }
}
