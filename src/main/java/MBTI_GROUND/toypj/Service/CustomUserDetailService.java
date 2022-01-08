package MBTI_GROUND.toypj.Service;

import MBTI_GROUND.toypj.Entity.UserEntity;
import MBTI_GROUND.toypj.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username).map(this::createUserDetails)
        .orElseThrow(()-> new UsernameNotFoundException(username + "-> 데이터베이스에서 찾을 수 없습니다."));

  }

  public UserDetails createUserDetails(UserEntity userEntity){
    return new User(String.valueOf(userEntity.getId()),userEntity.getPassword(), AuthorityUtils.NO_AUTHORITIES);
  }
}
