package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.OrganiserRepository;
import domain.Organiser;

@Component
@Transactional
public class StringToOrganiserConverter implements
		Converter<String, Organiser> {
	@Autowired
	OrganiserRepository organiserRepository;

	@Override
	public Organiser convert(String source) {
		Organiser result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = organiserRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}
}
