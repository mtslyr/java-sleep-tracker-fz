package ru.yandex.practicum.sleeptracker.analysis.func;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;

import java.util.List;
import java.util.function.Function;

public interface SleepAnalysisFunction extends Function<List<SleepSession>, SleepAnalysisResult> {
}
