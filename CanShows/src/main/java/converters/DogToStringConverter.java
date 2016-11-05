package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Dog;

@Component
@Transactional
public class DogToStringConverter implements Converter<Dog, String> {

	@Override
	public String convert(Dog dog) {
		String result;

		if (dog == null)
			result = null;
		else
			result = String.valueOf(dog.getId());
		return result;
	}

}
