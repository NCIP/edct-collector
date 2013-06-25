<?php
// $Id: user_delete.admin.inc,v 1.1.2.4 2010/01/12 07:44:29 sanduhrs Exp $

/**
 * @file
 * User delete - Administration page
 *
 * @author
 * Stefan Auditor <stefan.auditor@erdfisch.de>
 */

/**
 * Administrative settings page
 *
 * @return array
 *   a form array
 */
function user_delete_settings() {
  //TODO: add additional settings based on http://drupal.org/node/8#comment-628434
  $form['defaults'] = array(
    '#type' => 'fieldset',
    '#title' => t('Defaults'),
  );
  $form['defaults']['user_delete_default_action'] = array(
    '#type' => 'select',
    '#title' => t('Default action when deleting'),
    '#default_value' => variable_get('user_delete_default_action', 0),
    '#options' => array(
        0 => t('Let users choose action'),
        'user_delete_block' => t('Disable the account and keep all content.'),
        'user_delete_block_unpublish' => t('Disable the account and unpublish all content.'),
        'user_delete_reassign' => t('Delete the account and make all content belong to the <em>Anonymous user</em>.'),
        'user_delete_delete' => t('Delete the account and all content.'),
      ),
  );
  $form['defaults']['user_delete_block_text'] = array(
    '#type' => 'textfield',
    '#title' => t('Default Delete Block Message'),
    '#default_value' => variable_get('user_delete_block_text', 'The account will be disabled, all submitted content will be kept.'),
    '#description' => t(''),
    '#size' => 100,
  );
  $form['defaults']['user_delete_block_unpublish_text'] = array(
    '#type' => 'textfield',
    '#title' => t('Default Delete Block Unpublish Message'),
    '#default_value' => variable_get('user_delete_block_unpublish_text', 'The account will be disabled, all submitted content will be unpublished.'),
    '#description' => t(''),
    '#size' => 100,
  );
  $form['defaults']['user_delete_reassign_text'] = array(
    '#type' => 'textfield',
    '#title' => t('Default Delete Reassign Message'),
    '#default_value' => variable_get('user_delete_reassign_text', 'The account will be deleted, all submitted content will be reassigned to the <em>Anonymous user</em>. This action cannot be undone.'),
    '#description' => t(''),
    '#size' => 100,
  );
  $form['defaults']['user_delete_delete_text'] = array(
    '#type' => 'textfield',
    '#title' => t('Default Delete Message'),
    '#default_value' => variable_get('user_delete_delete_text', 'The account and all submitted content will be deleted. This action cannot be undone.'),
    '#description' => t(''),
    '#size' => 100,
  );
  
  // getting account
  global $user;
  $account = user_load($user->uid);
  $default = t('User record deleted. All submitted content from %name has been deleted.', array('%name' => check_plain($account->name), '!anonymous' => variable_get('anonymous', t('Anonymous'))));
  
  $form['defaults']['user_delete_confirmation_text'] = array(
    '#type' => 'textfield',
    '#title' => t('Default Delete Confirmation Message'),
    '#default_value' => variable_get('user_delete_confirmation_text', $default),
    '#description' => t(''),
    '#size' => 100,
  );
  
  // custom fields
  $form['defaults']['user_delete_button_text'] = array(
    '#type' => 'textfield',
    '#title' => t('Default Delete Button Text'),
    '#default_value' => variable_get('user_delete_button_text', 'Delete Account'),
    '#description' => t('This is the default text for the delete button that the user will click on to cancel their account.'),
  );
  
  
  $form['redirect'] = array(
    '#type' => 'fieldset',
    '#title' => t('Redirect'),
  );
  $form['redirect']['user_delete_redirect'] = array(
    '#type' => 'textfield',
    '#title' => t('Redirection page'),
    '#default_value' => variable_get('user_delete_redirect', ''),
    '#description' => t('Choose where to redirect your users after account deletion. Any valid Drupal path will do, e.g. %front or %node', array('%front' => '<front>', '%node' => 'node/1')),
  );
  $form['backup'] = array(
    '#type' => 'fieldset',
    '#title' => t('Backup'),
  );
  $form['backup']['user_delete_backup'] = array(
    '#type' => 'checkbox',
    '#title' => t('Backup data'),
    '#default_value' => variable_get('user_delete_backup', 0),
    '#description' => t('Backup data that is being deleted to the filesystem.'),
  );
  $options = array(
    60*60*24*7 => format_interval(60*60*24*7, 2),
    60*60*24*7*2 => format_interval(60*60*24*7*2, 2),
    60*60*24*7*4 => format_interval(60*60*24*7*4, 2),
    60*60*24*7*8 => format_interval(60*60*24*7*8, 2),
    60*60*24*7*12 => format_interval(60*60*24*7*12, 2),
    60*60*24*7*16 => format_interval(60*60*24*7*16, 2),
    60*60*24*7*24 => format_interval(60*60*24*7*24, 2),
  );
  $form['backup']['user_delete_backup_period'] = array(
    '#type' => 'select',
    '#title' => t('Keep backup time'),
    '#default_value' => variable_get('user_delete_backup_period', 60*60*24*7*12),
    '#options' => $options,
    '#description' => t('The time frame after which the backup should be deleted from the filesystem.'),
  );
  return system_settings_form($form);
}
