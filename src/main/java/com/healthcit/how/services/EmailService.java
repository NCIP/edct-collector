package com.healthcit.how.services;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailService {
	
	Logger log = Logger.getLogger(EmailService.class);
	
	private MailSender mailSender;
	
	private SimpleMailMessage message;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setMessage(SimpleMailMessage message) {
		this.message = message;
	}
	
	/**
	 * Generates email using the supplied parameters as the email body/recipients.
	 * @param body - The email content
	 * @param recipients - The email recipients
	 * @param body - The email body
	 */
	public void generateEmail( String body, String subject, String[] recipients ) {
		
		SimpleMailMessage msg = new SimpleMailMessage(this.message);
		
		// set the subject - default to the configuration value if this is null
		if ( subject != null )
			msg.setSubject( subject );
		
		// set the recipients
		msg.setTo( recipients );
		
		// set the body
		msg.setText( body );
		
		// generate the email
		
		try {
			this.mailSender.send(msg);
		} catch ( MailException mex ) {
			log.error( "Could not generate email..." );
			log.error( mex.getMessage() );
		}
		
	}

}
