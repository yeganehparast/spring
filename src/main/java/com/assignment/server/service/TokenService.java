package com.assignment.server.service;

import com.assignment.server.datatransferobject.TokenDTO;

import java.util.List;

/**
 * Token service which relates the TokenDAO and other controllers
 */
public interface TokenService extends DefaultService<TokenDTO> {

    void save(TokenDTO tokenDTO);

    List<TokenDTO> getAll();

    TokenDTO find(String token);
}
