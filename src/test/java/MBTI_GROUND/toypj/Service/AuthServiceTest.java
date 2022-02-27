package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Auth.TokenProvider;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.RefreshTokenRepository;
import MBTI_GROUND.toypj.Repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService 테스트")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private AuthenticationManagerBuilder authenticationManagerBuilder;

    private PasswordEncoder passwordEncoder;

    private TokenProvider tokenProvider;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    private UserEntity userEntity;


    @Test
    void signup() {


    }

    @Test
    void login() {
    }

    @Test
    void reissue() {
    }
}