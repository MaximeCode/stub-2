package ru.appline.logic.compass;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CompassModel implements Serializable {
    private static CompassModel instance;
    private final Set<Map<String, String>> directions = new HashSet<>();

    public CompassModel() {
    }

    public static CompassModel getInstance() {
        if (instance == null) {
            instance = new CompassModel();
        }
        return instance;
    }

    public Set<Map<String, String>> getDirections() {
        return directions;
    }

    public Map<String, String> getDirection(double degree) {
        Map<String, String> direction = new HashMap<>();
        // Преобразуем любой по величине и знаку угол в запросе к значению от 0 до 360.
        double convertedDegree = (degree % 360 + 360) % 360;
        // Для диапазона, внутри которого 360 переходит в 0, условие проверки иное, чем для других. Поэтому сначала
        // проверяем попадание заданного значения в остальные диапазоны. Если в них не попадает, то проверяем попадание
        // в диапазон, содержащий 0. Если в этот тоже не попадает, значит, диапазоны не заданы или заданы неверно.
        directions.stream()
                .filter(side -> convertedDegree >= getMin(side) && convertedDegree <= getMax(side))
                .map(side -> side.get("name"))
                .peek(sideName -> direction.put("Side", sideName))
                .findAny()
                .orElseGet(() -> directions.stream()
                        .filter(side -> getMin(side) > getMax(side))
                        .filter(side -> convertedDegree >= getMin(side) || convertedDegree <= getMax(side))
                        .map(side -> side.get("name"))
                        .peek(sideName -> direction.put("Side", sideName))
                        .findAny()
                        .orElseGet(() -> direction.put("Ошибка", (directions.isEmpty() ? "Не" : "Некорректно")
                                + " заданы дипазоны значений для компаса, задайте их через сервис /setDirections!")));
        return direction;
    }

    public void setDirections(Map<String, String> directions) {
        this.directions.clear();
        Map<String, String> direction = new HashMap<>();
        directions.forEach((k, v) -> {
            direction.put("name", k);
            direction.put("min", v.replaceFirst("-\\d+", ""));
            direction.put("max", v.replaceFirst("\\d+-", ""));
            this.directions.add(new HashMap<>(direction));
        });
    }

    private double getMin(Map<String, String> map) {
        return Double.parseDouble(map.get("min"));
    }

    private double getMax(Map<String, String> map) {
        return Double.parseDouble(map.get("max"));
    }
}
