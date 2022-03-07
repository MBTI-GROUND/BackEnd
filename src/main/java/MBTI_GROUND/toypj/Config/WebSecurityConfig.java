package MBTI_GROUND.toypj.Config;

import MBTI_GROUND.toypj.Oauth.OAuth2FailureHandler;
import MBTI_GROUND.toypj.Oauth.OAuth2SuccessHandler;
import MBTI_GROUND.toypj.Oauth.PrincipalOauth2UserService;
import MBTI_GROUND.toypj.Auth.JwtAccessDeniedHandler;
import MBTI_GROUND.toypj.Auth.JwtAuthenticationEntryPoint;
import MBTI_GROUND.toypj.Auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final OAuth2FailureHandler oAuth2FailureHandler;
  private final PrincipalOauth2UserService principalOauth2UserService;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers("/h2-console/**", "/favicon.ico");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf().disable()

        .exceptionHandling()
        .accessDeniedHandler(jwtAccessDeniedHandler)
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)

        .and()
        .httpBasic().disable()
        .formLogin().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()

        .authorizeRequests()
        .antMatchers("/", "/auth/**", "/oauth2/**", "/login/**","/ws/**","/sub/**",
                "/v2/**", // swagger
                "/webjars/**", // swagger
                "/swagger**", // swagger
                "/swagger-resources/**"
                ).permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(principalOauth2UserService)
        .and()
        .successHandler(oAuth2SuccessHandler)
        .failureHandler(oAuth2FailureHandler);

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
