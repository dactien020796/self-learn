package com.tino.selflearning.cache.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.tino.selflearning.cache.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheServiceImpl implements CacheService {

    private final HazelcastInstance hazelcastInstance;

    @Override
    public void blacklistJwt(String username, String jwt) {
        IMap<String, List<String>> blackedList = hazelcastInstance.getMap(CacheService.BLACKLIST_JWT);
        List<String> blackedTokens = blackedList.get(username);
        if (blackedTokens == null) {
            blackedTokens = new ArrayList<>();
        }
        blackedTokens.add(jwt);
        blackedList.put(username, blackedTokens);
        log.info("Add value to cache, cacheName = {}, key = {}, value = {}", BLACKLIST_JWT, username, jwt);
    }

    @Override
    public boolean isTokenBlackedList(String username, String jwt) {
        IMap<String, List<String>> blackedList = hazelcastInstance.getMap(CacheService.BLACKLIST_JWT);
        List<String> blackedTokens = blackedList.get(username);
        if (blackedTokens == null) {
            return false;
        }
        return blackedTokens.contains(jwt);
    }
}
