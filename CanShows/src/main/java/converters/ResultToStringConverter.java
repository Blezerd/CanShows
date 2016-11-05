package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Result;

@Component
@Transactional
public class ResultToStringConverter implements Converter<Result, String> {

	@Override
	public String convert(Result result) {
		String resultado;

		if (result == null)
			resultado = null;
		else
			resultado = String.valueOf(result.getId());
		return resultado;
	}

}
