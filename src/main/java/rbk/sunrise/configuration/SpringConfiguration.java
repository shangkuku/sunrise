package rbk.sunrise.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rbk.sunrise.base.BaseMapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class SpringConfiguration {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("rbk.sunrise.dao");
        mapperScannerConfigurer.setMarkerInterface(BaseMapper.class);
        return mapperScannerConfigurer;
    }
}
