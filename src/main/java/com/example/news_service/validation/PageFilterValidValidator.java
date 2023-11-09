package com.example.news_service.validation;

import com.example.news_service.web.dto.PagesRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class PageFilterValidValidator implements ConstraintValidator<PageFilterValid, PagesRequest>  {

  @Override
  public boolean isValid( PagesRequest value, ConstraintValidatorContext context ) {
    if ( ObjectUtils.anyNull( value.getPageNumber(), value.getPageSize() ) ) {
      return false;
    }

    return true;
  }
}
