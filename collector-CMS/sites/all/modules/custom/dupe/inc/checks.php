<?php
/**
 * search for a duplicate user in the
 * profile fields: first name, last name and dob
 */
function _check_dupe($data) {
    $profile_fields = _get_profile_fields();

    if (empty($profile_fields)) {
        return false;
    }

    $users = array();
    
    foreach ($profile_fields as $fname => $fvalue) {
        $users = _profile_search_field($profile_fields[$fname], $data[$fname], $users);
        if (empty($users)) return false;
    }

    $dupe_email = _user_email($users[0]);
    $dupe_msg = "<p>" . variable_get('dupe_message', 'Seems you already registered an account using %s!') . "</p>";
    $dupe_msg = sprintf($dupe_msg, $dupe_email);
    $dupe_msg .= '<p><a href="#" onclick="Drupal.dupe.dupeUserYes()">' . variable_get('dupe_message_yes', 'Yes, I own this email address.') . '</a></p>';
    $dupe_msg .= '<p><a href="#" onclick="Drupal.dupe.dupeUserNo()">' . variable_get('dupe_message_no', 'No, it\'s not mine.') . '</a></p>';

    form_set_error('profile_first_name', $dupe_msg);
    //drupal_set_message($dupe_msg, 'error');

    return $users[0]; // return the first one (possibly, the only one)

}

/**
 * check for passwd strength
 */
function _check_passwd($pass) {
    if (empty($pass)) return true; // drupal checks this
    $has_letters = preg_match("/[a-zA-Z]/", $pass);
    $has_numbers = preg_match("/[0-9]/", $pass);
    $has_punctuation = preg_match("/[^a-zA-Z0-9]/", $pass);
    $long_enough = (strlen($pass) >= 8 && strlen($pass) <= 20);
    if ($has_letters && $has_numbers && $has_punctuation && $long_enough) return true;
    if(strlen($pass) > 20){
      $dupe_msg = "<p>The password you are trying to use has more than 20 characters. Try using a shorter password.</p>";
    }else{
      $dupe_msg = "<p>The password you are trying to use is not strong enough. Try using at least one letter, one number and one special character.</p>";
    }
    
    form_set_error('pass', $dupe_msg);
    return false;
}

/**
 * check email
 */
function _check_email($email) {
    if (empty($email)) return true;
    if (eregi("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$", $email)) return true;
    $dupe_msg = "<p>You entered an invalid email address.</p>";
    form_set_error('profile_contact_email', $dupe_msg);
    return false;
}

/**
 * check for username strength
 */
function _check_username($user) {
    if (empty($user)) return true; // drupal checks this
    $has_letters = preg_match("/[a-zA-Z]/", $user);
    $has_numbers = preg_match("/[0-9]/", $user);
    $long_enough = (strlen($user) >= 2 && strlen($user) <= 20);
    if ($has_letters && $has_numbers && $long_enough) return true;

    $dupe_msg = "<p>User name must have at least 2 characters but not more than 20. User name must have at least one letter and one number. Special characters like $, #, @ are not accepted.</p>";
    form_set_error('name', $dupe_msg);
    return false;
}