package iostream;

import goods.Response;
import printer_options.RainbowPrinter;

import java.util.List;


public class Renderer {
    public Renderer() {
        RainbowPrinter.printInfo("Hello! Welcome to my Application!");
        RainbowPrinter.printInfo("Please type 'help' to read the instructions or type 'exit' to exit.");
    }
    public void printResponse(Response response) {
        if(response != null) {
            List<String> result = response.getResult();
            if(result != null) result.forEach(RainbowPrinter::printResult);
            List<String> notice = response.getNotice();
            if(notice != null) notice.forEach(RainbowPrinter::printInfo);
        } else {
            RainbowPrinter.printError("System crashed!!!");
        }
    }
}
