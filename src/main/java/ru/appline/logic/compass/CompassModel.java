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

    public Map<String, String> getDirection(int degree) {
        Map<String, String> direction = new HashMap<>();
        if (directions.isEmpty()) {
            return null;
        }
        direction.put("Side", directions.stream()
                .filter(e -> Integer.parseInt(e.get("min")) <= degree % 360)
                .filter(e -> Integer.parseInt(e.get("max")) >= degree % 360)
                .map(e -> e.get("name"))
                .findFirst()
                .orElse("North"));
        return direction;
    }

    public void setDirections(Map<String, String> directions) {
        Map<String, String> direction = new HashMap<>();
        directions.forEach((k, v) -> {
            direction.put("name", k);
            direction.put("min", v.replaceFirst("-\\d+", ""));
            direction.put("max", v.replaceFirst("\\d+-", ""));
            this.directions.add(new HashMap<>(direction));
        });
    }
}
