package MBTI_GROUND.toypj.Repository;

import MBTI_GROUND.toypj.Entity.PostEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("PostRepository 테스트")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("PostRepository 테스트")
    void findAllByTypeOrderByCreatedDateDesc() {
        //given
        String type = "ENTJ";
        PostEntity testPost = PostEntity.builder()
                .title("테스트")
                .type(type)
                .contents("레포지토리 테스트")
                .build();
        //when
        postRepository.save(testPost);
        List<PostEntity> result = postRepository.findAllByTypeOrderByCreatedDateDesc(type);

        //then
        assertEquals(result.get(0).getType(),type);
        assertEquals(testPost,result.get(0));
    }


}