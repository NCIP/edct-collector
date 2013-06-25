<?php

function custom_validate_field_settings_form($form_state) {
    $form = array();
    $form['custom_validate_field_email'] = array(
		'#type' => 'textfield',
		'#title' => t('Email Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_email', 'Email is not valid.'),
	);
	$form['custom_validate_field_first_name'] = array(
		'#type' => 'textfield',
		'#title' => t('First Name Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_first_name', 'First Name may only contain letters.'),
	);
	$form['custom_validate_field_middle_initial'] = array(
		'#type' => 'textfield',
		'#title' => t('Middle Initial Error Text'),
		'#size' => 10,
		'#default_value' => variable_get('custom_validate_field_middle_initial', 'Middle Initial may only contain letters.'),
	);
	$form['custom_validate_field_last_name'] = array(
		'#type' => 'textfield',
		'#title' => t('Last Name Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_last_name', 'Last Name may only contain letters.'),
	);
	$form['custom_validate_field_city'] = array(
		'#type' => 'textfield',
		'#title' => t('City Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_city', 'City may only contain letters.'),
	);
	$form['custom_validate_field_province'] = array(
		'#type' => 'textfield',
		'#title' => t('Province Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_province', 'Province may only contain letters.'),
	);
	
	$form['contact'] = array(
	    '#type' => 'fieldset',
	    '#title' => 'Contact Info Fields',
	);
	
	$form['contact']['custom_validate_field_contact_first_name'] = array(
		'#type' => 'textfield',
		'#title' => t('First Name Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_contact_first_name', 'First Name may only contain letters.'),
	);
	$form['contact']['custom_validate_field_contact_middle_name'] = array(
		'#type' => 'textfield',
		'#title' => t('Middle Name Error Text'),
		'#size' => 1,
		'#default_value' => variable_get('custom_validate_field_contact_middle_name', 'Middle Name may only contain letters.'),
	);
	$form['contact']['custom_validate_field_contact_last_name'] = array(
		'#type' => 'textfield',
		'#title' => t('Last Name Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_contact_last_name', 'Last Name may only contain letters.'),
	);
	$form['contact']['custom_validate_field_contact_city'] = array(
		'#type' => 'textfield',
		'#title' => t('City Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_contact_city', 'City may only contain letters.'),
	);
	$form['contact']['custom_validate_field_contact_province'] = array(
		'#type' => 'textfield',
		'#title' => t('Province Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_contact_province', 'Province may only contain letters.'),
	);
	$form['contact']['custom_validate_field_contact_zip_code'] = array(
		'#type' => 'textfield',
		'#title' => t('Zip Code Error Text'),
		'#size' => 5,
		'#default_value' => variable_get('custom_validate_field_contact_zip_code', 'Zip Code may only contain numbers.'),
	);
	$form['contact']['custom_validate_field_contact_phone_number'] = array(
		'#type' => 'textfield',
		'#title' => t('Phone Number Error Text'),
		'#size' => 15,
		'#default_value' => variable_get('custom_validate_field_contact_phone_number', 'Phone Number is invalid.'),
	);
	$form['contact']['custom_validate_field_contact_email'] = array(
		'#type' => 'textfield',
		'#title' => t('Email Error Text'),
		'#size' => 120,
		'#default_value' => variable_get('custom_validate_field_contact_email', 'Email is invalid.'),
	);
	
	return system_settings_form($form);
}