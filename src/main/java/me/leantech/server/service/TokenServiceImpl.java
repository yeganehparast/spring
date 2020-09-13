package me.leantech.server.service;

import me.leantech.server.dataaccessobject.TokenDAO;
import me.leantech.server.datatransferobject.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private TokenDAO tokenDAO;

    @Autowired
    public TokenServiceImpl(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @Override
    public void save(TokenDTO tokenDTO) {
        tokenDAO.save(tokenDTO);
    }

    @Override
    public List<TokenDTO> getAll() {
        return tokenDAO.getAll();
    }

    @Override
    public TokenDTO find(String token) {
        return tokenDAO.find(token);
    }

    @Override
    public TokenDTO findById(Long id) {
        return new TokenDTO();
    }
}
