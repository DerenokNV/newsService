package com.example.news_service.aop;

import com.example.news_service.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class UserVerificationBefore {

  private static final String ERROR_TEXT ="Редактирование и удаление новости/комментария, разрешается только тому пользователю, который её создал";

  @Before("@annotation(UserVerification)")
  public void logBefore( JoinPoint joinPoint ) {
    String userId = null;
    String objectUserId = null;

    Optional<Object> objectUserIdOpt = Arrays.stream( joinPoint.getArgs() ).filter( x -> (String.valueOf( x )).contains( "ObjectUserId" ) ).findFirst();
    if ( objectUserIdOpt.isPresent() ) {
      String obj = String.valueOf( objectUserIdOpt.get() );
      objectUserId = obj.substring( obj.indexOf( ":" ) + 1 );
    }

    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

    Map<String, String[]> parameterMap = request.getParameterMap();
    if ( parameterMap.isEmpty() || ! parameterMap.containsKey( "userId" ) ) {
      throw new EntityNotFoundException( ERROR_TEXT );
    }
    userId = parameterMap.get( "userId" )[0];

    log.info( " Params - userId:{} and objectUserId:{}", userId, objectUserId );

    if ( userId == null || objectUserId == null || "".equals( userId ) || "".equals( objectUserId ) ||
         ! Objects.equals( userId, objectUserId ) ) {
      throw new EntityNotFoundException( ERROR_TEXT );
    }
  }
}
