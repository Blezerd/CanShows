package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Participant;

@Component
@Transactional
public class ParticipantToStringConverter implements	Converter<Participant, String> {

	@Override
	public String convert(Participant participant) {
		String result;

		if (participant == null)
			result = null;
		else
			result = String.valueOf(participant.getId());
		return result;
	}

}
