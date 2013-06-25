<?php

/**
 * call JS to show step1
 * when on step1 all errors in step2 fields must be hidden 
 */
function _show_step1($errors = null) {
    drupal_add_js("Drupal.behaviors.dupe = function (context) { Drupal.dupe.stepOne(); }", "inline");
    if (!empty($errors) && count($errors) > 0) {
        $step_fields = _get_step(2);
        if (count($step_fields) > 0) {
            foreach ($step_fields as $item2) {
                if (isset($errors[$item2])) _hide_error_message($errors[$item2]);
            }
        }
    }
}

/**
 * call JS to show step2
 * if we're showing step2 for the first time (right after leaving step1)
 * we need to hide all errors
 */
function _show_step2($clear_err = false) {
    if ($clear_err) {
        unset($_SESSION['messages']);
        drupal_add_js("Drupal.behaviors.dupe = function (context) { Drupal.dupe.clearErrors(); Drupal.dupe.stepTwo(); }", "inline");
    } else {
        drupal_add_js("Drupal.behaviors.dupe = function (context) { Drupal.dupe.stepTwo(); }", "inline");
    }
}

/**
 * get email for user
 */
function _user_email($uid) {
    $sql = "select * from {users} where uid = %d";
    $r = db_query($sql, $uid);
    $user = db_fetch_array($r);
    return $user['mail'];
}

/**
 * search a given value in the given profile field
 * and return the list of user ids
 */
function _profile_search_field($profile_field, $value, $in_ids = null) {
    if (_profile_field_serialize($profile_field['type'])) $value = serialize($value);
    $sql = "select uid from {profile_values} where fid = %d and value = '%s'";
    if (!empty($in_ids)) {
        $sql .= " and uid in (%s)";
        $r = db_query($sql, $profile_field['fid'], $value, implode(',', $in_ids));
    } else {
        $r = db_query($sql, $profile_field['fid'], $value);
    }
    $profile_values = array();
    while ($profile_value = db_fetch_array($r)) {
        $profile_values[] = $profile_value['uid'];
    }
    return $profile_values;
}

/**
 * get the profile fields data
 */
function _get_profile_fields() {
    $profile_fields = variable_get('dupe_profile_fields', 'profile_last_name,profile_first_name');
    if (empty($profile_fields)) return null;

    $profile_fields_arr = explode(',', $profile_fields);
    foreach ($profile_fields_arr as $idx => $profile_field) $profile_fields_arr[$idx] = "'" . trim($profile_field) . "'";
    $profile_fields_in = implode(',', $profile_fields_arr);

    $sql = "SELECT * FROM {profile_fields} WHERE name in ({$profile_fields_in}) and category = '%s'";
    $r = db_query($sql, 'Personal Info');
    $fields = array();
    while ($field = db_fetch_array($r)) {
        $fields[$field['name']] = $field;
    }
    return $fields;
}

/**
 * reading from the 'form' struct we can load the
 * field names into a SESSION array
 */
function _load_step_fields($step, $fields) {
    $names = array();
    foreach ($fields as $name => $value) if ($name{0} != '#') $names[] = $name;
    $_SESSION['dupeFields'][$step] = $names;
}

/**
 * used to get the fields for each step
 * and then do validations and related stuff
 * reads from data loaded in _load_step_fields()
 */
function _get_step($i) {
    return $_SESSION['dupeFields'][$i];
}

/**
 * this function hides an error from the SESSION
 * the error remains there, but the user won't see it
 */
function _hide_error_message($to_remove) {
    $current_errors = $_SESSION['messages']['error'];
    $new_errors = array();
    foreach ($current_errors as $current_error) {
        if ($current_error != $to_remove) $new_errors[] = $current_error;
    }
    $_SESSION['messages']['error'] = $new_errors;
}

/**
 * this function checks if EVERY item in "fields" is not in "errors"
 * it's used to hide errors from step2 when on step1
 */
function _check_if_all_valid($errors, $fields) {
    foreach ($fields as $f) {
        if (isset($errors[$f])) return false;
    }
    return true;
}

/**
 * writes fields descriptions
 */
function _set_legends(&$form) {

    $hide = "The content of this field is kept private and will not be shown publicly.";
    $steps = array(
                'Personal Info',
                'Alternate Contact Info',
                'account');
    foreach ($steps as $step) {
        if (isset($form[$step])) {
            foreach ($form[$step] as $field => $value) {
                if ($field{0} != '#') {
                    $form[$step][$field]['#description'] = str_replace($hide, "", $form[$step][$field]['#description']);
                    if (empty($form[$step][$field]['#description'])) {
                        unset($form[$step][$field]['#description']);
                    }
                }
            }
        }
    }

    $form['account']['name']['#description'] = '
        <h3>Rules and Instructions</h3>
        <b>Username Needs:</b><br/>
        <ul>
            <li>2 to 20 characters</li>
            <li>At least 1 letter and 1 number</li>
			<li>NO special characters (like $, #, @, ! )</li>
            <li>Case sensitive</li>
        </ul>
        ';
    //$form['account']['name']['#description'] = "User name must have at least 2 characters but not more than 20. User name must have at least one letter and one number. Special characters like $, #, @ are not accepted.";

    $form['account']['pass']['#description'] = '
        <b>Password Needs:</b><br/>
        <ul>
            <li>8 to 20 characters</li>
            <li>2 letters</li>
            <li>Upper AND lower case</li>
            <li>1 number</li>
			<li>1 special character (like $, #, @, !)</li>
            <li>Case sensitive</li>
            <li>High strength</li>
        </ul>
    ';
    //$form['account']['pass']['#description'] = "Password must have at least 8 characters but not more than 20. Password must have at least one letter, one number and one special character such as $, # or !.";

    $form['account']['mail']['#description'] = "";

}
