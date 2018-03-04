package rbk.sunrise.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class SpringConfig {

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
}
