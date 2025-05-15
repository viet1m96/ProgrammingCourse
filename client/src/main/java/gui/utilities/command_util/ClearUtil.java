package gui.utilities.command_util;

import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import gui.utilities.tools.AlertUtil;
import network.Client;

public class ClearUtil {
    public ClearUtil() {}

    public void handleRequest() {
        Request request = new Request("clear", null, Client.getToken());
        try {
            Response response = Client.work(request);
            AlertUtil.showResultAlert(response);
        } catch (NetworkException e) {
            AlertUtil.showErrorAlert("Network Error", e.toString());
        }
    }
}
