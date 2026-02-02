package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.analysis.func.*;
import ru.yandex.practicum.sleeptracker.sleep.SleepSession;
import ru.yandex.practicum.sleeptracker.sleep.SleepType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class SleepTrackerApp {
    static Path logFile;
    static List<SleepSession> sleepSessions;
    static List<SleepAnalysisFunction> functions = new LinkedList<>();
    public static void main(String[] args) {
        logFile = Paths.get(args[0]);
        addFunctions();
        initSleepSessions();
        runFunctions();
    }

    public static void addFunctions() {
        functions.add(new CountSleepSessionFunc());
        functions.add(new MinSessionDurationFunc());
        functions.add(new MaxSessionDurationFunc());
        functions.add(new AverageSleepSessionFunc());
        functions.add(new TypeSessionCountFunc(SleepType.BAD));
        functions.add(new SleeplessNightsFunc());
    }

    public static void initSleepSessions() {
        try (BufferedReader br = Files.newBufferedReader(logFile.toAbsolutePath()) ) {
            sleepSessions = br.lines().map(SleepSession::new).toList();
        } catch (IOException e) {
            System.out.println("Лог файл не найден в директории 'src/main/resources'");
        }
    }

    public static void runFunctions() {
        functions.forEach(func -> {
            func.apply(sleepSessions).printResult();
        });
    }
}