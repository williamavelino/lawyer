package br.com.lawyer.web.api;

import br.com.lawyer.core.entity.common.Municipio;
import br.com.lawyer.web.annotation.ApiController;
import br.com.lawyer.web.delegate.MunicipioDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Deividi
 * @since 07/10/2013
 */
@ApiController
public class MunicipioController {

    @Autowired
    private MunicipioDelegate municipioDelegate;

    @RequestMapping (value = "/municipios", method = RequestMethod.GET)
    @ResponseBody
    public Page<Municipio> findByQuery(
                            @RequestParam(value = "q", required = false) String query,
                            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                            @RequestParam(value = "limit", defaultValue = "25", required = false) int limit) {
        return municipioDelegate.buscaPorNome(query, page, limit);
    }
}
