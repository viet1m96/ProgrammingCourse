package iostream;

import goods.Response;
import printer_options.RainbowPrinter;

import java.util.List;


public class Renderer {
    public Renderer() {
        RainbowPrinter.printInfo("Hello! Welcome to my Application!");
        RainbowPrinter.printInfo("Please type 'sign_in' to sign in or 'sign_up' if you dont have an account or exit if you want to exit.");
    }
    public void printNotice(Response response) {
        if(response != null) {
            List<String> notice = response.getNotice();
            for(String str : notice) {
                RainbowPrinter.printInfo(str);
            }
        } else {
            RainbowPrinter.printError("System Crashed!!!");
        }
    }

    public void printResult(Response response) {
        if(response != null) {
            List<String> result = response.getResult();
            if(result != null) result.forEach(RainbowPrinter::printResult);
        } else {
            RainbowPrinter.printError("System Crashed!!!");
        }
    }
}
