package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ResultRepository;
import domain.Result;

@Component
@Transactional
public class StringToResultConverter implements Converter<String, Result> {
	@Autowired
	ResultRepository resultRepository;

	@Override
	public Result convert(String source) {
		Result resultado;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				resultado = null;
			else {
				id = Integer.valueOf(source);
				resultado = resultRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return resultado;

	}

}
