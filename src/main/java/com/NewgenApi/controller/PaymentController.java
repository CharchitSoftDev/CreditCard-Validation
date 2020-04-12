package com.NewgenApi.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NewgenApi.dao.CardPaymentRepository;
import com.NewgenApi.dao.PaymentRepository;
import com.NewgenApi.dto.CardEncryption;
import com.NewgenApi.dto.TransactionNotFoundException;
import com.NewgenApi.model.CardPayment;
import com.NewgenApi.model.Payment;

@Controller
@RequestMapping(value = "/paymentRequest")
public class PaymentController {

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private CardPaymentRepository cardRepo;

	LocalDate today = LocalDate.now();
	
	@RequestMapping("/")
	public String home(Payment payment) {
		return "index";
	}
	@RequestMapping("/tokePayNow")
	public String tokenPay(CardPayment cardPayment) {
		return "token";
	}

	@PostMapping("/payNow")
	public String payInstant(@Valid Payment payment, BindingResult bindingResult, @Valid CardPayment cardPay,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "index";
		}
		
		String Tmonth = today.getMonthValue() < 10 ? "0" + Integer.toString(today.getMonthValue())
				: Integer.toString(today.getMonthValue());
		if (payment.getExpYear() < today.getYear()) {
			System.out.println("Card expiry is worng");
			return "index";
		} else if (payment.getExpYear() == today.getYear() && payment.getExpMonth() < Integer.parseInt(Tmonth)) {
			System.out.println("Card expiry is worng");
			return "index";
		}

		payment.setTransactionId(UUID.randomUUID().toString());
		model.addAttribute("payment", payment.getTransactionId());
		if (payment.isSaveCard())

		{
			cardPay.setCardNumber(CardEncryption.encryptCardDetails(payment.getCardNumber()));
			CardEncryption.decryptCardDetails(cardPay.getCardNumber());
			cardPay.setTokenID(this.generateToken(payment.getCardNumber()));
			System.out.println(this.generateToken(payment.getCardNumber()));
			cardRepo.save(cardPay);
		}

		paymentRepo.save(payment);
		return "save";
	}

	@GetMapping("/getTransactionDetails/{transactionId}")
	public List<Payment> getTransactionDetails(@PathVariable String transactionId) throws TransactionNotFoundException {
		if (paymentRepo.findByTransactionId(transactionId).isEmpty()) {
			throw new TransactionNotFoundException();
		}
		return paymentRepo.findByTransactionId(transactionId);
	}

	private String generateToken(String cardNumber) {
		String firstfour = cardNumber.substring(0, 4);
		String lastFour = cardNumber.substring(cardNumber.length() - 4);
		return firstfour + "XXXXX" + lastFour;
	}
}
