package transports;

public interface ITransActions {
    void getDescribe();
    void ringTheBell(String notice);
    void emitTheSmoke();
    void move(String destination);
}
