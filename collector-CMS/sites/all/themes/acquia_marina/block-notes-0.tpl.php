  <?php
// $Id: block.tpl.php,v 1.3 2007/08/07 08:39:36 goba Exp $
?>
<? $class = str_replace(' ', '-', $block->title); //Strips empty spaces and replaces with dashes ?>
<? $class = strtolower($class); //makes letter lowercase for ease of CSS Styling ?>
<div id="block-<?php print $block->module .'-'. $block->delta; ?>" class="clear-block block <?php echo $block->region ?> <?php echo $class ?>">

  <h4>My Notes - Latest Entry</h4>
  		<p class="line">Date Added</p>
		<? $note = notes_get_user_notes($user->uid);
		if($note[0])$noter = note_load($note[0]);
 		?>
 	<div class="content">
 		<? if($noter){ ?>
 			<? $note_body = $noter->body;
				$note_title = $noter->title;
				$note_date = $noter->created;
				$href = "/notes/edit/".$note[0]; 		
			 ?>
  			<p class="note"><a href="<? echo $href ?>" title="Edit Note" ><? echo $note_title ?></a> - <? echo $note_body ?></p>
            <p class="created"><?  print date(n."/".j."/".Y, $note_date); ?></p>
        <? }else{ ?>
        <p class="note">You do not have any notes.  <a href="/notes/add" title"Add a Notes">Click Here</a> to add one.</p>
        
        <? } ?>
  
  	</div><!-- end content -->
    
</div><!-- end block -->
