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
function aim_preprocess_page(&$vars) {
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
}