package com.example.news_service.aop;

import com.example.news_service.model.RoleType;
import com.example.news_service.security.AppUserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.*;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class UserVerificationBefore {

  private static final String ERROR_TEXT ="Редактирование и удаление новости/комментария, разрешается только тому пользователю, который её создал. Так же при наличии ролей ADMIN/MODERATOR";

  @Before("@annotation(UserVerification)")
  public void logBefore( JoinPoint joinPoint ) throws Exception {
    Long userId = null;
    Long objectUserId = null;

    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

    Map pathVariables = (Map) request.getAttribute( HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    String attId = (String)pathVariables.get("id");
    if ( attId != null && ! attId.isEmpty() ) {
      objectUserId = Long.parseLong( attId );
    }

    var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if ( currentPrincipal == null ) {
      throw new EntityNotFoundException( "Что то пошло не так" );
    }

    List<SimpleGrantedAuthority> roles = null;
    if ( currentPrincipal instanceof AppUserPrincipal ) {
      roles = (List<SimpleGrantedAuthority>)( (AppUserPrincipal) currentPrincipal ).getAuthorities();
      userId = ( (AppUserPrincipal) currentPrincipal ).getUserId();
    }

    if ( roles != null && ! roles.isEmpty() && ( roles.size() > 1 || ( roles.size() == 1 && roles.stream().filter( x -> x.getAuthority().equals( RoleType.ROLE_USER.toString() ) ).count() == 0 ) )  ) {
      return;
    }

    log.info( " Params - userId:{} and objectUserId:{}", userId, objectUserId );

    if ( userId == null || objectUserId == null || ! Objects.equals( userId, objectUserId ) ) {
      throw new EntityNotFoundException( ERROR_TEXT );
    }
  }
}
