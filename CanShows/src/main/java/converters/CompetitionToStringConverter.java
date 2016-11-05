package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Competition;

@Component
@Transactional
public class CompetitionToStringConverter implements	Converter<Competition, String> {

	@Override
	public String convert(Competition competition) {
		String result;

		if (competition == null)
			result = null;
		else
			result = String.valueOf(competition.getId());
		return result;
	}

}
