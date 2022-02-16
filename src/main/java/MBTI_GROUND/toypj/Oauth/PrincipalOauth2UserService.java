package MBTI_GROUND.toypj.Oauth;

import java.util.Map;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  //social 로그인 서버로부터 받은 userRequest 데이터에 대한 후처리되는 함수
  // 소셜로그인 클릭 -> 로그인창 -> 로그인을 완료 -> code 리턴(OAuth-client라이브러리) -> AccessToken 요청-> userRequest 정보 ->loadUser함수 호출 -> 소셜로그인 서버로부터 회원프로필 받아줌
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    Map<String, Object> attributes = null;
    OAuth2User oAuth2User = super.loadUser(userRequest);
    switch (userRequest.getClientRegistration().getRegistrationId()) {
      case "google":
      case "facebook":
        attributes = oAuth2User.getAttributes();
        break;
      case "naver":
        attributes = oAuth2User.getAttribute("response");
        break;
    }
    assert attributes != null;

    return new DefaultOAuth2User(AuthorityUtils.NO_AUTHORITIES,attributes,"email");

  }


}
