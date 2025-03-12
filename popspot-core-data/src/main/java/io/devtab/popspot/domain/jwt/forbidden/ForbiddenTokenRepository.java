package io.devtab.popspot.domain.jwt.forbidden;

import org.springframework.data.repository.CrudRepository;

public interface ForbiddenTokenRepository extends CrudRepository<ForbiddenTokenEntity, String> {

}
