package iostream;

public class Main {
    public static void main(String[] args) {
        String filePath = System.getenv("file_name");
        Handler handler = new Handler();
        handler.prepare(filePath);
        //handler.prepare("/home/cun/IdeaProjects/Lab5/src/main/resources/data.csv");
        handler.run();
    }
}