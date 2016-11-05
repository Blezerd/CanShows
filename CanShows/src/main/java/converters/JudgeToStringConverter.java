package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Judge;

@Component
@Transactional
public class JudgeToStringConverter implements	Converter<Judge, String> {

	@Override
	public String convert(Judge judge) {
		String result;

		if (judge == null)
			result = null;
		else
			result = String.valueOf(judge.getId());
		return result;
	}

}
