package transports;

public abstract class Transports {
    public abstract void getDescribe();
    public abstract void ringTheBell(String notice);
    public abstract void emitTheSmoke();
    public abstract void move(String destination);
}
