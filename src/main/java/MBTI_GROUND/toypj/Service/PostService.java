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

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Optional<UserEntity> writer = userRepository.findByEmail(postRequestDto.getWriterId());
        if (writer.isPresent()) {
            UserEntity writerUserEntity = writer.get();
            PostEntity newPostEntity = postRequestDto.toPostEntity(writerUserEntity);
            postRepository.save(newPostEntity);

            return PostResponseDto.of(newPostEntity);
        } else {
            // 예외처리 필요
            return null;
        }

    }

    public PostResponseDto findOne(Long id) {
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

    public PostResponseDto updPost(PostRequestDto postRequestDto) {
        Optional<PostEntity> findPost = postRepository.findById(postRequestDto.getId());
        if (findPost.isPresent()) {
            PostEntity oldPost = findPost.get();
            oldPost.update(postRequestDto.getTitle(), postRequestDto.getContents());
            postRepository.save(oldPost);

            return PostResponseDto.of(oldPost);
        } else
            // 예외처리 필요
            return null;
    }

    public void delPost(Long id) {
        Optional<PostEntity> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            PostEntity target = findPost.get();
            postRepository.delete(target);
        } // 예외처리 필요
    }

    public void likeCountPlus(Long id){
        Optional<PostEntity> findPost = postRepository.findById(id);
        if(findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateLikeCount(1);
            postRepository.save(target);
        } // 에외처리 필요

   }

    public void likeCountMinus(Long id){
        Optional<PostEntity> findPost = postRepository.findById(id);
        if(findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateLikeCount(-1);
            postRepository.save(target);
        } // 에외처리 필요

    }

    public void hateCountPlus(Long id){
        Optional<PostEntity> findPost = postRepository.findById(id);
        if(findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateHateCount(1);
            postRepository.save(target);
        } // 에외처리 필요

    }

    public void hateCountMinus(Long id){
        Optional<PostEntity> findPost = postRepository.findById(id);
        if(findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateHateCount(-1);
            postRepository.save(target);
        } // 에외처리 필요

    }


}
