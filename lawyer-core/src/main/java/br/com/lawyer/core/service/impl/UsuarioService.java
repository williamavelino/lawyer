package br.com.lawyer.core.service.impl;

import br.com.lawyer.core.authentication.LawyerAuthenticationToken;
import br.com.lawyer.core.base.BaseService;
import br.com.lawyer.core.entity.Usuario;
import br.com.lawyer.core.exception.BusinessException;
import br.com.lawyer.core.repository.IUsuarioRepository;
import br.com.lawyer.core.service.IUsuarioService;
import br.com.lawyer.core.util.LawyerStringUtils;
import br.com.lawyer.core.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Deividi Cavarzan
 */
@SuppressWarnings ("ALL")
@Service
public class UsuarioService extends BaseService<String, Usuario, IUsuarioRepository> implements IUsuarioService {

    /**
     * Construtor
     *
     * @param repository - DAO que será utilizado referente a entidade Usuario
     */
    @Autowired
    public UsuarioService (IUsuarioRepository repository) {
        super(repository);
    }

    /**
     * Realiza a autenticação do usuario com base na entidade informada e no manager que implementa a regra de autenticação.
     * Qualquer exceção de negocio na autenticação lança uma {@link BadCredentialsException} em runtime.
     *
     * @param user
     * @param manager
     * @return
     * @throws BusinessException
     * @throws BadCredentialsException
     */
    @Override
    public Usuario authenticate (Usuario user, AuthenticationManager manager) {

        if (LawyerStringUtils.containStringBlank(user.getEmail(), user.getSenha())) {
            throw new BadCredentialsException("Usuario / Senha nao informado.");
        }

        String password = PasswordEncoder.encodePassword(user.getSenha(), user.getEmail());

        Usuario usuario = null;
        try {
            usuario = getRepository().findByEmail(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LawyerAuthenticationToken auth = new LawyerAuthenticationToken(user.getEmail(), password, usuario);
        auth = (LawyerAuthenticationToken) manager.authenticate(auth);

        if (auth.isAuthenticated()) {

            // Insere nosso token na sess�o para ficar disponpivel para consulta.
            SecurityContextHolder.getContext().setAuthentication(auth);

        } else {
            throw new BadCredentialsException("Usuário não foi autenticado.");
        }

        return usuario;
    }

    @Override
    public Usuario atualizarUsuario (Usuario usuario, String uid) throws BusinessException {

        if (LawyerStringUtils.isBlank(uid) || LawyerStringUtils.isBlank(usuario.getUid())) {
            throw new BusinessException("Usuário deve ser informado!");
        }

        if (!getRepository().exists(uid)) {
            throw new BusinessException("Entidade informada não existe.");
        }

        if (uid.equals(usuario.getUid())) {
            return getRepository().save(usuario);
        }

        return null;
    }


}