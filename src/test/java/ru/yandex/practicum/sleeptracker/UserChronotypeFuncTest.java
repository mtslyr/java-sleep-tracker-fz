package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.analysis.func.UserChronotypeFunc;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;
import ru.yandex.practicum.sleeptracker.sleep.SleepType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserChronotypeFuncTest {

    private final UserChronotypeFunc func = new UserChronotypeFunc();

    @Test
    @DisplayName("Определение жаворонка (засыпание < 22:00, пробуждение < 07:00)")
    public void shouldIdentifyLark() {
        List<SleepSession> sessions = List.of(
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 21, 30),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        Duration.ofHours(9), SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals("Жаворонок", result.getValue());
    }

    @Test
    @DisplayName("Определение совы (засыпание > 23:00, пробуждение > 09:00)")
    public void shouldIdentifyOwl() {
        List<SleepSession> sessions = List.of(
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        Duration.ofHours(10), SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals("Сова", result.getValue());
    }

    @Test
    @DisplayName("Определение голубя (промежуточный случай)")
    public void shouldIdentifyPigeon() {
        List<SleepSession> sessions = List.of(
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        Duration.ofHours(7), SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals("Голубь", result.getValue());
    }

    @Test
    @DisplayName("Равное количество типов — выбирается голубь")
    public void shouldReturnPigeonWhenCountsAreEqual() {
        List<SleepSession> sessions = List.of(
                // Жаворонок
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Duration.ofHours(9), SleepType.NORMAL
                ),
                // Сова
                new SleepSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 10, 0),
                        Duration.ofHours(10), SleepType.NORMAL
                )
        );

        SleepAnalysisResult result = func.apply(sessions);
        assertEquals("Голубь", result.getValue());
    }
}
