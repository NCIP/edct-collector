<?php

/**
 * Menu callback.
 * @return html
 */
function cacure_x_page_callback($context, $arg) {
  drupal_set_header('Content-type: text/xml; charset=UTF-8');
  drupal_set_header("X-UA-Compatible", "chrome=1");

  $restcall = new RestCall();
  $xml = $restcall->getForm($arg);

  $json = json_decode($xml);
  if (!is_null($json)) {
    $message = $json->message;
    $details = $json->details;
    drupal_set_message(t(ucfirst($message).'. '.ucfirst($details)), 'error');
    $_SESSION['xml_arg'] = array();
    return '';
  }

  $path_to_module = drupal_get_path('module', 'cacure_x');
  //echo team_devel_detect_browser(); exit();
  if (team_devel_detect_browser() == 'IE') {
    drupal_add_js(drupal_get_path('module', 'cacure_x') . '/js/chrome-frame.js');
  }

  drupal_add_css($path_to_module . '/xsltforms/dialog_box.css');



  // Hardcoded NCCCP context value
//  $context = 'ncccp';

  $xml = str_replace('<base-url>xform.view</base-url>',
          '<base-url>' . '/formsubmit/' . $context . '/' . $arg . '</base-url>', $xml);

  $string_open = strpos($xml, "<html");

  $xml = substr($xml, $string_open, strlen($xml));

  ini_set('pcre.backtrack_limit', 500000);
  
  preg_match('|<head>(.*)</head>|Uis', $xml, $out);
  $head = $out[1];
  
  $head = trim($head);

  //$head = '<head><xform:model>'.$out[1].'</xform:model></head>';
  //$xml = str_replace($out1[0], '', $xml);

  //preg_match('|<html(.)*>(.*)</html>|Uis', $xml, $out2);
  //preg_match('|<body(.)*>(.*)</body>|Uis', $out2[2], $out3);
  //$block = $out3[2];
  preg_match('|<body(.)*>(.*)</body>|Uis', $xml, $out);
  $block = $out[2];

  $block = str_replace('id="container"', 'class="xform-block"', $block);

  $_SESSION['xml_arg'] = array(
    'head' => $head,
    'body' => $block,
    'block_id' => $arg,
  );

  return '';
}

/**
 * Menu callback
 */
function cacure_x_formsubmit_callback($arg) {

  $arg = arg();
  $protocol = team_devel_getProtocol();
  $partialSave = 'true';

  $restcall = new RestCall();

  $action = isset($arg[3]) ? $arg[3] : null;
  
  if ($action) {
    $partialSave = 'false';
  }

  if ('next' == $action || 'prev' == $action || 'submit' == $action || 'save' == $action)
  {
    $response = $restcall->saveForm($arg[2], $partialSave);
  
    if ($response->status_message != 'OK') {
        drupal_set_message('Your form not saved. Please, try later..');
        echo '<script type="text/javascript"> parent.location.href="/' . $_GET['q'] . '"; </script>';
        die;
    }
  }
  
  if ($action) {
    $restcall = new RestCall();
    switch ($action) {
      case 'save' :
          $id = 'NONE';
          $p  = 'node/71';
          break;
      case 'prev' :
        $restcall->changeFormStatus($arg[2], 'submit');
        $id = $restcall->getPrevFormId($arg[2]);
        if ($id == 'NONE') {
          // get forwarding URL from settings
          $p = 'node/71';
        }
        break;
      case 'next' :
        $restcall->changeFormStatus($arg[2], 'submit');
        $id = $restcall->getNextFormId($arg[2]);
        if ($id == 'NONE') {
          // get forwarding URL from settings
          $p = 'submit-questionnaire/' . $arg[1];
        }
        break;
      case 'submit' :
        $restcall->changeFormStatus($arg[2], 'submit');
		$id = 'NONE';
		$p = 'node/71';
        break;
      case 'approve' :
        $restcall->changeFormStatus($arg[2], 'approve');
		$id = 'NONE';
		$p = 'node/71';
        break;
      case 'decline' :
        $restcall->changeFormStatus($arg[2], 'decline');
		$id = 'NONE';
		$p = 'node/71';
        break;
    }

    if ($id == 'NONE') {
      echo '<script type="text/javascript"> parent.location.href="/' . $p . '"; </script>';
      die;
    } else {
      $p = '/form/' . $arg[1] . '/' . $id;
      //print_r($id); die;
      echo '<script type="text/javascript"> parent.location.href="' . $p . '"; </script>';
      die;
    }

  } else {
    if (isset($arg[2])) {
      echo '<script type="text/javascript"> parent.location.href="' . '/form/' . $arg[1] . '/' . $arg[2] . '"; </script>';
      die;
    }
    else {
      echo 'Your form not saved. Please, try later..';
    }
  }
  die;
}
