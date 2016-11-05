package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;

import domain.CreditCard;

@Access(AccessType.PROPERTY)
public class CustomerForm extends ActorForm {

	private CreditCard creditCard;

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
