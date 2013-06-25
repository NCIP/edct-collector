<?php

function ipost_settings_form($form_state) {
    
    $form = array();
    $form['ipost_client_token'] = array(
        '#type' => 'textfield',
        '#title' => t('Client Token'),
        '#description' => t('The client_token is a unique abbreviation used to represent your client in the iPost system. It is chosen by iPost staff when your iPost client account is set up. You can see your client token by clicking on the Client tab under Settings where <strong>Client Token</strong> is displayed near the top. '),
        '#default_value' => variable_get('ipost_client_token', ''),
    );
    $form['ipost_mail_list'] = array(
        '#type' => 'textfield',
        '#title' => t('Email List'),
        '#description' => t('The email distribution list to add/update information to.'),
        '#default_value' => variable_get('ipost_mail_list', ''),
    );
    $form['debug'] = array(
        '#type' => 'fieldset',
        '#title' => 'Debug Options',
        '#collapsible' => true,
        '#collapsed' => true,
    );
    $form['debug']['ipost_debug_enabled'] = array(
        '#type' => 'checkbox',
        '#title' => t('Enable Debug'),
        '#description' => 'Enabling debug will add a debug variable to Ipost requests. <strong>No data will be saved to Ipost while this checked!</strong>',
        '#default_value' => variable_get('ipost_debug_enabled', 0),
    );
    $form['debug']['ipost_debug_output'] = array(
        '#type' => 'checkbox',
        '#title' => t('Enable Debug Output'),
        '#description' => 'Debug output will be printed as a drupal message. This will also show https requests in Charles.',
        '#default_value' => variable_get('ipost_debug_output', 0),
    );
    $form['debug']['ipost_log'] = array(
        '#type' => 'checkbox',
        '#title' => t('Enable Logging'),
        '#description' => 'The status of ipost calls will be sent to watchdog and can be viewed if logging modules are enabled.',
        '#default_value' => variable_get('ipost_log', 0),
    );
    return system_settings_form($form);

}