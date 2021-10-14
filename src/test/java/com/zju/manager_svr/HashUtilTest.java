package com.zju.manager_svr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zju.manager_svr.util.HashUtil;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashUtilTest {

    @ParameterizedTest
    @CsvSource({
            "123456,pbkdf2:sha256:260000$hxymrVhMaA4CszrW$460d382eef1ba3fe27e34520ae4a0f9e3ab7b4b6c6bdb26133f771d7b57e9450",
            "string,pbkdf2:sha256:260000$ygNNi7PGWBbb6QT1$82c85a39863313c75d6da0921f22d19ba501df387e9a7bdc30535ab4942109e9" })
    public void passwordCheckTest(String password, String expected) {
        assertTrue(HashUtil.verification(password, expected));
    }
}
