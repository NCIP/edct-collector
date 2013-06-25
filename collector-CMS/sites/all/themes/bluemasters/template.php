<?php
// $Id: template.php,v 1.2 2010/12/28 21:18:31 skounis Exp $

/**
 * Sets the body-tag class attribute.
 *
 * Adds 'sidebar-left', 'sidebar-right' or 'sidebars' classes as needed.
 */
function phptemplate_body_class($sidebar_left, $sidebar_right) {
  if ($sidebar_left != '' && $sidebar_right != '') {
    $class = 'sidebars';
  }
  else {
    if ($sidebar_left != '') {
      $class = 'sidebar-left';
    }
    if ($sidebar_right != '') {
      $class = 'sidebar-right';
    }
  }

  if (isset($class)) {
    print ' class="'. $class .'"';
  }
}

/**
 * Return a themed breadcrumb trail.
 *
 * @param $breadcrumb
 *   An array containing the breadcrumb links.
 * @return a string containing the breadcrumb output.
 */
function phptemplate_breadcrumb($breadcrumb) {
  if (!empty($breadcrumb)) {
    return '<div class="breadcrumb">'. implode(' › ', $breadcrumb) .'</div>';
  }
}

/**
 * Allow themable wrapping of all comments.
 */
function phptemplate_comment_wrapper($content, $type = null) {
  static $node_type;
  if (isset($type)) $node_type = $type;

  if (!$content || $node_type == 'forum') {
    return '<div id="comments">'. $content . '</div>';
  }
  else {
    return '<div id="comments"><h2 class="comments">'. t('Comments') .'</h2>'. $content .'</div>';
  }
}

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

/**
 * GT
 * Returns the terms of a specific vocabulary.
 * Paremeters: Node id, Vocabulary id
 *
 */
function skodaxanthifc_print_only_terms_of($node, $vid) {
	$terms = taxonomy_node_get_terms_by_vocabulary($node, $vid);
	if ($terms) {
		$links = array();
		$output .= '';
		foreach ($terms as $term) {
			//$links[] = l($term->name, taxonomy_term_path($term), array('rel' => 'tag', 'title' => strip_tags($term->description)));
			$output .= $term->name;
		}
	//$output .= implode(', ', $links);
	$output .= ' ';
	}
	$output .= '';
	return $output;
}

/**
  * Theme override for search form.
  */
  function skodaxanthifc_search_theme_form($form) {

    unset($form['search_theme_form']['#title']);
    //$form['submit']['#value'] = '';
    //$form['search_theme_form']['#value'] = 'Search.';
   // $form['search_theme_form']['#attributes'] = array('onblur' => "if (this.value == '') {this.value = 'Search.';}", 'onfocus' => "if (this.value == 'Search.') {this.value = '';}" );

    $output .= drupal_render($form);
    return $output;
}
// Gets the User First Name via the UID for display purposes

function getName($uid)
{
	$query = "SELECT value FROM profile_values WHERE fid = 1 AND uid = '" . $uid . "'";  //Search for Name field via User ID field
	$result = db_query($query);
	$name = db_result($result);

	return $name;
}

//Removes white spaces and capitalization from variables for cleaner CSS

function classify($element)
{

$class = str_replace(' ', '-', $element); //Strips empty spaces and replaces with dashes
$class = strtolower($class); //makes letter lowercase for ease of CSS Styling

return $class;
}

/**
 * Override or insert PHPTemplate variables into the templates.
 */
function bluemasters_preprocess_page(&$vars) {
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

  // Classes for body element. Allows advanced theming based on context
  // (home page, node of certain type, etc.)
  $body_classes = array($vars['body_classes']);
  if (!$vars['is_front']) {
    // Add unique classes for each page and website section
    $path = drupal_get_path_alias($_GET['q']);
    list($section,) = explode('/', $path, 2);
    $body_classes[] = bluemasters_id_safe('page-' . $path);
    $body_classes[] = bluemasters_id_safe('section-' . $section);
    if (arg(0) == 'node') {
      if (arg(1) == 'add') {
        if ($section == 'node') {
          array_pop($body_classes); // Remove 'section-node'
        }
        $body_classes[] = 'section-node-add'; // Add 'section-node-add'
      }
      elseif (is_numeric(arg(1)) && (arg(2) == 'edit' || arg(2) == 'delete')) {
        if ($section == 'node') {
          array_pop($body_classes); // Remove 'section-node'
        }
        $body_classes[] = 'section-node-' . arg(2); // Add 'section-node-edit' or 'section-node-delete'
      }
    }
    // Add a unique class when viewing a node
    if (arg(0) == 'node' && is_numeric(arg(1))) {
      $body_classes[] = 'node-full-view'; // Add 'node-full-view'
    }
  }
  $vars['body_classes'] = implode(' ', $body_classes); // Concatenate with spaces

  if ($secondary = menu_secondary_local_tasks()) {
      $output = '<span class="clear"></span>';
      $output .= "<ul class=\"tabs secondary\">\n". $secondary ."</ul>\n";
      $vars['tabs2'] = $output;
    }

    // Hook into color.module
    if (module_exists('color')) {
      _color_page_alter($vars);
    }
  
}


function bluemasters_id_safe($string) {
  if (is_numeric($string{0})) {
    // If the first character is numeric, add 'n' in front
    $string = 'n' . $string;
  }
  return strtolower(preg_replace('/[^a-zA-Z0-9-]+/', '-', $string));
}