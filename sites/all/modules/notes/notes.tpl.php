<?php
/**
 *
 * Theme implementation to display a list of notes.
 *
 * Available variables:
 * - $nids: an array of note nids.
 *
 */

?>

<div class="notes">
	<p class="notes-description"><?php print variable_get('notes_page_description', '') ?></p>
	<a class="notes-add-note-link" href="<?php print url('notes/add'); ?>"><?php print variable_get('notes_add_link', 'Add Note'); ?></a>
    <?php if(isset($nids) && count($nids)): ?>
    	<?php foreach ($nids as $nid): ?>
    	    <?php print theme('note', $nid); ?>
    	<?php endforeach;?>
    <?php endif; ?>
</div>