package me.leantech.server.dataaccessobject;

import com.google.common.collect.Sets;
import me.leantech.server.datatransferobject.TokenDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Token Data Access Object implementation
 */
@Repository
public class TokenDAOImpl implements TokenDAO {

    private Set<TokenDTO> data = Sets.newTreeSet((e1, e2) -> e1.hashCode() - e2.hashCode());

    @Override
    public void save(TokenDTO tokenDTO) {
        data.add(tokenDTO);
    }

    @Override
    public List<TokenDTO> getAll() {
        return data.stream().collect(Collectors.toList());
    }

    @Override
    public TokenDTO find(String token) {

        return data
                .stream()
                .filter(tokenDTO -> tokenDTO.getToken().equals(token))
                .findFirst()
                .orElseGet(() -> new TokenDTO(""));
    }
}
