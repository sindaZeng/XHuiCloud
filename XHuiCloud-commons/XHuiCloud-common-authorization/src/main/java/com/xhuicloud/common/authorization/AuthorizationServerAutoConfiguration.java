package com.xhuicloud.common.authorization;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xhuicloud.common.authorization.jose.Jwks;
import com.xhuicloud.common.authorization.resource.utils.SecurityHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.security.KeyStore;
import java.security.KeyStoreException;

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
        RSAKey rsaKey;
        try {
            String alias = "xhuicloud";
            String storePass = "xhuicloud.cn";
            KeyStore jks = KeyStore.getInstance("jks");
            char[] pin = storePass.toCharArray();
            rsaKey = RSAKey.load(jks, alias, pin);
        } catch (Exception e) {
            rsaKey = Jwks.generateRsa();
        }
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

}
