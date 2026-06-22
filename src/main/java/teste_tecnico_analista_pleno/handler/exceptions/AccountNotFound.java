package teste_tecnico_analista_pleno.handler.exceptions;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(String account) {
        super("Account not found: " + account);
    }

}
