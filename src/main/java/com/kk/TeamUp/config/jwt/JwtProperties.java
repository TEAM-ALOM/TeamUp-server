package com.kk.TeamUp.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt") //자바 클래스에 property 값 가져와서 사용하는 annotation
public class JwtProperties {
    public String issuer;
    public String secretKey;
}
