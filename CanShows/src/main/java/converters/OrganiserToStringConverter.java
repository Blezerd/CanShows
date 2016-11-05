package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Organiser;

@Component
@Transactional
public class OrganiserToStringConverter implements	Converter<Organiser, String> {

	@Override
	public String convert(Organiser organiser) {
		String result;

		if (organiser == null)
			result = null;
		else
			result = String.valueOf(organiser.getId());
		return result;
	}

}
