<?php
// $Id: block.tpl.php,v 1.3 2007/08/07 08:39:36 goba Exp $
?>
<? $class = str_replace(' ', '-', $block->title); //Strips empty spaces and replaces with dashes ?>
<? $class = strtolower($class); //makes letter lowercase for ease of CSS Styling ?>
<div id="block-<?php print $block->module .'-'. $block->delta; ?>" class="clear-block block <?php echo $block->region ?> <?php echo $class ?>">
  <div class="content"><?php print $block->content ?></div>
</div>
