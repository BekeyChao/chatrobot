package xyz.bekeychao.chatrobot.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author BekeyChao@github.com
 */
@Entity
public class Alarm {

    @Id
    // 维护唯一Id
    private String uuid;

    // 目标用户
    private String userId;

    // 内容
    private String content;

    // corn表达式
    private String corn;

    // startTime
    private LocalDateTime time;

    // 间隔时间
    private Long rate;

    // 最后运行时间
    private LocalDateTime lastRunnableDate;

    // 创建时间
    private LocalDateTime createDate;

    // 取消的
    private boolean cancelled;

    // 删除的
    private boolean deleted;

    public Alarm(String userId, String content, String corn, LocalDateTime time, Long rate, LocalDateTime lastRunnableDate, LocalDateTime createDate) {
        this.userId = userId;
        this.content = content;
        this.corn = corn;
        this.time = time;
        this.rate = rate;
        this.lastRunnableDate = lastRunnableDate;
        this.createDate = createDate;
        this.cancelled = false;
        this.deleted = false;
    }

    public LocalDateTime getLastRunnableDate() {
        return lastRunnableDate;
    }

    public void setLastRunnableDate(LocalDateTime lastRunnableDate) {
        this.lastRunnableDate = lastRunnableDate;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
