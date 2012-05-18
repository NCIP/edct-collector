<?php
// $Id: block.tpl.php,v 1.3 2007/08/07 08:39:36 goba Exp $
?>

<div id="block-<?php print $block->module .'-'. $block->delta; ?>" class="clear-block block <?php echo $block->region ?> <?php echo classify($block->title) ?>">

<h2><? print $block->subject ?></h2>
 <div class="content">
 	 <? print $block->content;?> 
 </div><!-- end content -->
  	<p class="small">Have questions or suggestions,</p>
  	<p class="rteright"><a href="Contact-us" title="contact us" >Contact Us</a><a href="Contact-us" title="contact us" >&gt;</a></p>

</div>
