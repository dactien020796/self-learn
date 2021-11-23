package com.tino.selflearning.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CachingService {

  public static final String BLACKLIST_JWT = "blacklist_jwt";

  private final CacheManager cacheManager;

  //@CachePut(cacheNames = BLACKLIST_JWT, key = "#username", value = "#jwt")
  public void blacklistJwt(String username, String jwt) {
    cacheManager.getCache(BLACKLIST_JWT).put(username, jwt);
    log.info("Add value to cache, cacheName = {}, key = {}, value = {}", BLACKLIST_JWT, username, jwt);
  }

  public Optional<Object> getValue(String cacheName, Object key) {
    Optional<Cache> optionalCache = Optional.ofNullable(cacheManager.getCache(cacheName));
    return optionalCache.map(cache -> cache.get(key));
  }
}
