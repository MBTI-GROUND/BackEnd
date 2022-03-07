package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Dto.PostRequestDto;
import MBTI_GROUND.toypj.Dto.PostResponseDto;
import MBTI_GROUND.toypj.Entity.PostEntity;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.PostRepository;
import MBTI_GROUND.toypj.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostService 테스트")
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private PostEntity postEntity;

    private UserEntity userEntity;

    private PostRequestDto postRequestDto;


    @BeforeEach
    void setup() {
        userEntity = UserEntity.builder()
                .email("hose0728@naver.com")
                .mbti("ENTJ")
                .nickname("홋메")
                .password("test")
                .phoneNumber("01047372086")
                .build();

        postEntity = PostEntity.builder()
                .title("테스트")
                .contents("이직하고싶다.")
                .type("TEST")
                .userEntity(userEntity)
                .build();

        postRequestDto = PostRequestDto.builder()
                .id(1L)
                .title("테스트용")
                .contents("테스트용")
                .type("TEST")
                .writerId("hose0728@naver.com")
                .build();

    }


    @Test
    @DisplayName("게시글을 생성한다.")
    void createPost() {

        //given
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(userEntity));


        //when
        PostResponseDto result = postService.createPost(postRequestDto);
        //then
        assertEquals(result.getContents(), postRequestDto.getContents());
        assertEquals(result.getTitle(), postRequestDto.getTitle());
        assertEquals(result.getNickname(), userEntity.getNickname());

    }

    @Test
    @DisplayName("게시글 찾기")
    void findOne() {

        //given
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(postEntity));
        //when
        PostResponseDto result = postService.findOne(1L);
        //then
        assertEquals(result.getNickname(), userEntity.getNickname());
        assertEquals(result.getTitle(), postEntity.getTitle());
        assertEquals(result.getContents(), postEntity.getContents());

    }

    @Test
    @DisplayName("특정 게시글 전체 조회")
    void findAll() {

        //given
        List<PostEntity> test = new ArrayList<>();
        test.add(postEntity);
        given(postRepository.findAllByTypeOrderByCreatedDateDesc(any())).willReturn(test);
        //when
        List<PostResponseDto> result = postService.findAll("TEST");
        //then
        assertEquals(result.get(0).getType(),postEntity.getType());

    }

    @Test
    @DisplayName("게시글 업데이트")
    void updPost() {

        //given
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(postEntity));
        //when
        PostResponseDto result = postService.updPost(postRequestDto);
        //then
        assertEquals(result.getTitle(),postRequestDto.getTitle());
        assertEquals(result.getContents(),postRequestDto.getContents());

    }

    @Test
    @DisplayName("좋아요 +1")
    void testLikeCountPlus() {
        //given
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(postEntity));
        //when
        postService.likeCountPlus(1L);

        //then
        assertEquals(postEntity.getLikeCount(),1);
    }

    @Test
    @DisplayName("좋아요 -1")
    void likeCountMinus() {

        //given
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(postEntity));
        //when
        postService.likeCountMinus(1L);
        //then
        assertEquals(postEntity.getLikeCount(),-1);
    }

    @Test
    @DisplayName("싫어요 +1")
    void hateCountPlus() {

        //given
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(postEntity));
        //when
        postService.hateCountPlus(1L);
        //then
        assertEquals(postEntity.getHateCount(),1);

    }

    @Test
    @DisplayName("싫어요 -1")
    void hateCountMinus() {

        //given
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(postEntity));
        //when
        postService.hateCountMinus(1L);
        //then
        assertEquals(postEntity.getHateCount(),-1);
    }
}