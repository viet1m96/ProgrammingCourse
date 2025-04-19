package config;
import exceptions.log_exceptions.EnvNotExistsException;
import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {
    private static Dotenv dotenv;
    public static void init() {
        String dirPath = System.getenv("DIR");
        String fileName = System.getenv("FILE_NAME");
        //dotenv = Dotenv.configure().directory(dirPath).filename(fileName).load();
        dotenv = Dotenv.configure().directory("/home/cun/IdeaProjects/Lab7_v2").filename("config_file.env").load();
    }
    public static String getJwtSecret() throws EnvNotExistsException {
        String secret = dotenv.get("JWT_SECRET");
        if (secret == null) {
            throw new EnvNotExistsException("JWT_SECRET not found");
        }
        return secret;
    }

    public static String getDatabase_user() throws EnvNotExistsException {
        String username = dotenv.get("DATABASE_USERNAME");
        if (username == null) {
            throw new EnvNotExistsException("DATABASE_USERNAME not set");
        }
        return username;
    }

    public static String getDatabase_pass() throws EnvNotExistsException {
        String password = dotenv.get("DATABASE_PASSWORD");
        if (password == null) {
            throw new EnvNotExistsException("DATABASE_PASSWORD not set");
        }
        return password;
    }

    public static String getDatabase_url() throws EnvNotExistsException {
        String url = dotenv.get("DATABASE_URL");
        if (url == null) {
            throw new EnvNotExistsException("DATABASE_URL not set");
        }
        return url;
    }


}
