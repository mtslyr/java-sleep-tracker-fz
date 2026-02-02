package ru.yandex.practicum.sleeptracker.analysis.func;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;
import ru.yandex.practicum.sleeptracker.sleep.SleepType;

import java.util.List;

public class TypeSessionCountFunc implements SleepAnalysisFunction {

    private final SleepType type;

    private final String description;

    public TypeSessionCountFunc(SleepType type) {
        this.type = type;
        switch (type) {
            case NORMAL -> description = "нормальным";
            case BAD -> description = "плохим";
            case GOOD -> description = "хорошим";
            default -> description = "неопределенным";
        }
    }

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        Long count = sleepSessions.stream()
                .filter(s -> s.getType().equals(type))
                .count();

        return new SleepAnalysisResult(
                "Количество сессий с %s качество сна".formatted(description),
                count
        );
    }
}
