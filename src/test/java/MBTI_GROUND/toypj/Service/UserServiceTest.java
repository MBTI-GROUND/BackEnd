package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Dto.UserResponseDto;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void setup(){
        UserEntity userEntity = UserEntity.builder()
                .email("hose0728@naver.com")
                .mbti("ENTJ")
                .nickname("홋메")
                .password("test")
                .phoneNumber("01047372086")
                .build();
        userRepository.save(userEntity);

    }

    @Test
    @DisplayName("유저 정보를 가져온다.")
    void getUserInfo() {
        Optional<UserEntity> user = userRepository.findByEmail("hose0728@naver.com");
        UserEntity userEntity = user.orElse(null);
        String email = Objects.requireNonNull(userEntity).getEmail();
        System.out.println("email = " + email);
        UserResponseDto userInfo;
        userInfo = userService.getUserInfo(email);
        System.out.println("userInfo.getEmail() = " + userInfo.getEmail());

    }

    @Test
    @DisplayName("내 정보를 가져온다.")
    void getMyInfo() {
    }
}