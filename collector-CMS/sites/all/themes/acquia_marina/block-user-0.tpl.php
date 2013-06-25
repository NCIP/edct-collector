<?php
// $Id: block.tpl.php,v 1.3 2007/08/07 08:39:36 goba Exp $
?>

<div id="block-<?php print $block->module .'-'. $block->delta; ?>" class="clear-block block <?php echo $block->region ?> <?php echo classify($block->title) ?>">

<h2><? print $block->subject ?></h2>
<p class="case-msg">Username & Password are case sensitive</p>
  <div class="content">
 <?php print $block->content ?>
    </div><!-- end content -->

</div>