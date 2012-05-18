<?php

function notes_page() {
    global $user;
    $output = theme('notes', notes_get_user_notes($user->uid));
    return $output;
}

function notes_form_add($form_state) {    
    $form['title'] = array(
		'#type' => 'textfield',
		'#title' => t('Title'),
        '#required' => true,
	);
	$form['body'] = array(
		'#type' => 'textarea',
		'#title' => t('Body'),
	    '#required' => true,
	);
	$form["submit"] = array(
		'#type' => 'submit',
		'#value' => t('Add'),
	);
	$form['cancel'] = array(
		'#type' => 'button',
		'#attributes' => array('onClick' => 'location.replace("'. referer_uri() .'"); return false;'),
		'#value' => t('Cancel'),
    );
	$form['#redirect'] = 'notes';
	return $form;
    
}

function notes_form_add_submit($form, &$form_state) {
    global $user;
    $data = array(
    	'uid' => $user->uid,
    	'created' => time(),
    	'title' => $form_state['values']['title'],
    	'body' => $form_state['values']['body']
    );
    if(drupal_write_record('notes', $data) == 1) drupal_set_message(t('Your note has been added.'));
    else drupal_set_message(t('There was a problem adding this note.'));
}

function notes_form_edit($form_state, $note) {    
    $form['title'] = array(
		'#type' => 'textfield',
		'#title' => t('Title'),
        '#required' => true,
        '#default_value' => $note->title,
	);
	$form['body'] = array(
		'#type' => 'textarea',
		'#title' => t('Body'),
	    '#required' => true,
	    '#default_value' => $note->body,
	);
	$form["nid"] = array(
	    '#type' => 'hidden',
	    '#value' => $note->nid,
	);
	$form["submit"] = array(
		'#type' => 'submit',
		'#value' => t('Save'),
	);
	$form['cancel'] = array(
		'#type' => 'button',
		'#attributes' => array('onClick' => 'location.replace("'. referer_uri() .'"); return false;'),
		'#value' => t('Cancel'),
    );
	$form['#redirect'] = 'notes';
	return $form;
    
}

function notes_form_edit_submit($form, &$form_state) {
    global $user;
    $data = array(
    	'nid' => $form_state['values']['nid'],
    	'title' => $form_state['values']['title'],
    	'body' => $form_state['values']['body']
    );
    if(drupal_write_record('notes', $data, 'nid') == 2) drupal_set_message(t('Your note has been updated.'));
    else drupal_set_message(t('There was a problem updating this note.'));
}

function notes_form_delete($form_state, $note) { 
    $form = array();
    $form["#prefix"] = t("Are you sure you want to delete note \"!title\"?", array('!title' => $note->title));
    $form["nid"] = array(
	    '#type' => 'hidden',
	    '#value' => $note->nid,
	);
	$form["title"] = array(
	    '#type' => 'hidden',
	    '#value' => $note->title,
	);
    $form["submit"] = array(
		'#type' => 'submit',
		'#value' => t('Delete'),
	);
	$form['cancel'] = array(
		'#type' => 'button',
		'#attributes' => array('onClick' => 'location.replace("'. referer_uri() .'"); return false;'),
		'#value' => t('Cancel'),
    );
	$form['#redirect'] = 'notes';
	return $form;
}

function notes_form_delete_submit($form, &$form_state) {
    global $user;
    $result = db_query("DELETE FROM {notes} WHERE nid = %d AND uid = %d", $form_state['values']['nid'], $user->uid);
    if(db_affected_rows()) {
        drupal_set_message(t("Note \"!title\" has been deleted.", array('!title' => $form_state['values']['title'])));
    }
    else {
        drupal_set_message(t('There was a problem deleting this note.'));
    }
}