package app;

public class DTO {
    private String name;
    private int level;

    public DTO(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }
}
