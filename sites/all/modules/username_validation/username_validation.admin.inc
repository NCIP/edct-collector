<?php

function username_validation_settings_form($form_state) {
    $form = array();
    $form['username_validation_min'] = array(
		'#type' => 'textfield',
		'#title' => t('Minimun Length'),
		'#description' => t('The minimum length of the username.'),
		'#size' => 5,
        '#maxlength' => 2,
		'#default_value' => variable_get('username_validation_min', 2),
	);
	$form['username_validation_min_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Minimun Length Error Message'),
	    '#description' => t('You may use the token !min to print the Minimum Length inside the error message.'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_min_txt', 'This username is too short. The minimum size is !min.'),
	);
	$form['username_validation_max'] = array(
		'#type' => 'textfield',
		'#title' => t('Maximun Length'),
		'#description' => t('The maximum length of the username.'),
		'#size' => 5,
        '#maxlength' => 2,
		'#default_value' => variable_get('username_validation_max', 20),
	);
	$form['username_validation_max_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Maximun Length Error Message'),
	    '#description' => t('You may use the token !max to print the Maximum Length inside the error message.'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_max_txt', 'This username is too long. The maximum size is !max.'),
	);
	$form['username_validation_letter'] = array(
        '#type' => 'checkbox',
        '#title' => t('Letter Required'),
        '#default_value' => variable_get('username_validation_letter', 1),
    );
    $form['username_validation_letter_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Letter Required Error Message'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_letter_txt', 'Username requires at least one letter.'),
	);
	$form['username_validation_ucase'] = array(
        '#type' => 'checkbox',
        '#title' => t('Upper Case Letter Required'),
        '#default_value' => variable_get('username_validation_ucase', 1),
    );
    $form['username_validation_ucase_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Upper Case Required Error Message'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_ucase_txt', 'Username requires at least one upper case letter.'),
	);
    $form['username_validation_lcase'] = array(
        '#type' => 'checkbox',
        '#title' => t('Lower Case Letter Required'),
        '#default_value' => variable_get('username_validation_lcase', 0),
    );
    $form['username_validation_lcase_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Lower Case Required Error Message'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_lcase_txt', 'Username requires at least one lower case letter.'),
	);
    $form['username_validation_number'] = array(
        '#type' => 'checkbox',
        '#title' => t('Number Required'),
        '#default_value' => variable_get('username_validation_number', 0),
    );
    $form['username_validation_number_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Number Required Error Message'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_number_txt', 'Username requires at least one number.'),
	);
    $form['username_validation_illegal'] = array(
        '#type' => 'checkbox',
        '#title' => t('No Illegal Characters (Letters and Numbers Only)'),
        '#default_value' => variable_get('username_validation_illegal', 1),
    ); 
    $form['username_validation_illegal_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Illegal Character Error Message'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_illegal_txt', ' Username must not contain illegal characters.'),
	);
	$form['username_validation_blank_txt'] = array(
		'#type' => 'textfield',
		'#title' => t('Blank Username Message(js only)'),
		'#size' => 120,
		'#default_value' => variable_get('username_validation_blank_txt', 'Username is blank.'),
	);
    return system_settings_form($form);
}