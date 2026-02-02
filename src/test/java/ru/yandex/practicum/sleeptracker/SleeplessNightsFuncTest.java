package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.analysis.func.SleeplessNightsFunc;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;
import ru.yandex.practicum.sleeptracker.sleep.SleepType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsFuncTest {

    private final SleeplessNightsFunc func = new SleeplessNightsFunc();

    @Test
    @DisplayName("Первая сессия до 12:00 — проверка текущей ночи")
    public void shouldCheckCurrentNightIfStartBeforeNoon() {
        List<SleepSession> sessions = List.of(
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        LocalDateTime.of(2025, 10, 1, 13, 0),
                        Duration.ofHours(2),
                        SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals(1, result.getValue());
    }

    @Test
    @DisplayName("Одна сессия дневного сна после 12:00 – 0 бессонных ночей")
    public void shouldCheckNextNightIfStartAfterNoon() {
        List<SleepSession> sessions = List.of(
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 13, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        Duration.ofHours(2),
                        SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals(0, result.getValue());
    }

    @Test
    @DisplayName("Сон на границе интервала 00:00-06:00")
    public void shouldNotBeSleeplessWhenSleepIntersectsBoundary() {
        List<SleepSession> sessions = List.of(
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 5, 59),
                        LocalDateTime.of(2025, 10, 1, 8, 0),
                        Duration.ofMinutes(121),
                        SleepType.NORMAL
                ),
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 0, 1),
                        Duration.ofMinutes(61),
                        SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals(0, result.getValue());
    }

    @Test
    @DisplayName("Длительный период без ночного сна")
    public void shouldCountAllNightsInPeriodAsSleepless() {
        List<SleepSession> sessions = List.of(
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 8, 0),
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        Duration.ofHours(2),
                        SleepType.NORMAL
                ),
                new SleepSession(
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 16, 0),
                        Duration.ofHours(2),
                        SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals(3, result.getValue());
    }
}
