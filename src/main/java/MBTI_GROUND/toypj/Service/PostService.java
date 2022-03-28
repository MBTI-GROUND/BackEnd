package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Dto.PostRequestDto;
import MBTI_GROUND.toypj.Dto.PostResponseDto;
import MBTI_GROUND.toypj.Entity.PostEntity;
import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Exception.PostNotFoundException;
import MBTI_GROUND.toypj.Exception.UserNotFoundException;
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

    public PostResponseDto createPost(PostRequestDto postRequestDto) throws RuntimeException {
        Optional<UserEntity> writer = userRepository.findByEmail(postRequestDto.getWriterId());
        if (writer.isPresent()) {
            UserEntity writerUserEntity = writer.get();
            PostEntity newPostEntity = postRequestDto.toPostEntity(writerUserEntity);
            postRepository.save(newPostEntity);

            return PostResponseDto.of(newPostEntity);
        } else {
            throw new UserNotFoundException("유저 정보 없음");
        }

    }

    public PostResponseDto findOne(Long id) throws RuntimeException {
        Optional<PostEntity> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            return PostResponseDto.of(findPost.get());
        } else {
            throw new PostNotFoundException("ID에 해당하는 게시글 없음");
        }

    }

    public List<PostResponseDto> findAll(String type) {
        List<PostEntity> posts = postRepository.findAllByTypeOrderByCreatedDateDesc(type);
        List<PostResponseDto> responseDtos = new ArrayList<>();
        for (PostEntity post : posts) {
            responseDtos.add(PostResponseDto.of(post));
        }
        return responseDtos;
    }

    public PostResponseDto updPost(PostRequestDto postRequestDto) throws RuntimeException {
        Optional<PostEntity> findPost = postRepository.findById(postRequestDto.getId());
        if (findPost.isPresent()) {
            PostEntity oldPost = findPost.get();
            oldPost.update(postRequestDto.getTitle(), postRequestDto.getContents());
            postRepository.save(oldPost);

            return PostResponseDto.of(oldPost);
        } else
            // 예외처리 필요
            throw new PostNotFoundException("ID에 해당하는 게시글 없음");
    }

    public void delPost(Long id) throws RuntimeException {
        Optional<PostEntity> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            PostEntity target = findPost.get();
            postRepository.delete(target);
        } // 예외처리 필요
        else {
            throw new PostNotFoundException("ID에 해당하는 게시글 없음");
        }
    }

    public void likeCountPlus(Long id) throws RuntimeException {
        Optional<PostEntity> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateLikeCount(1);
            postRepository.save(target);
        } // 에외처리 필요
        else {
            throw new PostNotFoundException("ID에 해당하는 게시글 없음");
        }

    }

    public void likeCountMinus(Long id) throws RuntimeException{
        Optional<PostEntity> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateLikeCount(-1);
            postRepository.save(target);
        } // 에외처리 필요
        else {
            throw new PostNotFoundException("ID에 해당하는 게시글 없음");
        }

    }

    public void hateCountPlus(Long id) throws RuntimeException{
        Optional<PostEntity> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateHateCount(1);
            postRepository.save(target);
        } // 에외처리 필요
        else {
            throw new PostNotFoundException("ID에 해당하는 게시글 없음");
        }

    }

    public void hateCountMinus(Long id) throws RuntimeException{
        Optional<PostEntity> findPost = postRepository.findById(id);
        if (findPost.isPresent()) {
            PostEntity target = findPost.get();
            target.updateHateCount(-1);
            postRepository.save(target);
        } // 에외처리 필요
        else {
            throw new PostNotFoundException("ID에 해당하는 게시글 없음");
        }
    }


}
