package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.JudgeRepository;
import domain.Judge;

@Component
@Transactional
public class StringToJudgeConverter implements Converter<String, Judge> {
	@Autowired
	JudgeRepository judgeRepository;

	@Override
	public Judge convert(String source) {
		Judge result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = judgeRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}

}
