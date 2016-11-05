package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Administrator;

import repositories.AdministratorRepository;

@Component
@Transactional
public class StringToAdministratorConverter implements
		Converter<String, Administrator> {
	@Autowired
	AdministratorRepository administratorRepository;

	@Override
	public Administrator convert(String source) {
		Administrator result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = administratorRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}

}
