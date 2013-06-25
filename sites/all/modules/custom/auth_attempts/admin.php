<?php

/**
 * implementation of hook_perm
 */
function auth_attempts_perm() {
  return array('administer auth attempts');
}

/**
 * Implementation of hook_menu().
 */
function auth_attempts_menu() {
  $items = array();
  $items['admin/user/auth_attempts'] = array(
    'title' => t('Authentication Attempts Settings'),
    'page callback' => 'drupal_get_form',
    'page arguments' => array('auth_attempts_settings'),
    'access arguments' => array('administer auth attempts')
  );
  return $items;
}

/**
 * settings form for auth attempts
 */
function auth_attempts_settings() {
  $form['do_auth_attempts_check'] = array(
    '#type' => 'checkbox',
    '#title' => t('Enable authentication attemps check'),
    '#default_value' => variable_get('do_auth_attempts_check', 0)
  );
  $form['auth_attempts_minutes'] = array(
    '#type' => 'textfield',
    '#title' => t('Check Interval (in minutes)'),
    '#default_value' => variable_get('auth_attempts_minutes', 1)
  );
  $form['auth_attempts_tries'] = array(
    '#type' => 'textfield',
    '#title' => t('Max number of allowed tries'),
    '#default_value' => variable_get('auth_attempts_tries', 3)
  );
  $form['auth_attempts_password_reset_warning'] = array(
    '#type' => 'textfield',
    '#title' => t('Password Reset Warning'),
    '#default_value' => variable_get('auth_attempts_password_reset_warning', 'You\'ve tried to login {attempts} times unsuccessfully.<br/>You need to reset your password using this {link}form{/link}.')
  );
  $form['auth_attempts_warning_message'] = array(
    '#type' => 'textfield',
    '#title' => t('Warning Message'),
    '#default_value' => variable_get('auth_attempts_warning_message', 'If you fail the next atempt, you will need to reset your password.')
  );
  return system_settings_form($form);
}
