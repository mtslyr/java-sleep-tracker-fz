package ru.yandex.practicum.sleeptracker.analysis;

public class SleepAnalysisResult {
    private final String description;
    private final Object value;

    public SleepAnalysisResult(String description, Object value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        return value;
    }

    public String getValueAsString() {
        return value.toString();
    }

    public void printResult() {
        System.out.println();
        System.out.println("*".repeat(50));
        System.out.println("ФУНКЦИЯ: %s".formatted(this.description));
        System.out.println("ЗНАЧЕНИЕ: %s".formatted(getValueAsString()));
        System.out.println("*".repeat(50));
    }

    @Override
    public String toString() {
        return "SleepAnalysisResult{" +
                "description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}
