package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Dto.PostRequestDto;
import MBTI_GROUND.toypj.Dto.PostResponseDto;
import MBTI_GROUND.toypj.Entity.PostEntity;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.PostRepository;
import MBTI_GROUND.toypj.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto){
        Optional<UserEntity> writer = userRepository.findByEmail(postRequestDto.getWriterId());
        if (writer.isPresent()){
            UserEntity writerUserEntity = writer.get();
            PostEntity newPostEntity = postRequestDto.toPostEntity(writerUserEntity);
            postRepository.save(newPostEntity);

            return PostResponseDto.of(newPostEntity);
        }
        else{
            return null;
        }

    }

    public PostResponseDto findOne(Long id){
        Optional<PostEntity> findPost = postRepository.findById(id);
        return findPost.map(PostResponseDto::of).orElse(null);
    }

    public List<PostResponseDto> findAll(String type) {
        List<PostEntity> posts = postRepository.findAllByTypeOrderByCreatedDateDesc(type);
        List<PostResponseDto> responseDtos = new ArrayList<>();
        for (PostEntity post : posts) {
            responseDtos.add(PostResponseDto.of(post));
        }
        return responseDtos;
    }

    public void updPost(PostRequestDto postRequestDto){
        Optional<PostEntity> findPost = postRepository.findById(postRequestDto.getId());
        if(findPost.isPresent()){
            PostEntity oldPost = findPost.get();
            oldPost.update(postRequestDto.getType(), postRequestDto.getTitle(), postRequestDto.getContents());
            postRepository.save(oldPost);
        }
    }



}
