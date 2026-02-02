package ru.yandex.practicum.sleeptracker.sleep;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepSession {

    final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private LocalDateTime sleepStart;
    final private LocalDateTime sleepEnd;
    final private Duration sleepDuration;
    final private SleepType type;

    public SleepSession(LocalDateTime sleepStart, LocalDateTime sleepEnd, Duration sleepDuration, SleepType type) {
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.sleepDuration = sleepDuration;
        this.type = type;
    }

    public SleepSession(String sleepSessionRecord) {
        String[] sleepData = sleepSessionRecord.split(";");
        this.sleepStart = LocalDateTime.parse(sleepData[0], DATE_TIME_FORMATTER);
        this.sleepEnd = LocalDateTime.parse(sleepData[1], DATE_TIME_FORMATTER);
        this.sleepDuration = Duration.between(sleepStart, sleepEnd);
        this.type = SleepType.valueOf(sleepData[2]);
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getSleepEnd() {
        return sleepEnd;
    }

    public Duration getSleepDuration() {
        return sleepDuration;
    }

    public SleepType getType() {
        return type;
    }

    public void setSleepStart(LocalDateTime newSleepStart) {
        this.sleepStart = newSleepStart;
    }

    @Override
    public String toString() {
        return "SleepSession{" +
                "sleepStart=" + sleepStart +
                ", sleepEnd=" + sleepEnd +
                ", sleepDuration=" + sleepDuration +
                ", type=" + type +
                '}';
    }
}
