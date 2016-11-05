package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Breed;

@Component
@Transactional
public class BreedToStringConverter implements	Converter<Breed, String> {

	@Override
	public String convert(Breed breed) {
		String result;

		if (breed == null)
			result = null;
		else
			result = String.valueOf(breed.getId());
		return result;
	}

}
