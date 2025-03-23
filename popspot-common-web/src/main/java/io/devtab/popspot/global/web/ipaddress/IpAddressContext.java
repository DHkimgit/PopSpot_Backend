package io.devtab.popspot.global.web.ipaddress;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@RequestScope
public class IpAddressContext {

    private String ipAddress;
}
