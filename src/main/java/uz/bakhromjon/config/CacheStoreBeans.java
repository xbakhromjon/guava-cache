package uz.bakhromjon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.bakhromjon.template.Template;

import java.util.concurrent.TimeUnit;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 19/10/22, Wed, 20:52
 **/
@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Template> employeeCache() {
        return new CacheStore<Template>(10, TimeUnit.MINUTES);
    }

}
