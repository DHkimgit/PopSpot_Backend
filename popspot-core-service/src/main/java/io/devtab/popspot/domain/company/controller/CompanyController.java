package io.devtab.popspot.domain.company.controller;

import static io.devtab.popspot.domain.company.dto.CompanyRequest.BusinessNumberCheck.validateAndReturnCommand;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.devtab.popspot.domain.company.api.CompanyApi;
import io.devtab.popspot.domain.company.dto.CompanyRequest;
import io.devtab.popspot.domain.company.service.CompanyService;
import io.devtab.popspot.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    @PostMapping("/register")
    @PreAuthorize("permitAll() or isAnonymous()")
    public ResponseEntity<?> register(CompanyRequest.Register request) {
        companyService.registerCompany(request.toCommand());
        return ResponseEntity.ok(SuccessResponse.noContent());
    }

    @GetMapping("/check/business-number/{value}")
    @PreAuthorize("permitAll() or isAnonymous()")
    public ResponseEntity<?> checkBusinessNumber(@PathVariable("value") String businessNumber) {
        Boolean result = companyService.checkBusinessNumber(validateAndReturnCommand(businessNumber));
        return ResponseEntity.ok(SuccessResponse.from("is_duplicated", result));
    }
}
