package io.devtab.popspot.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.devtab.popspot.domain.user.api.UserAccountApi;
import io.devtab.popspot.domain.user.dto.UserUpdateDto;
import io.devtab.popspot.domain.user.service.UserAccountService;
import io.devtab.popspot.global.response.SuccessResponse;
import io.devtab.popspot.global.security.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/me")
public class UserAccountController implements UserAccountApi {

    private final UserAccountService userAccountService;

    @PatchMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(UserUpdateDto.PasswordRequest request, SecurityUserDetails userDetails) {
        log.debug("히히");
        userAccountService.updatePassword(userDetails.getUserId(), request.oldPassword(), request.newPassword());
        log.debug("헤헤");
        return ResponseEntity.ok(SuccessResponse.noContent());
    }
}
