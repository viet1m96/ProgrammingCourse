package authorization_lib;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.AlreadySignInException;
import exceptions.user_exceptions.NotSignInException;
import input_validators.InputPartition;
import iostream.Renderer;
import network.ClientTransporter;

public class AuthorizationChecker {
    private AuthManager authManager;
    public AuthorizationChecker() {}

    public void init() {
        authManager = new AuthManager();
        authManager.init();
    }

    public boolean checkAuthorization(Account account, ClientTransporter transporter, Renderer renderer, String inp) throws NotSignInException, AlreadySignInException, LogException, NetworkException {
        String cmd = InputPartition.part1st(inp);
        if(account.getToken() == null || account.getToken().isEmpty()) {
            if(cmd.equals("sign_in") | cmd.equals("sign_up")) {
                Account providedAccount = authManager.call(cmd, transporter, renderer);
                if(providedAccount != null) {
                    account.setUsername(providedAccount.getUsername());
                    account.setPassword(providedAccount.getPassword());
                    account.setToken(providedAccount.getToken());
                }
                return false;
            } else {
                throw new NotSignInException();
            }
        } else {
            if(cmd.equals("sign_in") | cmd.equals("sign_up")) throw new AlreadySignInException();
        }
        return true;
    }
}
