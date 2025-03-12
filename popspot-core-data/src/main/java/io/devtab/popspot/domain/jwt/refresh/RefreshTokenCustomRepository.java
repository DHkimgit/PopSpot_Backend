package io.devtab.popspot.domain.jwt.refresh;

public interface RefreshTokenCustomRepository {

    void deleteAllByUserId(Integer userId);
}
