package network;

import authorization_lib.Account;
import exceptions.network_exception.NetworkException;
import goods.Request;
import goods.Response;
import gui.utilities.tools.ResourceManager;

public class Client {
     private static ClientTransporter transporter;
     public static void setTransporter(ClientTransporter transporter) {
         Client.transporter = transporter;
     }
     public synchronized static Response work(Request request) throws NetworkException {
         request.setLocale(ResourceManager.getLocale());
         return transporter.transport(request);
     }

     public static void setAccount(String username, String password, String token) {
         transporter.setAccount(new Account(username, password, token));
     }

     public static String getUsername() {
         return Client.transporter.getAccount().getUsername();
     }
     public static String getToken() {
         return Client.transporter.getAccount().getToken();
     }

}
