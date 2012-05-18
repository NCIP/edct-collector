<?php
/**
 *
 * Theme implementation to display a note.
 *
 * Available variables:
 * - $nid: the nid of the note.
 * - $created: the timestamp the note was created.
 * - $title: the title of the note.
 * - $body: the body of the note.
 *
 */
?>
<div class="note note-<?php print $nid; ?>">
	<h2 class="note-title"><?php print $title; ?></h2>
	<p class="note-created">Added on <?php print date('F j, o', $created); ?></p>
	<p class="note-body"><?php print $body; ?></p>
	<div class="note-links">
		<a class="note-edit" href="<?php print url('notes/edit/'. $nid); ?>"><?php print variable_get('notes_edit_link', 'Edit'); ?></a>
		<a class="note-delete" href="<?php print url('notes/delete/'. $nid); ?>"><?php print variable_get('notes_delete_link', 'Delete'); ?></a>
	</div>
</div>