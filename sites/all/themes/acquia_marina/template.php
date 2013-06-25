<?php
// $Id: template.php,v 1.16.2.3 2010/05/11 09:41:22 goba Exp $



// Gets the User First Name via the UID for display purposes

function getName($uid)
{
	$query = "SELECT value FROM profile_values WHERE fid = 1 AND uid = '" . $uid . "'";  //Search for Name field via User ID field
	$result = db_query($query);
	$name = db_result($result);
	
	return $name;
}



//Adds a span tag inside the legend so we can control the legend width and positioning
function acquia_marina_fieldset($element) {

  if (!empty($element['#collapsible'])) {
    drupal_add_js('misc/collapse.js');

    if (!isset($element['#attributes']['class'])) {
      $element['#attributes']['class'] = '';
    }

    $element['#attributes']['class'] .= ' collapsible';
    if (!empty($element['#collapsed'])) {
      $element['#attributes']['class'] .= ' collapsed';
    }
  }

  return '<fieldset'. drupal_attributes($element['#attributes']) .'>'. ($element['#title'] ? '<legend><span>'. $element['#title'] .'</span></legend>' : '') . (isset($element['#description']) && $element['#description'] ? '<div class="description">'. $element['#description'] .'</div>' : '') . (!empty($element['#children']) ? $element['#children'] : '') . (isset($element['#value']) ? $element['#value'] : '') ."</fieldset>\n";
}
// End Legend function

function phptemplate_form_element() {
  $args = func_get_args();
  $arg = arg();
  //print_r(arg());die;
  if($args['0']['#type'] == 'radios' && $arg['0'] == 'user' && $arg['1'] == 'register')
    return str_replace(':', '', call_user_func_array('theme_form_element', $args));
  return call_user_func_array('theme_form_element', $args);
}

//Removes white spaces and capitalization from variables for cleaner CSS 

function classify($element) 
{
	
$class = str_replace(' ', '-', $element); //Strips empty spaces and replaces with dashes 
$class = strtolower($class); //makes letter lowercase for ease of CSS Styling 

return $class;
}

//Removes slashes from uri and capitalization from variables for cleaner CSS 

function change_uri($element) 
{
	
$class = str_replace('/', '-', $element); //Strips empty spaces and replaces with dashes 
$class = strtolower($class); //makes letter lowercase for ease of CSS Styling 
$class = substr($class, 1);
if (substr($class, 0, 8) == 'editform') 
{
$class = 'editform';
}
return $class;
}

// Allows page title access for nodes

function acquia_marina_preprocess_node(&$variables) {  
  $variables['page_title'] = drupal_get_title();
//  $node = $vars['node'];  
//  $vars['template_file'] = 'node-'. $node->nid;
  /*
    global $theme;
  // Populate all block regions.
  $regions = system_region_list($theme);

    foreach (array_keys($regions) as $region) {
    // Prevent left and right regions from rendering blocks when 'show_blocks' == FALSE.
    if (!($region == 'left' || $region == 'right')) {
      $blocks = theme('blocks', $region);
    }
    else {
      $blocks = '';
    }
    // Assign region to a region variable.
    isset($variables[$region]) ? $variables[$region] .= $blocks : $variables[$region] = $blocks;
  }

  */
}


//  Alloes page title access for blocks
function phptemplate_preprocess_block(&$variables) { 
  $variables['page_title'] = drupal_get_title();
  
}


/**
* Override theme_links to include <span> in list.
*/
function phptemplate_link($links, $attributes = array('class' => 'links')) {

  $output = '';
  if (count($links) > 0) {
    $output = '<ul'. drupal_attributes($attributes) .'>';
    $num_links = count($links);
    $i = 1;
    foreach ($links as $key => $link) {
      $class = '';
      // Automatically add a class to each link and also to each LI
      if (isset($link['attributes']) && isset($link['attributes']['class'])) {
        $link['attributes']['class'] .= ' ' . $key;
        $class = $key;
      }
      else {
        $link['attributes']['class'] = $key;
        $class = $key;
      }
      // Add first and last classes to the list of links to help out themers.
      $extra_class = '';
      if ($i == 1) {
        $extra_class .= 'first ';
      }
      if ($i == $num_links) {
        $extra_class .= 'last ';
      }
      // Add class active to active li
      $current = '';
      if (strstr($class, 'active')) {
        $current = ' active';
      }
      $output .= '<li class="'. $extra_class . $class . $current .'"><span>';
      // Is the title HTML?
      $html = isset($link['html']) && $link['html'];
      // Initialize fragment and query variables.
      $link['query'] = isset($link['query']) ? $link['query'] : NULL;
      $link['fragment'] = isset($link['fragment']) ? $link['fragment'] : NULL;
      if (isset($link['href'])) {
        $output .= l($link['title'], $link['href'], $link['attributes'], $link['query'], $link['fragment'], FALSE, $html);
      }
      else if ($link['title']) {
        //Some links are actually not links, but we wrap these in <span> for adding title and class attributes
        if (!$html) {
          $link['title'] = check_plain($link['title']);
        }
        $output .= '<span'. drupal_attributes($link['attributes']) .'>'. $link['title'] .'</span>';
      }
      $i++;
      $output .= "</span></li>\n";
    }
    $output .= '</ul>';
  }
  return $output;
}



