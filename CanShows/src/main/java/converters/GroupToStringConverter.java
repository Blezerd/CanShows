package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Groups;

@Component
@Transactional
public class GroupToStringConverter implements	Converter<Groups, String> {

	@Override
	public String convert(Groups group) {
		String result;

		if (group == null)
			result = null;
		else
			result = String.valueOf(group.getId());
		return result;
	}

}
