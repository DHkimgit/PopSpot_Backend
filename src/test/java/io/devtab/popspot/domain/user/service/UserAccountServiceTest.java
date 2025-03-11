package io.devtab.popspot.domain.user.service;

import static io.devtab.popspot.domain.user.exception.UserErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.devtab.popspot.domain.user.exception.UserErrorException;
import io.devtab.popspot.domain.user.model.User;
import io.devtab.popspot.domain.user.model.UserPasswordHistory;
import io.devtab.popspot.domain.user.service.implement.PasswordHistoryAppender;
import io.devtab.popspot.domain.user.service.implement.PasswordHistoryGetter;
import io.devtab.popspot.domain.user.service.implement.UserGetter;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserGetter userGetter;

    @Mock
    private PasswordHistoryGetter passwordHistoryGetter;

    @Mock
    private PasswordHistoryAppender passwordHistoryAppender;

    @Mock
    private User user;

    @Test
    @DisplayName("조건에 맞는 새 비밀번호로 비밀번호를 변경한다")
    void 조건에_맞는_새_비밀번호로_비밀번호를_변경한다() {
        // given
        Integer userId = 1;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserPasswordHistory newHistory = mock(UserPasswordHistory.class);

        when(userGetter.read(userId)).thenReturn(user);
        when(passwordHistoryGetter.getLatestPasswordHistory(userId)).thenReturn(Optional.empty());
        when(user.updatePassword(oldPassword, newPassword, passwordEncoder, null)).thenReturn(newHistory);

        // when
        assertDoesNotThrow(() -> userAccountService.updatePassword(userId, oldPassword, newPassword));

        // then
        verify(userGetter).read(userId);
        verify(passwordHistoryGetter).getLatestPasswordHistory(userId);
        verify(user).updatePassword(oldPassword, newPassword, passwordEncoder, null);
        verify(passwordHistoryAppender).save(newHistory);
    }

    @Test
    @DisplayName("새 비밀번호가 이전에 사용된 비밀번호와 동일하면 예외가 발생한다")
    void 새_비밀번호가_이전에_사용된_비밀번호와_동일하면_예외가_발생한다() {
        // given
        Integer userId = 1;
        String oldPassword = "oldPassword";
        String newPassword = "formerPassword";
        UserPasswordHistory history = mock(UserPasswordHistory.class);

        when(userGetter.read(userId)).thenReturn(user);
        when(passwordHistoryGetter.getLatestPasswordHistory(userId)).thenReturn(Optional.of(history));
        when(user.updatePassword(oldPassword, newPassword, passwordEncoder, history))
            .thenThrow(new UserErrorException(IS_FORMER_PASSWORD));

        // when & then
        assertThrows(UserErrorException.class, () -> userAccountService.updatePassword(userId, oldPassword, newPassword));
        verify(userGetter).read(userId);
        verify(passwordHistoryGetter).getLatestPasswordHistory(userId);
        verify(user).updatePassword(oldPassword, newPassword, passwordEncoder, history);
        verify(passwordHistoryAppender, never()).save(any());
    }
}
