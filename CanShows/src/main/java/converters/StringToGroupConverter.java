package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.GroupRepository;
import domain.Groups;

@Component
@Transactional
public class StringToGroupConverter implements
		Converter<String, Groups> {
	@Autowired
	GroupRepository groupRepository;

	@Override
	public Groups convert(String source) {
		Groups result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = groupRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}

}
