package xyz.bekeychao.chatrobot.domain;

import java.time.LocalDateTime;

public class AlarmFuture {

    // 维护唯一Id
    private String uuid;

    // 维护的任务实例
//    @JsonIgnore
//    private ScheduledFuture<?> future;
    // corn表达式
    private String corn;

    // startTime
    private LocalDateTime time;

    // 间隔时间
    private Long rate;

    // 最后运行时间
    private LocalDateTime lastRunnableDate;

    private boolean cancelled;

    public AlarmFuture(String uuid, LocalDateTime time, String corn, Long rate) {
        this.uuid = uuid;
        this.time = time;
        this.corn = corn;
        this.rate = rate;
        this.cancelled = false;
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
}
