package com.example.news_service.security.facadeuser;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

  Authentication getAuthentication();

}
