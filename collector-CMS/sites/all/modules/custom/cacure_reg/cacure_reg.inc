<?php
// File with includes.

/**
 * Function get new identifier.
 * @return string.
 */
function cacure_reg_get_identifier(){
  $restcall = new RestCall();
  return $restcall->getNewEntityInNewGroup($_POST['name']);
}

/**
 * Function save identifier.
 * @param  string $identifier
 * @param  array $account
 * @return void
 */
function cacure_reg_save_identifier($identifier, &$account){
  // Set account identifier in user obj.
  $account->identifier = $identifier;
  
  //get group id
  $restcall = new RestCall();
  $grpid = $restcall->getGroupId($_POST['name']);

  // Write record in db table.
  $query = 'INSERT INTO cc_user (uid, identifier, grpid)
      VALUES ('.$account->uid.', \''.$account->identifier.'\', \''.$grpid.'\') ON DUPLICATE KEY UPDATE identifier = \''.$account->identifier.'\'';
  db_query($query);
  //drupal_write_record('cc_user', $account);
}

/**
 * Function load identifier.
 */
function cacure_reg_load_identifier(&$account){

  // Load identifier from db
  $result = db_result(db_query('SELECT cc_user.identifier FROM {cc_user} cc_user WHERE cc_user.uid = %d', $account->uid));

  // if user registered, but not have identifier
  if(user_is_logged_in() && empty($result)){
    // Get custom identifier.
    $result = $identifier = cacure_reg_get_identifier();
    // Save identifier
    cacure_reg_save_identifier($identifier, $account);
  }

  // Set account identifier in user obj.
  $account->identifier = empty($result) ? false : $result;
}
