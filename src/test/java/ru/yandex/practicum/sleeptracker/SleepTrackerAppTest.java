package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.analysis.func.*;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;
import ru.yandex.practicum.sleeptracker.sleep.SleepType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    static List<SleepSession> sleepSessions;
    static List<SleepSession> emptySessions;

    @BeforeAll
    public static void beforeAll() {
        LocalDateTime now = LocalDateTime.now();

        SleepSession session1 = new SleepSession(
                now,
                now.plusMinutes(60),
                Duration.ofMinutes(60),
                SleepType.NORMAL
        );

        SleepSession session2 = new SleepSession(
                now.plusHours(1),
                now.plusHours(1).plusMinutes(10),
                Duration.ofMinutes(10),
                SleepType.BAD
        );

        SleepSession session3 = new SleepSession(
                now.plusHours(2),
                now.plusHours(2).plusMinutes(90),
                Duration.ofMinutes(90),
                SleepType.GOOD
        );

        sleepSessions = List.of(session1, session2, session3);
        emptySessions = List.of();
    }

    @Test
    @DisplayName("Количество записей сессий сна: 3")
    public void shouldCountSleepSessions() {
        CountSleepSessionFunc func = new CountSleepSessionFunc();
        SleepAnalysisResult result = func.apply(sleepSessions);
        assertEquals(3, Integer.valueOf(result.getValueAsString()));
    }

    @Test
    @DisplayName("Количество записей в пустом списке: 0")
    public void shouldCountZeroSessions() {
        CountSleepSessionFunc func = new CountSleepSessionFunc();
        SleepAnalysisResult result = func.apply(emptySessions);
        assertEquals(0, Integer.valueOf(result.getValueAsString()));
    }

    @Test
    @DisplayName("Минимальная продолжительность сессии: 10 мин")
    public void shouldReturnMinimalSleepDuration() {
        MinSessionDurationFunc func = new MinSessionDurationFunc();
        SleepAnalysisResult result = func.apply(sleepSessions);
        assertEquals("10", result.getValueAsString());
    }

    @Test
    @DisplayName("Минимальная длительность в пустом списке")
    public void shouldHandleEmptyListForMinDuration() {
        MinSessionDurationFunc func = new MinSessionDurationFunc();
        SleepAnalysisResult result = func.apply(emptySessions);
        assertEquals("Не удалось определить", result.getValueAsString());
    }

    @Test
    @DisplayName("Максимальная продолжительность сессии: 90 мин")
    public void shouldReturnMaximalSleepDuration() {
        MaxSessionDurationFunc func = new MaxSessionDurationFunc();
        SleepAnalysisResult result = func.apply(sleepSessions);
        assertEquals("90", result.getValueAsString());
    }

    @Test
    @DisplayName("Максимальная длительность в пустом списке")
    public void shouldHandleEmptyListForMaxDuration() {
        MaxSessionDurationFunc func = new MaxSessionDurationFunc();
        SleepAnalysisResult result = func.apply(emptySessions);
        assertEquals("Не удалось определить", result.getValueAsString());
    }

    @Test
    @DisplayName("Средняя продолжительность сна: 53.33 мин")
    public void shouldCalculateAverageSleepDuration() {
        AverageSleepSessionFunc func = new AverageSleepSessionFunc();
        SleepAnalysisResult result = func.apply(sleepSessions);
        assertEquals(53.33, Double.parseDouble(result.getValueAsString()), 0.01);
    }

    @Test
    @DisplayName("Среднее значение для пустого списка")
    public void shouldHandleEmptyListForAverage() {
        AverageSleepSessionFunc func = new AverageSleepSessionFunc();
        SleepAnalysisResult result = func.apply(emptySessions);
        assertEquals("Не удалось определить", result.getValueAsString());
    }

    @Test
    @DisplayName("Количество сессий с нормальным качеством: 1")
    public void shouldCountNormalSleepSessions() {
        TypeSessionCountFunc func = new TypeSessionCountFunc(SleepType.NORMAL);
        SleepAnalysisResult result = func.apply(sleepSessions);
        assertEquals(1L, Long.valueOf(result.getValueAsString()));
    }

    @Test
    @DisplayName("Количество сессий с плохим качеством: 1")
    public void shouldCountBadSleepSessions() {
        TypeSessionCountFunc func = new TypeSessionCountFunc(SleepType.BAD);
        SleepAnalysisResult result = func.apply(sleepSessions);
        assertEquals(1L, Long.valueOf(result.getValueAsString()));
    }
}
