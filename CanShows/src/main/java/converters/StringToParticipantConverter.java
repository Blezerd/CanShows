package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ParticipantRepository;
import domain.Participant;

@Component
@Transactional
public class StringToParticipantConverter implements
		Converter<String, Participant> {
	@Autowired
	ParticipantRepository participantRepository;

	@Override
	public Participant convert(String source) {
		Participant result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = participantRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}

}
