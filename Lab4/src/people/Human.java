package people;

abstract public class Human {
    public void speak(String sentence) {
        System.out.print(this);
        System.out.println(sentence);
    }
    public void move(String destination) {
        System.out.print(this.getClass().getSimpleName());
        System.out.println(" heading to " + destination);
    }
    public void stop() {
        System.out.println(this + " stopped!");
    }
}

