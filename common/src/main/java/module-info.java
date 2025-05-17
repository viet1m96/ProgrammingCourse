module common {
    requires io.github.cdimascio.dotenv.java;
    exports exceptions.log_exceptions;
    exports goods;
    exports exceptions.network_exception;
    exports main_objects;
    opens main_objects;
    exports enums;
    exports utilities;
    exports exceptions.user_exceptions;
}