package com.xhuicloud.common.authorization;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xhuicloud.common.authorization.jose.Jwks;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class AuthorizationServerAutoConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return SecurityHolder.passwordEncoder();
    }

    /**
     * keytool -genkey -alias xhuicloud  -keyalg RSA -storetype PKCS12 -keysize 2048 -validity 365 -keystore xhuicloud.jks -storepass xhuicloud.cn  -dname "CN=(Sinda), OU=(xhuicloud), O=(xhuicloud), L=(gz), ST=(gd), C=(cn)"
     *
     * keytool -export -alias xhuicloud -keystore xhuicloud.jks  -file pub.cer
     *
     * @return
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        return (jwkSelector, securityContext) -> jwkSelector.select(new JWKSet(rsaKey));
    }

}
