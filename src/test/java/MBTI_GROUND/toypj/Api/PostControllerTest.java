package MBTI_GROUND.toypj.Api;

import MBTI_GROUND.toypj.Dto.PostRequestDto;
import MBTI_GROUND.toypj.Dto.PostResponseDto;
import MBTI_GROUND.toypj.Entity.PostEntity;
import MBTI_GROUND.toypj.Service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {PostController.class, ObjectMapper.class})
@DisplayName("PostController 테스트")
class PostControllerTest {

    private MockMvc mvc;
    @MockBean
    private PostService postService;

    private PostRequestDto postRequestDto;


    private PostResponseDto postResponseDto;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new PostController(postService)).addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        postRequestDto = PostRequestDto.builder()
                .type("ENTJ")
                .contents("테스트")
                .writerId("hose0728@naver.com")
                .title("Controller 테스트")
                .build();

        postResponseDto = PostResponseDto.builder()
                .id(1L)
                .title("테스트")
                .type("TEST")
                .nickname("홋메")
                .hateCount(0)
                .likeCount(0)
                .contents("Controller 테스트")
                .createdTime(LocalDateTime.now())
                .build();


    }

    @Test
    @DisplayName("게시글 추가 테스트")
    @WithAnonymousUser
    void addPost() throws Exception {
        //given
        given(postService.createPost(any())).willReturn(postResponseDto);

        //when
        ResultActions actions = mvc.perform(post("/post/create").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(postRequestDto)));

        //then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(postResponseDto.getTitle()))
                .andExpect(jsonPath("id").value(postResponseDto.getId()))
                .andExpect(jsonPath("likeCount").value(postResponseDto.getLikeCount()))
                .andExpect(jsonPath("type").value(postResponseDto.getType()))
                .andExpect(jsonPath("contents").value(postResponseDto.getContents()))
                .andExpect(jsonPath("hateCount").value(postResponseDto.getHateCount()))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 게시글 찾기")
    void findPost() throws Exception {
        //given
        given(postService.findOne(any())).willReturn(postResponseDto);
        //when
        ResultActions actions = mvc.perform(get("/post/find/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(postResponseDto.getId()))
                .andExpect(jsonPath("type").value(postResponseDto.getType()))
                .andExpect(jsonPath("nickname").value(postResponseDto.getNickname()))
                .andExpect(jsonPath("title").value(postResponseDto.getTitle()))
                .andExpect(jsonPath("contents").value(postResponseDto.getContents()))
                .andExpect(jsonPath("likeCount").value(postResponseDto.getLikeCount()))
                .andExpect(jsonPath("hateCount").value(postResponseDto.getHateCount()))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 게시글 type 전부 찾기")
    void findPostAll() throws Exception {

        //given
        List<PostResponseDto> test = new ArrayList<>();
        test.add(postResponseDto);
        test.add(postResponseDto);
        given(postService.findAll(any())).willReturn(test);

        //when
        ResultActions actions = mvc.perform(get("/post/find").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("type",postRequestDto.getType()));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(postResponseDto.getId()))
                .andExpect(jsonPath("$[0].nickname").value(postResponseDto.getNickname()))
                .andExpect(jsonPath("$[0].contents").value(postResponseDto.getContents()))
                .andExpect(jsonPath("$[0].title").value(postResponseDto.getTitle()))
                .andExpect(jsonPath("$[0].type").value(postResponseDto.getType()))
                .andExpect(jsonPath("$[0].likeCount").value(postResponseDto.getLikeCount()))
                .andExpect(jsonPath("$[0].hateCount").value(postResponseDto.getHateCount()))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void delPost() throws Exception {

        //given

        //when
        ResultActions actions = mvc.perform(delete("/post/del/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));
        //then
        actions.andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("게시글 변경 테스트")
    void updPost() throws Exception {

        //given
        given(postService.updPost(any())).willReturn(postResponseDto);
        //when
        ResultActions actions = mvc.perform(put("/post/update").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto))
                .characterEncoding("UTF-8"));
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(postResponseDto.getId()))
                .andExpect(jsonPath("type").value(postResponseDto.getType()))
                .andExpect(jsonPath("nickname").value(postResponseDto.getNickname()))
                .andExpect(jsonPath("title").value(postResponseDto.getTitle()))
                .andExpect(jsonPath("contents").value(postResponseDto.getContents()))
                .andExpect(jsonPath("likeCount").value(postResponseDto.getLikeCount()))
                .andExpect(jsonPath("hateCount").value(postResponseDto.getHateCount()))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 좋아요 +1")
    void likeCountPlus() throws Exception {
        //given

        //when
        ResultActions actions = mvc.perform(put("/post/likeCount/plus/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 좋아요 -1")
    void likeCountMinus() throws Exception {

        //given

        //when
        ResultActions actions = mvc.perform(put("/post/likeCount/minus/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 싫어요 +1")
    void hateCountPlus() throws Exception {

        //given

        ResultActions actions = mvc.perform(put("/post/hateCount/plus/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 싫어요 -1")
    void hateCountMinus() throws Exception {

        //given

        ResultActions actions = mvc.perform(put("/post/hateCount/minus/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }
}