function acquia_marina_menu_item($link, $has_children, $menu = '', $in_active_trail = FALSE, $extra_class = NULL) {
  $class = ($menu ? 'expanded' : ($has_children ? 'collapsed' : 'leaf'));
  if (!empty($extra_class)) {
    $class .= ' '. $extra_class;
  }
  if ($in_active_trail) {
    $class .= ' active-trail-' . $has_children;
  }


    /*    if (isset($link['attributes']) && isset($link['attributes']['class'])) {
        $link['attributes']['class'] .= ' ' . $key;
        $temp = $key;
      }
      else {
        $link['attributes']['class'] = $key;
        $temp = $key;
      }
        if (strstr($temp, 'active')) {
        $class .= ' current';
      }
	  */
  

  return '<li class="'. $class .'">'. $link . $menu ."</li>\n";
}

  /**
   * Theme override for user edit form.
   *
   * The function is named themename_formid.
   */

/**
* This snippet will register a theme implementation of the user login form.
*
* Drupal 6.x only!
* Replace 'mytheme' with the name of your theme.
*/


/*
function acquia_marina_theme() {
  return array(
    // The form ID.
    'user_login_block' => array(
      // Forms always take the form argument.
      'arguments' => array('form' => NULL),
    ),
  );
}
*/
/**
* Theme override for user login block.
*
* The function is named themename_formid.
*/
/*
function acquia_marina_user_login_block($form) {
	//print_r($form);

  $form['name']['#title'] = t('user name'); //sets the Label Name of the user name field
  $form['pass']['#title'] = t('password'); //sets the value parameter if the password field
   $form['submit']['#value'] = t(''); //Changes value of submit button
   

   // $form['name']['#attributes'] = array(
     // 'onfocus' => "if (this.value == 'user name') {this.value = '';}", //Allows default value of a field to return after onblur event
      //'onblur' => "if (this.value == '') {this.value = 'user name';}");

  // Adds Li elements to the links as well as add titles and change link syntax for usability
 	$form['links']['#value'] =  '<ul><li>' . l(t('Forgot User Name / Password? >'), 'user/password', array('attributes' => array('title' => 'Forgot User name or Password?'))) . '</li>'; 
	$form['links'][0]['#value'] =  '<li class=\'last\'>Don\'t Have an Account Yet?' . l(t('Sign up today. >'), 'user/register', array('attributes' => array('title' => 'Sign up Today'))) . '</li></ul>';
	
  return (drupal_render($form));
}
*/

/**
 * Sets the body-tag class attribute.
 *
 * Adds 'sidebar-left', 'sidebar-right' or 'sidebars' classes as needed.
 */
 
 

drupal_rebuild_theme_registry();

function phptemplate_body_class($left, $right) {
  if ($left != '' && $right != '') {
    $class = 'sidebars';
  }
  else {
    if ($left != '') {
      $class = 'sidebar-left';
    }
    if ($right != '') {
      $class = 'sidebar-right';
    }
  }

  if (isset($class)) {
    print ' class="'. $class .'"';
  }
}

/*
function phptemplate_body_class($left, $right) {
  if ($left != '' && $right != '') {
    $class = 'sidebars';
  }
  else {
    if ($left != '') {
      $class = 'sidebar-left';
    }
    if ($right != '') {
      $class = 'sidebar-right';
    }
  }

  if (isset($class)) {
    print ' class="'. $class .'"';
  }
}
*/
/**
 * Return a themed breadcrumb trail.
 *
 * @param $breadcrumb
 *   An array containing the breadcrumb links.
 * @return a string containing the breadcrumb output.
 */
 /*
function phptemplate_breadcrumb($breadcrumb) {
  if (!empty($breadcrumb)) {
    return '<div class="breadcrumb">'. implode(' › ', $breadcrumb) .'</div>';
  }
}
*/
/**
 * Override or insert PHPTemplate variables into the templates.
 */
function phptemplate_preprocess_page(&$vars) {
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

  $vars['tabs2'] = menu_secondary_local_tasks();

  // Hook into color.module
  if (module_exists('color')) {
    _color_page_alter($vars);
  }
}
/**
 * Add a "Comments" heading above comments except on forum pages.
 */
 /*
function garland_preprocess_comment_wrapper(&$vars) {
  if ($vars['content'] && $vars['node']->type != 'forum') {
    $vars['content'] = '<h2 class="comments">'. t('Comments') .'</h2>'.  $vars['content'];
  }
}
*/
/**
 * Returns the rendered local tasks. The default implementation renders
 * them as tabs.
 *
 * @ingroup themeable
 */
function phptemplate_menu_local_tasks() {
  $output = '';

  if ($primary = menu_primary_local_tasks()) {
    $output .= "<ul class=\"tabs primary\">\n". $primary ."</ul>\n";
  }

  return $output;
}

/**
 * Returns the themed submitted-by string for the comment.
 */
function phptemplate_comment_submitted($comment) {
  return t('!datetime — !username',
    array(
      '!username' => theme('username', $comment),
      '!datetime' => format_date($comment->timestamp)
    ));
}

/**
 * Returns the themed submitted-by string for the node.
 */
function phptemplate_node_submitted($node) {
  return t('!datetime — !username',
    array(
      '!username' => theme('username', $node),
      '!datetime' => format_date($node->created),
    ));
}

/**
 * Generates IE CSS links for LTR and RTL languages.
 */
function phptemplate_get_ie_styles() {
  global $language;

  $iecss = '<link type="text/css" rel="stylesheet" media="all" href="'. base_path() . path_to_theme() .'/fix-ie.css" />';
  if ($language->direction == LANGUAGE_RTL) {
    $iecss .= '<style type="text/css" media="all">@import "'. base_path() . path_to_theme() .'/fix-ie-rtl.css";</style>';
  }

  return $iecss;
}
