package com.assignment.server.dataaccessobject;

import com.assignment.server.datatransferobject.TokenDTO;

import java.util.List;

/**
 * Token Data Access Object
 */
public interface TokenDAO {

    void save(TokenDTO tokenDTO);

    List<TokenDTO> getAll();

    TokenDTO find(String token);
}
