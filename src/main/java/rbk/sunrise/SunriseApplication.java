package rbk.sunrise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import rbk.sunrise.base.BaseController;
import rbk.sunrise.base.BaseService;

@SpringBootApplication
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "rbk.sunrise.base.*"),
})
public class SunriseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SunriseApplication.class, args);
	}
}
