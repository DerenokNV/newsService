package com.example.news_service.service;

import com.example.news_service.model.RoleType;
import com.example.news_service.model.User;
import com.example.news_service.security.AppUserPrincipal;
import com.example.news_service.security.facadeuser.IAuthenticationFacade;
import org.springframework.security.core.context.SecurityContextHolder;

public class Toolkit {

  private static final String ERROR_MAIN = " может только пользователь, который её создал или Модератор/Администратор.";

  public enum TypeObject {
    NEWS ( "новость" ),
    USERS ( "пользователя" ),
    COMMENT ( "комментарий" );

    String info;

    private TypeObject( String info ) {
      this.info = info;
    }
  }
  public enum TypeAction {
    UPDATE ( "Обновлять" ),
    DELETE ( "Удалять" );

    String info;

    private TypeAction( String info ) {
      this.info = info;
    }
  }

    public static User getUserContext() {
    var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if ( currentPrincipal instanceof AppUserPrincipal ) {
      return ((AppUserPrincipal)currentPrincipal).getUser();
    }

    return null;
  }

  public static String checkUserPrincipalAndUserObject( TypeObject typeObject, TypeAction typeAction,
                                                        Long idUserObject, IAuthenticationFacade authenticationFacade ) {
    AppUserPrincipal principal = (AppUserPrincipal)authenticationFacade.getAuthentication().getPrincipal();
    long countRoleAdmOrModer = principal.getAuthorities().stream()
             .filter( x -> x.getAuthority().equals( RoleType.ROLE_ADMIN.toString() ) ||
                           x.getAuthority().equals( RoleType.ROLE_MODERATOR.toString() ) )
             .count();

    return idUserObject == principal.getUserId() || countRoleAdmOrModer > 0 ? null :
                                                                              ( typeAction.info + " " + typeObject.info + ERROR_MAIN );
  }
}
