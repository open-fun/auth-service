package me.treaba.auth;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * Created by Stanislav on 08.03.17.
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler({DataIntegrityViolationException.class, TransactionSystemException.class, ConstraintViolationException.class, JpaSystemException.class})
  void handleBadRequests(Exception ex, HttpServletResponse response) throws IOException {
    logger.warn(ex.getLocalizedMessage(), ex);
    response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
  }
}
