package com.sap.csc.app.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.CacheManagerAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDAO extends AbstractSessionDAO implements CacheManagerAware {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String KEY_PREFIX = "session:";
	
	public static final String ACTIVE_SESSION_CACHE_NAME = "shiro-activeSessionCache";
	
	private CacheManager cacheManager;
	
	private Cache<String, Session> activeSession;
	
	private String activeSessionCacheName = ACTIVE_SESSION_CACHE_NAME;

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Cache<String, Session> getActiveSession() {
		return activeSession;
	}

	public void setActiveSession(Cache<String, Session> activeSession) {
		this.activeSession = activeSession;
	}
	
	public String getActiveSessionCacheName() {
		return activeSessionCacheName;
	}

	public void setActiveSessionCacheName(String activeSessionCacheName) {
		this.activeSessionCacheName = activeSessionCacheName;
	}

	private Cache<String, Session> getActiveSessionCacheLazy() {
		if(this.activeSession == null) {
			this.activeSession = createActiveSessionCache();
		}
		return activeSession;
	}
	
	protected Cache<String, Session> createActiveSessionCache() {
		Cache<String, Session> cache = null;
		CacheManager cacheMngr = getCacheManager();
		
		if(cacheMngr != null) {
			String name = getActiveSessionCacheName();
			cache = cacheMngr.getCache(name);
		}
		return cache;
	}
	
	protected Session getCachedSession(Serializable sessionId) {
        Session cached = null;
        if (sessionId != null) {
            Cache<String, Session> cache = getActiveSessionCacheLazy();
            if (cache != null) {
                cached = cache.get(getKey(sessionId));
            }
        }
        return cached;
    }
	
	public Serializable create(Session session) {
        Serializable sessionId = super.create(session);
        cache(session, sessionId);
        return sessionId;
    }
	
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session s = getCachedSession(sessionId);
        if (s == null) {
            s = super.readSession(sessionId);
        }
        return s;
    }
	
	public void update(Session session) throws UnknownSessionException {
		if (session instanceof ValidatingSession) {
            if (((ValidatingSession) session).isValid()) {
                cache(session, session.getId());
            } else {
                uncache(session, session.getId());
            }
        } else {
            cache(session, session.getId());
        }
	}

	public void delete(Session session) {
		uncache(session, session.getId());
	}

	public Collection<Session> getActiveSessions() {
		Cache<String, Session> cache = getActiveSessionCacheLazy();
		if(cache != null) {
			return cache.values();
		} else {
			return Collections.emptySet();
		}
	}
	
	protected void cache(Session session, Serializable sessionId) {
		if(session == null || sessionId == null) {
			return ;
		}
		Cache<String, Session> cache = getActiveSessionCacheLazy();
		if(cache == null) {
			return ;
		}
		cache.put(getKey(sessionId), session);
	}

	protected void uncache(Session session, Serializable sessionId) {
		if(session == null || sessionId == null) {
			return;
		}
		Cache<String, Session> cache = getActiveSessionCacheLazy();
		if(cache != null) {
			cache.remove(getKey(sessionId));
		}
	}

	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		return null;
	}

	private String getKey(Serializable sessionId) {
		return new StringBuilder(KEY_PREFIX).append(sessionId).toString();
	}

}
