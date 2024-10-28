package animals;

public record Animal (String name, String action) {
    public void move() {
        System.out.println(name + action);
    }
}
