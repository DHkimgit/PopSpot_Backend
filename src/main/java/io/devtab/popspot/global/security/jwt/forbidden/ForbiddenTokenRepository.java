package io.devtab.popspot.global.security.jwt.forbidden;

import org.springframework.data.repository.CrudRepository;

public interface ForbiddenTokenRepository extends CrudRepository<ForbiddenTokenEntity, String> {

}
