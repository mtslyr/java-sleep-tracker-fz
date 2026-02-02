package ru.yandex.practicum.sleeptracker.analysis.func;


import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;

import java.util.List;

public class CountSleepSessionFunc implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        Integer count = sleepSessions.size();
        return new SleepAnalysisResult(
                "Количество сессий сна",
                count
        );
    }
}
