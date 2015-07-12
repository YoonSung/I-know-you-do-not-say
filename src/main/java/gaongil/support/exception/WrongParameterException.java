package gaongil.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongParameterException extends RuntimeException{
	private static final long serialVersionUID = 7254043714249432443L;
}
