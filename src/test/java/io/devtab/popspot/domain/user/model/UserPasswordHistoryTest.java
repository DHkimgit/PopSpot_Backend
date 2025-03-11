package io.devtab.popspot.domain.user.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.devtab.popspot.domain.user.exception.UserErrorException;

@ExtendWith(MockitoExtension.class)
class UserPasswordHistoryTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserPasswordHistory passwordHistory;

    @BeforeEach
    void setUp() {
        User user = mock(User.class);
        when(passwordEncoder.encode("notencodedpassword")).thenReturn("encodedpassword");
        passwordHistory = UserPasswordHistory.builder()
            .id(1)
            .user(user)
            .password(passwordEncoder.encode("notencodedpassword"))
            .build();
    }

    @Test
    @DisplayName("새 비밀번호가 이전 비밀번호와 다르면 검증에 성공한다")
    void 새_비밀번호가_이전에_사용했던_비밀번호와_다르다() {
        // given
        when(passwordEncoder.matches("encodedpassword", "newPassword")).thenReturn(false);

        assertDoesNotThrow(() -> passwordHistory.validateNewPasswordDiffersFromHistory("newPassword", passwordEncoder));
        verify(passwordEncoder).matches("encodedpassword", "newPassword");
    }

    @Test
    @DisplayName("새 비밀번호가 이전 비밀번호와 같으면 예외가 발생한다.")
    void 새_비밀번호가_이전에_사용했던_비밀번호와_같다() {
        when(passwordEncoder.matches("encodedpassword", "newPassword")).thenReturn(true);

        UserErrorException e = assertThrows(UserErrorException.class,
            () -> passwordHistory.validateNewPasswordDiffersFromHistory("newPassword", passwordEncoder));

        assertEquals("과거에 사용했던 비밀번호로 변경할 수 없습니다.", e.getExplainError());
    }
}
