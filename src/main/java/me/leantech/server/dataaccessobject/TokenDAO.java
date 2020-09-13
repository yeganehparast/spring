package me.leantech.server.dataaccessobject;

import me.leantech.server.datatransferobject.TokenDTO;

import java.util.List;

/**
 * Token Data Access Object
 */
public interface TokenDAO {

    void save(TokenDTO tokenDTO);

    List<TokenDTO> getAll();

    TokenDTO find(String token);
}
