package com.alicp.jetcache.autoconfigure;

import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created on 2016/11/17.
 *
 * @author <a href="mailto:yeli.hl@taobao.com">huangli</a>
 */
@Configuration
@ConditionalOnClass(GlobalCacheConfig.class)
@ConditionalOnMissingBean(GlobalCacheConfig.class)
@EnableConfigurationProperties(JetCacheProperties.class)
@Import({RedisAutoConfiguration.class, CaffeineAutoConfiguration.class, LinkedHashMapAutoConfiguration.class})
public class JetCacheAutoConfiguration {

    public JetCacheAutoConfiguration(){
    }

    @Autowired
    private JetCacheProperties props;

    @Bean
    @ConditionalOnMissingBean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean
    public AutoConfigureBeans autoConfigureBeans(){
        return new AutoConfigureBeans();
    }

    @Bean
    public GlobalCacheConfig globalCacheConfig(SpringConfigProvider configProvider,AutoConfigureBeans autoConfigureBeans) {
        GlobalCacheConfig c = new GlobalCacheConfig();
        c.setConfigProvider(configProvider);
        c.setHiddenPackages(props.getHidePackages());
        c.setStatIntervalMinutes(props.getStatIntervalMinutes());
        c.setLocalCacheBuilders(autoConfigureBeans.getLocalCacheBuilders());
        c.setRemoteCacheBuilders(autoConfigureBeans.getRemoteCacheBuilders());
        return c;
    }

}
