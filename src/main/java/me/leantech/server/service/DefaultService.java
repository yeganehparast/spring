package me.leantech.server.service;

import java.util.List;

/**
 * A base for service interfaces
 */
public interface DefaultService<R> {

    void save(R t);

    List<R> getAll();

    R findById(Long id);

}
