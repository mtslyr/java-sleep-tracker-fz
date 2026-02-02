package ru.yandex.practicum.sleeptracker.analysis.func;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;

public class AverageSleepSessionFunc implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        OptionalDouble avg = sleepSessions.stream()
                .map(SleepSession::getSleepDuration)
                .mapToLong(Duration::toMinutes)
                .average();

        if (avg.isPresent()) {
            return new SleepAnalysisResult(
                    "Средняя продолжительность сна (мин)",
                    avg.getAsDouble()
            );
        } else {
            return new SleepAnalysisResult(
                    "Средняя продолжительность сна (мин)",
                    "Не удалось определить"
            );
        }
    }
}
