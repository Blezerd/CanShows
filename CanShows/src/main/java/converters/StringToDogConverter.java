package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.DogRepository;
import domain.Dog;

@Component
@Transactional
public class StringToDogConverter implements
		Converter<String, Dog> {
	@Autowired
	DogRepository dogRepository;

	@Override
	public Dog convert(String source) {
		Dog result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = dogRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}

}
