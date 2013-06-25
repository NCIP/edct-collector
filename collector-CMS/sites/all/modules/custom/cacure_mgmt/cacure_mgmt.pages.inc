<?php
/**
 * Menu callback.
 */
function cacure_mgmt_submit_path_callback(){
  return theme('box', t('Paths settings'), drupal_get_form('cacure_mgmt_submit_path_form'));
}

/**
 * Form construct.
 */
function cacure_mgmt_submit_path_form(){
  $form = array();

  $def_1 = variable_get('cacure_mgmt_submit_path', '');
  $def_1 = $def_1 == '/' ? '<front>' : $def_1;

  $def_2 = variable_get('cacure_mgmt_afterlast_path', '');
  $def_2 = $def_2 == '/' ? '<front>' : $def_2;

  $form['submit_path'] = array(
    '#type' => 'textfield',
    '#title' => 'After module submit',
    '#default_value' => $def_1,
    '#description' => t('Please, enter new redirect path after submit. You should use only internal urls, like "user/logout" or "<front>"')
  );

  $form['afterlast_path'] = array(
    '#type' => 'textfield',
    '#title' => 'After save form',
    '#default_value' => $def_2,
    '#description' => t('Please, enter new redirect path after user saves a last form. You should use only internal urls, like "user/logout" or "<front>"')
  );

  $form['submit'] = array(
    '#type' => 'submit',
    '#value' => 'Save'
  );
  
  return $form;
}

/**
 * Form submit.
 */
function cacure_mgmt_submit_path_form_submit($form, &$form_state){
  variable_set('cacure_mgmt_submit_path', $form_state['values']['submit_path']);
  variable_set('cacure_mgmt_afterlast_path', $form_state['values']['afterlast_path']);
  drupal_set_message('All paths has been saved.', 'status');
}

/**
 * Menu callback().
 */
function cacure_mgmt_submitmodule_callback($context){
	//print_r($_POST);
  if(isset($_POST['form_id'])){
    $restcall = new RestCall();
    $response = $restcall->changeModuleStatus($_POST['form_id'], 'submit');
	if($response == 'OK'){
      global $user;
      $account = user_load($user->uid);
      //print_r($user);print_r($account->profile_first_name); die;
      $vars = array(
        'username' => $account->profile_first_name,
        'to' => $user->mail, 
		'goto' => variable_get('cacure_mgmt_submit_path', '')
      );
      //$mail = new MailSender();
      //$mail->sendSubmitModule($vars);
      //$vars['formname_nocomplite'] = 'Custom form name';
      //$vars['link_nocomplite'] = '<a href="http://www.google.com/">Link</a>';
      //$vars['goto'] = variable_get('cacure_mgmt_submit_path', '');;
      //$mail = new MailSender();
      //$mail->sendReminderSubmit($vars);
    } else {
      drupal_set_message(t('Your order has not been sent.'), 'error');
      drupal_goto('<front>');
    }
  }
}

function cacure_mgmt_submit_questionnaire_callback($context){
  return "";
}
/**
 *  Menu callback.
 */
function management_questionnaire_callback($arg){
  return 'Questionnaire ';
}

/**
 * Menu callback.
 */
function management_page() {
    return '';
}
