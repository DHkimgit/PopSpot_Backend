package io.devtab.popspot.global.security.jwt.refresh;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String>, RefreshTokenCustomRepository{
}
