package mailbox;

import java.util.concurrent.ConcurrentHashMap;

public class MailboxManager {

    private final ConcurrentHashMap<String, String> locks = new ConcurrentHashMap<>();

    public boolean lock(String username, String sessionId) {
        return locks.putIfAbsent(username, sessionId) == null;
    }

    public void unlock(String username, String sessionId) {
        locks.remove(username, sessionId);
    }

    public boolean isLocked(String username) {
        return locks.containsKey(username);

    }
}
