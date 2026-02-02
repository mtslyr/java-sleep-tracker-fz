package ru.yandex.practicum.sleeptracker.analysis.func;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MinSessionDurationFunc implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        Optional<SleepSession> min = sleepSessions
                .stream()
                .min((s1, s2) -> s1.getSleepDuration().compareTo(s2.getSleepDuration()));

        if (min.isPresent()) {
            SleepSession minDurationSession = min.get();
            return new SleepAnalysisResult(
                    "Минимальная продолжительность сессии (мин)",
                    minDurationSession.getSleepDuration().toMinutes()
            );
        } else {
            return new SleepAnalysisResult(
                    "Минимальная длительность сессии сна",
                    "Не удалось определить"
            );
        }
    }
}
