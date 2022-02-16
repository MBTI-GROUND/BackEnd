package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Auth.SecurityUtil;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 테스트")
class UserServiceTest {

    @Mock private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;

    @BeforeEach
    void setup(){
        UserEntity userEntity = UserEntity.builder()
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
    @WithMockUser
    @DisplayName("내 정보를 가져온다.")
    void getMyInfo() {

    }
}