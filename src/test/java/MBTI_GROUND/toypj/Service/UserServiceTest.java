package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 테스트")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;

    @BeforeEach
    void setup() {
        userEntity = UserEntity.builder()
                .email("hose0728@naver.com")
                .mbti("ENTJ")
                .nickname("홋메")
                .password("test")
                .phoneNumber("01047372086")
                .build();

    }

    @Test
    @DisplayName("유저 정보를 가져온다.")
    void getUserInfo() {
        //given
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(userEntity));


        //when
        assert userEntity != null;
        UserResponseDto result = userService.getUserInfo(userEntity.getEmail());

        //then
        assertEquals(result.getEmail(), userEntity.getEmail());

    }

    @Test
    @DisplayName("내 정보를 가져온다.")
    void getMyInfo() {
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userEntity.getEmail(), userEntity.getPassword(), AuthorityUtils.NO_AUTHORITIES);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(userEntity));

        //when
        UserResponseDto myInfo = userService.getMyInfo();
        //then
        assertEquals(myInfo.getEmail(), userEntity.getEmail());

    }
}