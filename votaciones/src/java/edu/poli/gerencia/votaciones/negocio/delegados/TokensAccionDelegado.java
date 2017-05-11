package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.TokensAccion;
import edu.poli.gerencia.votaciones.modelo.dao.TokensAccionDAO;
import java.sql.Connection;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class TokensAccionDelegado extends GenericoDelegado<TokensAccion> {

    private final TokensAccionDAO tokensAccionDAO;

    public TokensAccionDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        tokensAccionDAO = new TokensAccionDAO(cnn);
        genericoDAO = tokensAccionDAO;
    }

}
