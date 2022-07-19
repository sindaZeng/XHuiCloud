package com.xhuicloud.common.authorization;

import cn.hutool.core.util.ObjectUtil;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xhuicloud.common.authorization.jose.Jwks;
import com.xhuicloud.common.authorization.resource.properties.SecurityProperties;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.KeyStore;

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
    public JWKSource<SecurityContext> jwkSource(SecurityProperties securityProperties) {
        RSAKey rsaKey = null;
        SecurityProperties.Jwk jwk = securityProperties.getAuthorization().getJwk();
        if (ObjectUtil.isAllNotEmpty(jwk)) {
            try {
                char[] pin = jwk.getStorePass().toCharArray();
                KeyStore jks = KeyStore.getInstance(jwk.getKeyStore());
                jks.load(new ClassPathResource(jwk.getKeyPath()).getInputStream(), pin);
                rsaKey = RSAKey.load(jks, jwk.getAlias(), pin);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (rsaKey == null){
            rsaKey = Jwks.generateRsa();
        }
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

}
