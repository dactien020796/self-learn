package com.tino.selflearning.cache;

public interface CacheService {
    String BLACKLIST_JWT = "blacklist_jwt";

    void blacklistJwt(String username, String jwt);

    boolean isTokenBlackedList(String username, String jwt);

}
