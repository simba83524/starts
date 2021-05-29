package com.simba.auth.config;

import cn.hutool.core.codec.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.keygen.KeyGenerators;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author chenjun
 * @date 2021-05-24
 * @time 9:48
 * @Description: key配置
 */
public class KeyConfig {

    private static final String KEY_STORE_FILE = "starts.jks";
    private static final String KEY_STORE_PASSWORD = "1983524";
    private static final String KEY_ALIAS = "starts";
    private static KeyStoreKeyFactory KEY_STORE_KEY_FACTORY = new KeyStoreKeyFactory(
            new ClassPathResource(KEY_STORE_FILE), KEY_STORE_PASSWORD.toCharArray());

    static final String VERIFIER_KEY_ID = new String(Base64.encode(KeyGenerators.secureRandom(32).generateKey()));

    public static RSAPublicKey getVerifierKey() {
        return (RSAPublicKey) getKeyPair().getPublic();
    }

    static RSAPrivateKey getSignerKey() {
        return (RSAPrivateKey) getKeyPair().getPrivate();
    }

    private static KeyPair getKeyPair() {
        return KEY_STORE_KEY_FACTORY.getKeyPair(KEY_ALIAS);
    }
}
