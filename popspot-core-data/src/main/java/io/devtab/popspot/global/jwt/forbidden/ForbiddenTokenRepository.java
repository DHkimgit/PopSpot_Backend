package io.devtab.popspot.global.jwt.forbidden;

import org.springframework.data.repository.CrudRepository;

public interface ForbiddenTokenRepository extends CrudRepository<ForbiddenTokenEntity, String> {

}
