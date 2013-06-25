<?php
// $Id: phpcaptcha.admin.inc,v 1.1.2.1 2009/08/10 17:50:02 robloach Exp $

/**
 * @file
 * Provides the phpcaptcha administration settings.
 */

/**
 * Form callback; administrative settings for phpcaptcha.
 */
function phpcaptcha_admin_settings() {

  $form = array();
  $form['phpcaptcha__code_length'] = array(
    '#type' => 'textfield',
    '#title' => t('Code Lenght'),
    '#default_value' => variable_get('phpcaptcha__code_length', 6),
    '#maxlength' => 4,
    '#description' => t('Number of characters in the generated text code', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_image_width'] = array(
    '#type' => 'textfield',
    '#title' => t('Image Width'),
    '#default_value' => variable_get('phpcaptcha_image_width', '200'),
    '#maxlength' => 4,
    '#description' => t('Captcha image width', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_image_height'] = array(
    '#type' => 'textfield',
    '#title' => t('Image Height'),
    '#default_value' => variable_get('phpcaptcha_image_height', '90'),
    '#maxlength' => 4,
    '#description' => t('Captcha image height', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_perturbation'] = array(
    '#type' => 'textfield',
    '#title' => t('Image perturbation'),
    '#default_value' => variable_get('phpcaptcha_image_perturbation', '0.9'),
    '#maxlength' => 5,
    '#description' => t('1.0 = high distortion, higher numbers = more distortion', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_image_bg_color'] = array(
    '#type' => 'textfield',
    '#title' => t('Image Background Color'),
    '#default_value' => variable_get('phpcaptcha_image_bg_color', '#0099CC'),
    '#maxlength' => 7,
    '#description' => t('Captcha image background color', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_text_color'] = array(
    '#type' => 'textfield',
    '#title' => t('Image Text Color'),
    '#default_value' => variable_get('phpcaptcha_text_color', '#EAEAEA'),
    '#maxlength' => 7,
    '#description' => t('Captcha text color', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_text_transparency_percentage'] = array(
    '#type' => 'textfield',
    '#title' => t('Text Transparency'),
    '#default_value' => variable_get('phpcaptcha_text_transparency_percentage', '10'),
    '#maxlength' => 3,
    '#description' => t('100 = completely transparent', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_num_lines'] = array(
    '#type' => 'textfield',
    '#title' => t('Number of lines'),
    '#default_value' => variable_get('phpcaptcha_num_lines', '8'),
    '#maxlength' => 2,
    '#description' => t('How many lines to draw over text.', array()),
    '#required' => TRUE,
   );
  $form['phpcaptcha_line_color'] = array(
    '#type' => 'textfield',
    '#title' => t('Line Color'),
    '#default_value' => variable_get('phpcaptcha_line_color', '#0000CC'),
    '#maxlength' => 7,
    '#description' => t('Line color', array()),
    '#required' => TRUE,
   );

  return system_settings_form($form);
}

/**
 * Validation function for phpcaptcha_admin_settings().
 *
 * @see phpcaptcha_admin_settings()
 */
function phpcaptcha_admin_settings_validate($form, &$form_state) {
  $tabindex = $form_state['values']['phpcaptcha_tabindex'];
  if (!empty($tabindex) && !is_numeric($tabindex)) {
    form_set_error('phpcaptcha_tabindex', t('The Tab Index must be an integer.'));
  }
}
