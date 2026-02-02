package ru.yandex.practicum.sleeptracker.analysis.func;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.Chronotype;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.yandex.practicum.sleeptracker.sleep.Chronotype.*;

public class UserChronotypeFunc implements SleepAnalysisFunction {

    private static final LocalTime SLEEP_LIMIT_LARK = LocalTime.of(22, 0);
    private static final LocalTime WAKE_LIMIT_LARK = LocalTime.of(7, 0);
    private static final LocalTime SLEEP_LIMIT_OWL = LocalTime.of(23, 0);
    private static final LocalTime WAKE_LIMIT_OWL = LocalTime.of(9, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        if (sleepSessions == null || sleepSessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", "Не определен");
        }

        Map<Chronotype, Long> counts = sleepSessions.stream()
                .filter(this::isNightSession)
                .map(this::determineNightType)
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        long larks = counts.getOrDefault(LARK, 0L);
        long owls = counts.getOrDefault(OWL, 0L);
        long pigeons = counts.getOrDefault(PIGEON, 0L);

        Chronotype finalType;
        if (larks > owls && larks > pigeons) {
            finalType = LARK;
        } else if (owls > larks && owls > pigeons) {
            finalType = OWL;
        } else {
            finalType = PIGEON;
        }

        return new SleepAnalysisResult("Хронотип пользователя", finalType.getName());
    }

    private boolean isNightSession(SleepSession session) {
        LocalTime start = session.getSleepStart().toLocalTime();
        LocalTime end = session.getSleepEnd().toLocalTime();
        return start.isBefore(LocalTime.of(6, 0)) || end.isAfter(LocalTime.MIDNIGHT) && start.isAfter(LocalTime.of(18,0)) || end.isBefore(LocalTime.of(12,0));
    }

    private Chronotype determineNightType(SleepSession session) {
        LocalTime start = session.getSleepStart().toLocalTime();
        LocalTime end = session.getSleepEnd().toLocalTime();

        if (start.isBefore(SLEEP_LIMIT_LARK) && end.isBefore(WAKE_LIMIT_LARK)) {
            return LARK;
        } else if (start.isAfter(SLEEP_LIMIT_OWL) && end.isAfter(WAKE_LIMIT_OWL)) {
            return OWL;
        } else {
            return PIGEON;
        }
    }
}
