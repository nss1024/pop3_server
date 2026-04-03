package mailbox;

import java.nio.file.Path;

public class MailMessage {

    int index;           // 1-based POP3 index
    String id;           // UUID (for UIDL)
    Path filePath;       // where the file lives
    long size;           // file size in bytes
    boolean deleted;     // DELE flag

    public MailMessage(int index, String id, Path filePath, long size, boolean deleted) {
        this.index = index;
        this.id = id;
        this.filePath = filePath;
        this.size = size;
        this.deleted = deleted;
    }

    private MailMessage() {
    }

    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    public Path getFilePath() {
        return filePath;
    }

    public long getSize() {
        return size;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "MailMessage{" +
                "index=" + index +
                ", id='" + id + '\'' +
                ", filePath=" + filePath +
                ", size=" + size +
                ", deleted=" + deleted +
                '}';
    }
}
