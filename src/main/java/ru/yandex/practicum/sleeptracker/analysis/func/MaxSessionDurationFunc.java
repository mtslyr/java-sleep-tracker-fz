package ru.yandex.practicum.sleeptracker.analysis.func;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MaxSessionDurationFunc implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        Optional<SleepSession> min = sleepSessions
                .stream()
                .max(Comparator.comparing(SleepSession::getSleepDuration));

        if (min.isPresent()) {
            SleepSession minDurationSession = min.get();
            return new SleepAnalysisResult(
                    "Максимальная продолжительность сессии (мин)",
                    minDurationSession.getSleepDuration().toMinutes()
            );
        } else {
            return new SleepAnalysisResult(
                    "Максимальная длительность сессии сна",
                    "Не удалось определить"
            );
        }
    }
}
