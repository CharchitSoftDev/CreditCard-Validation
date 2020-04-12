package com.NewgenApi.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	public String transactionId;
	@NotNull(message = "CVV must not be Null")
	@Pattern(regexp = "^[0-9]{3,4}+", message = "CVV Number is Invalid")
	private String CVV;
	@NotNull(message = "Expiry Year must not be Null")
	private int expMonth;
	@NotNull(message = "Expiry Year must not be Null")
	private int expYear;
	@NotNull(message = "Card Number must not be Null")
	@Pattern(regexp = "^[0-9]{16}+", message = "Card Number is Invalid")
	private String cardNumber;
	private String vendor;
	@NotNull(message = "Amount Cannot be Null")
	@Pattern(regexp = "[1-9].+", message = "Invalid Amount")
	@Min(value = 1L, message = "The value must be positive")
	private String amount;
	@Transient
	private boolean saveCard;
	@OneToOne(mappedBy = "paymentId", cascade = CascadeType.ALL)
	private CardPayment cardPayment;

}
