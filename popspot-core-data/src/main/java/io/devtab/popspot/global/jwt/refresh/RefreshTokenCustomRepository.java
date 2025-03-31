package io.devtab.popspot.global.jwt.refresh;

public interface RefreshTokenCustomRepository {

    void deleteAllByUserId(Integer userId);
}
