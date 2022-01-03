package com.tino.selflearning.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.tino.selflearning.cache.CacheService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EmbeddedCacheConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance(createConfig());
    }

    public Config createConfig() {
        Config config = new Config();
        config.addMapConfig(blacklistJwtMapConfig());
        return config;
    }

    private MapConfig blacklistJwtMapConfig() {
        MapConfig mapConfig = new MapConfig(CacheService.BLACKLIST_JWT);
        mapConfig.setTimeToLiveSeconds(86400);
        mapConfig.setMaxIdleSeconds(20);
        return mapConfig;
    }
}
