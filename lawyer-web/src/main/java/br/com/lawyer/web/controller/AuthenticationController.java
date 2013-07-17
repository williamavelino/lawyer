package br.com.lawyer.web.controller;

import br.com.lawyer.core.authentication.LawyerAuthenticationToken;
import br.com.lawyer.core.entity.Usuario;
import br.com.lawyer.core.util.LawyerStringUtils;
import br.com.lawyer.core.util.PasswordEncoder;
import br.com.lawyer.web.exception.RestException;
import br.com.lawyer.web.vo.AuthenticationVO;
import br.com.lawyer.web.vo.UsuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Deividi Cavarzan
 */
@Controller
public class AuthenticationController {


    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    /**
     * Esse é o mapeamento que o Spring Security redireciona quando o usuário acessa um contexto protegido por ele.
     * Foi criado esse mapeamento para a URL fica amigável, /auth, ao invés de /auth/login.html
     * @return {String} pagina de login
     */
    @RequestMapping(value = "/auth/**", method = RequestMethod.GET)
    public String auth() {
        return "authentication/index";
    }

    /**
     * Realiza a autenticação na pagina de login
     * @return {@link ResponseEntity} {@link UsuarioVO} com o status HTTP.
     */
    @RequestMapping(value = "/auth/authenticate", method = RequestMethod.POST)
    public ResponseEntity<UsuarioVO> authenticate(@RequestBody AuthenticationVO authenticationVO) {

        // Retorna 401 se der erro na autenticação.
        HttpStatus http = HttpStatus.UNAUTHORIZED;

        UsuarioVO usuario = null;

        if (!LawyerStringUtils.containStringBlank(authenticationVO.getEmail(), authenticationVO.getSenha())) {

            // Realiza o encode da senha.
            String password = PasswordEncoder.encodePassword(authenticationVO.getSenha(), authenticationVO.getEmail());

            Authentication auth = new LawyerAuthenticationToken(authenticationVO.getEmail(), password);
            //auth = authenticationManager.authenticate(auth);

            if (!auth.isAuthenticated()) {

                // Insere nosso token na sessão para ficar disponpivel para consulta.
                SecurityContextHolder.getContext().setAuthentication(auth);
                http = HttpStatus.OK;

                LawyerAuthenticationToken lawyerAuth = (LawyerAuthenticationToken) auth;

                // UsuarioVO para manter no cookie ou localStorage do browser. O AngularJS irá acessá-lo para criar o serviço de autenticação e permissões.
                usuario = new UsuarioVO();
                usuario.setEmail(lawyerAuth.getName());
                usuario.setToken(lawyerAuth.getToken());
                usuario.setAuthorities((List<GrantedAuthority>) lawyerAuth.getAuthorities());
            }
        }

        return new ResponseEntity<UsuarioVO>(usuario, http);
    }


}