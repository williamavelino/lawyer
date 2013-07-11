package br.com.lawyer.core.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.UUID;

/**
 * Classe para gerar a senha que será utilizada para salvar ou consultar na base de dados.
 * Gera o Hash para comparar a senha no banco
 * Gera um token para recuperação de senha quando o usuário esquecer, que sera enviado via email provavelmente.
 *
 */
public class PasswordEncoder {

    private static String salt = ":Law-|:.:|-yeR:";

    /**
     * @param password
     * @param login
     * @return Senha criptografada.
     */
    public static String encodePassword(String password, String login) {
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder(salt+login);
        return standardPasswordEncoder.encode(password);
    }

    /**
     * @param login
     * @return Token para gerar a nova senha.
     */
    public static String generateTokenForPassword(String login) {
        return new ShaPasswordEncoder(256).encodePassword(UUID.randomUUID().toString(), salt+login);
    }

}