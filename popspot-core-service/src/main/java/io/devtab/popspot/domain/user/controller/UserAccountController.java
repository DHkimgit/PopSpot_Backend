package io.devtab.popspot.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.devtab.popspot.domain.user.api.UserAccountApi;
import io.devtab.popspot.domain.user.dto.UserAccountValidateDto;
import io.devtab.popspot.domain.user.dto.UserDeleteRequest;
import io.devtab.popspot.domain.user.dto.UserUpdateDto;
import io.devtab.popspot.domain.user.service.UserAccountService;
import io.devtab.popspot.domain.user.service.UserDeleteService;
import io.devtab.popspot.global.response.SuccessResponse;
import io.devtab.popspot.global.security.SecurityUserDetails;
import io.devtab.popspot.global.web.ipaddress.IpAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/me")
public class UserAccountController implements UserAccountApi {

    private final UserAccountService userAccountService;
    private final UserDeleteService userDeleteService;

    @GetMapping("/validate/nickname/{value}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> validateNickname(@PathVariable("value") String nickname) {
        var response = new UserAccountValidateDto.NicknameResponse(
            userAccountService.validateNickname(nickname));
        return ResponseEntity.ok(SuccessResponse.from(response));
    }

    @PatchMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(UserUpdateDto.PasswordRequest request, SecurityUserDetails userDetails) {
        userAccountService.updatePassword(userDetails.getUserId(), request.oldPassword(), request.newPassword());
        return ResponseEntity.ok(SuccessResponse.noContent());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUser(UserDeleteRequest request, SecurityUserDetails userDetails, @IpAddress String ipAddress) {
        userDeleteService.deleteUser(request, userDetails.getUserId(), ipAddress);
        return ResponseEntity.ok(SuccessResponse.noContent());
    }
}
