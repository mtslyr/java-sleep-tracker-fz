package ru.yandex.practicum.sleeptracker.analysis.func;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class SleeplessNightsFunc implements SleepAnalysisFunction {

    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        if (sleepSessions == null || sleepSessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0);
        }

        LocalDateTime firstStart = sleepSessions.stream()
                .map(SleepSession::getSleepStart)
                .min(LocalDateTime::compareTo).get();

        LocalDateTime lastEnd = sleepSessions.stream()
                .map(SleepSession::getSleepEnd)
                .max(LocalDateTime::compareTo).get();

        LocalDate firstNight = firstStart.toLocalTime().isBefore(LocalTime.NOON)
                ? firstStart.toLocalDate()
                : firstStart.toLocalDate().plusDays(1);

        LocalDate lastNight = lastEnd.toLocalDate();

        long sleeplessCount = Stream
                .iterate(
                    firstNight, date -> !date.isAfter(lastNight),
                    date -> date.plusDays(1))
                .filter(nightDate -> isNightSleepless(nightDate, sleepSessions))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", (int) sleeplessCount);
    }

    private boolean isNightSleepless(LocalDate date, List<SleepSession> sessions) {
        LocalDateTime nightStart = date.atStartOfDay();
        LocalDateTime nightEnd = date.atTime(6, 0);

        return sessions.stream().noneMatch(s ->
                s.getSleepStart().isBefore(nightEnd) && s.getSleepEnd().isAfter(nightStart)
        );
    }

}
