<?php

/**
 * implementation of hook_perm
 */
function dupe_perm() {
  return array('administer dupe');
}

/**
 * Implementation of hook_menu().
 */
function dupe_menu() {
  $items = array();
  $items['admin/user/dupe'] = array(
    'title' => t('Dupe Settings'),
    'page callback' => 'drupal_get_form',
    'page arguments' => array('dupe_settings'),
    'access arguments' => array('administer dupe')
  );
  return $items;
}

/**
 * settings form for dupe
 */
function dupe_settings() {

  $form['dupe_check'] = array(
    '#type' => 'fieldset', 
    '#title' => t('Dupe Check'), 
    '#collapsible' => true,
    '#collapsed' => false,
  );

  $form['dupe_check']['do_dupe_check'] = array(
    '#type' => 'checkbox',
    '#title' => t('Duplicates check'),
    '#description' => t('If checked, join page validation will check if the data entered seems to be a duplicate of a currently registered user.'),
    '#default_value' => variable_get('do_dupe_check', 0)
  );

  $form['dupe_check']['dupe_profile_fields'] = array(
    '#type' => 'textfield',
    '#title' => t('Dupe profile fields'),
    '#description' => t('If <b>Duplicates check</b> option is checked, enter here a comma separated list of the Profile Fields (picked from the <b>First Step Category</b>) that will be used to check for a possible duplicated user.'),
    '#default_value' => variable_get('dupe_profile_fields', 'profile_dob,profile_last_name,profile_first_name')
  );

  $form['confirm_box'] = array(
    '#type' => 'fieldset', 
    '#title' => t('Dupe Confirm Box'), 
    '#collapsible' => true,
    '#collapsed' => false,
  );

  $form['confirm_box']['dupe_message'] = array(
    '#type' => 'textfield',
    '#title' => t('Dupe confirm box message'),
    '#description' => t('Will use this as the text box to alert users that they might be registering a duplicate account. <i>%s</i> will be replaced by the user\'s email.'),
    '#default_value' => variable_get('dupe_message', 'Seems you already registered an account using %s!')
  );

  $form['confirm_box']['dupe_message_yes'] = array(
    '#type' => 'textfield',
    '#title' => t('Dupe confirm box option <i>Yes</i>'),
    '#description' => t('Text for the <i>Yes</i> link.'),
    '#default_value' => variable_get('dupe_message_yes', 'Yes, I own this email address.')
  );

  $form['confirm_box']['dupe_message_no'] = array(
    '#type' => 'textfield',
    '#title' => t('Dupe confirm box option <i>No</i>'),
    '#description' => t('Text for the <i>No</i> link.'),
    '#default_value' => variable_get('dupe_message_no', 'No, it\'s not mine.')
  );

  $form['previous_pages'] = array(
    '#type' => 'fieldset', 
    '#title' => t('Previous Pages'), 
    '#collapsible' => true,
    '#collapsed' => false,
    '#description' => t('Please be sure to send users to /user/register<b>?c</b> so that they can join. If the <b>c</b> param is not present in the GET, the module will redirect the user to <i>First page</i>.')
  );

  $form['previous_pages']['redirect_to_path'] = array(
    '#type' => 'textfield',
    '#title' => t('First page'),
    '#default_value' => variable_get('redirect_to_path', '')
  );

  return system_settings_form($form);

}
