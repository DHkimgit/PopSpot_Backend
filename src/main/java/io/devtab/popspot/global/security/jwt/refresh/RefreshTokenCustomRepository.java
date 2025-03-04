package io.devtab.popspot.global.security.jwt.refresh;

public interface RefreshTokenCustomRepository {

    void deleteAllByUserId(Integer userId);
}
