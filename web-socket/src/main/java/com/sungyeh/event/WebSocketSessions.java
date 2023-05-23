package com.sungyeh.event;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketSessions
 * 考慮到使用者可能多個窗口或者重新連線socket session的改變，進行註冊以便找出對應接口
 *
 * @author sungyeh
 */
@Component
public class WebSocketSessions {
    private ConcurrentHashMap<String, String> sessionUsers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> serveMap = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        return "[WebSocketSessions] sessionUsers: " + sessionUsers.size();
    }

    public synchronized void registerSessionId(String user, String sessionId) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(sessionId, "sessionId must not be null");

        sessionUsers.put(sessionId, user);
    }

    public synchronized void removeSessionId(String sessionId) {
        Assert.notNull(sessionId, "sessionId must not be null");

        if (sessionUsers.containsKey(sessionId)) {
            sessionUsers.remove(sessionId);
        }
    }

    public List<String> getAllUsers() {
        return new ArrayList<>(sessionUsers.values());
    }

    public List<String> getAllSessionIds() {
        return new ArrayList<>(sessionUsers.keySet());
    }

    /**
     * 取得相同使用者的所有sessionIds
     */
    public List<String> getSessionIds(String user) {
        List<String> sessionIds = new ArrayList<>();
        for (Map.Entry<String, String> entry : sessionUsers.entrySet()) {
            String userInMap = entry.getValue();
            if (userInMap.equals(user)) {
                sessionIds.add(entry.getKey());
            }
        }
        return sessionIds;
    }

    public synchronized void registerServe(String ccs, String serve) {
        Assert.notNull(ccs, "user must not be null");
        Assert.notNull(serve, "sessionId must not be null");

        serveMap.put(ccs, serve);
    }

    public synchronized void unregisterServe(String ccs) {
        Assert.notNull(ccs, "user must not be null");
        serveMap.remove(ccs);
    }

    public String getServeId(String ccs) {
        return serveMap.get(ccs);
    }

    public List<String> getAllServeIds() {
        return new ArrayList<>(serveMap.keySet());
    }

}
