<?php
// $Id: template.php,v 1.1 2010/10/26 23:22:22 aross Exp $

/**
 * Maintenance page preprocessing
 */
function mix_and_match_preprocess_maintenance_page(&$vars) {
  mix_and_match_preprocess_page($vars);
}

// Gets the User First Name via the UID for display purposes

function getName($uid)
{
	$query = "SELECT value FROM profile_values WHERE fid = 1 AND uid = '" . $uid . "'";  //Search for Name field via User ID field
	$result = db_query($query);
	$name = db_result($result);

	return $name;
}

/**
 * Page preprocessing
 */
function mix_and_match_theme(){
	  return array(
    'grid_row_mm' => array(
      'arguments' => array('element' => NULL, 'name' => NULL, 'class' => NULL, 'width' => NULL),
    ),
    'grid_block_mm' => array(
      'arguments' => array('element' => NULL, 'name' => NULL),
    ));
}
function mix_and_match_preprocess_page(&$vars) {
  $path = drupal_get_path_alias($_GET['q']);
  // Add body classes for custom design options
  $body_classes = explode(' ', $vars['body_classes']);
  $body_classes[] = theme_get_setting('mix_and_match_body_bg');
  $body_classes[] = theme_get_setting('mix_and_match_accent_color');
  $body_classes[] = theme_get_setting('mix_and_match_footer_color');
  $body_classes[] = theme_get_setting('mix_and_match_header_color');
  $body_classes[] = theme_get_setting('mix_and_match_link_color');
  $body_classes[] = theme_get_setting('mix_and_match_corners');
  $body_classes[] = mix_and_match_id_safe('page-' . $path);
  $body_classes = array_filter($body_classes);   
  $vars['body_classes'] = implode(' ', $body_classes);
  $vars['page_color'] = theme_get_setting('mix_and_match_page_bg');

  global $user;
  if ($user->uid == '0') {
    //if ($_GET['test'] == '1') {
	  //print('<!--').print_r($vars).print('-->');
	//}
	if(module_exists('path'))
    {
      //gets the "clean" URL of the current page
      $alias = drupal_get_path_alias($_GET['q']);
	  if ($alias == 'my-account/my-account-overview' || $vars['node']->nid == "71") {
	    drupal_goto('user/login');
	  }
	}
  }
  else {
    // check for the consent forms for the join process and, if the user is logged in, redirect them to the logged-in alias
	if ($vars['node']->nid){
	  if ($vars['node']->nid == "46" || $vars['node']->nid == "3") {
	    drupal_goto('node/109');
	  }
    }
  }
}


/**
 * Search form preprocessing
 */
function mix_and_match_preprocess_search_theme_form(&$vars) {
  $vars['accent_color'] = theme_get_setting('mix_and_match_accent_color');
}
function classify($element) {
  $class = str_replace(' ', '-', $element); //Strips empty spaces and replaces with dashes
  $class = strtolower($class); //makes letter lowercase for ease of CSS Styling
  return $class;
}
function mix_and_match_grid_row_mm($element, $name, $class='', $width='', $extra='') {
  $output = '';
  $extra = ($extra) ? ' ' . $extra : '';
  if ($element) {
    if ($class == 'full-width') {
      $output .= '<div id="' . $name . '-wrapper" class="' . $name . '-wrapper full-width">' . "\n";
      $output .= '<div id="' . $name . '" class="' . $name . ' row ' . $width . $extra . '">' . "\n";
    }
    else {
      $output .= '<div id="' . $name . '" class="' . $name . ' row ' . $class . ' ' . $width . $extra . '">' . "\n";
    }
    $output .= '<div id="' . $name . '-inner" class="' . $name . '-inner inner clearfix">' . "\n";
    $output .= $element;
    $output .= '</div><!-- /' . $name . '-inner -->' . "\n";
    $output .= '</div><!-- /' . $name . ' -->' . "\n";
    $output .= ($class == 'full-width') ? '</div><!-- /' . $name . '-wrapper -->' . "\n" : '';
  }
  return $output;
}

function mix_and_match_grid_block_mm($element, $name) {
  $output = '';
  if ($element) {
    $output .= '<div id="' . $name . '" class="' . $name . ' block">' . "\n";
    $output .= '<div id="' . $name . '-inner" class="' . $name . '-inner inner clearfix">' . "\n";
    $output .= $element;
    echo $element; drupal_die();
    $output .= '</div><!-- /' . $name . '-inner -->' . "\n";
    $output .= '</div><!-- /' . $name . ' -->' . "\n";
  }
  return $output;
}


/**
 * Converts a string to a suitable html ID attribute.
 *
 * - Preceeds initial numeric with 'n' character.
 * - Replaces space and underscore with dash.
 * - Converts entire string to lowercase.
 * - Works for classes too!
 *
 * @param string $string
 *   The string
 * @return
 *   The converted string
 */
function mix_and_match_id_safe($string) {
  if (is_numeric($string{0})) {
    // If the first character is numeric, add 'n' in front
    $string = 'n' . $string;
  }
  return strtolower(preg_replace('/[^a-zA-Z0-9-]+/', '-', $string));
}