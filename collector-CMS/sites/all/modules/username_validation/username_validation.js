Drupal.behaviors.username_validation = function() {
	var usernameInput = $('#edit-name');
	$(usernameInput).after('<div id="username_validation_error"></div>');
	var errorDiv = $('#username_validation_error');
	var errors = '';
	var validUsername = function() {
		var username = usernameInput.val();
		// blank
		if (!username) {
	        errors += Drupal.settings.username_validation.blank_txt + '<br />';
		}
		// too short
		if (username.length < Drupal.settings.username_validation.min) {
	        errors += Drupal.settings.username_validation.min_txt + '<br />';
		}
		// too long
		if (username.length > Drupal.settings.username_validation.max) {
	        errors += Drupal.settings.username_validation.max_txt + '<br />';
		}
		// letter required
	    if(Drupal.settings.username_validation.require_letter) {
	    	var regex = new RegExp(/[A-Za-z]/);
	        if(regex.exec(username) == null) errors += Drupal.settings.username_validation.require_letter_txt + '<br />';
	    }
		// lower case letter required
	    if(Drupal.settings.username_validation.require_lcase) {
	    	var regex = new RegExp(/[a-z]/);
	        if(regex.exec(username) == null) errors += Drupal.settings.username_validation.require_lcase_txt + '<br />';
	    }
		// upper case letter required
	    if(Drupal.settings.username_validation.require_ucase) {
	    	var regex = new RegExp(/[A-Z]/);
	        if(regex.exec(username) == null) errors += Drupal.settings.username_validation.require_ucase_txt + '<br />';
	    }
	    // number required
	    if(Drupal.settings.username_validation.require_num) {
	    	var regex = new RegExp(/[0-9]/);
	        if(regex.exec(username) == null) errors += Drupal.settings.username_validation.require_num_txt + '<br />';
	    }
	    // no illegal characters
	    if(Drupal.settings.username_validation.no_illegal) {
	    	var regex = new RegExp(/^[a-zA-Z0-9]+$/);
	    	if(regex.exec(username) == null) errors += Drupal.settings.username_validation.no_illegal_txt + '<br />';
	    }
		
		// show errors
		if(errors != '') {
			$(errorDiv).html(errors);
		}
	};
	
	var clearErrors = function() {
		$(errorDiv).html('');
		errors = '';
	};
	
	usernameInput.blur(validUsername);
	usernameInput.focus(clearErrors);
};
