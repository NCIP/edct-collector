<?php // $Id: block.tpl.php,v 1.4 2009/05/04 20:52:54 jmburnz Exp $
/**
 * @file
 *  block.tpl.php
 *
 * Theme implementation to display a block.
 *
 * @see template_preprocess()
 * @see template_preprocess_block()
 */
?>
<? if(($block->delta != 59) 
  && ($block->delta != 61)
  && ($block->delta != 64)
  && ($block->delta != 66)
  && ($block->delta != 68)
  && ($block->delta != 62)
  && ($block->delta != 67)
  && ($block->delta != 51)
  && ($block->delta != 54)
  && ($block->delta != 56)
  && ($block->delta != 58)
  && ($block->delta != 52)
  && ($block->delta != 55)
  && ($block->delta != 57)
  && ($block->delta != 71)
  && ($block->delta != 72)
  && ($block->delta != 70)
  && ($block->delta != 69)
  && ($block->delta != 60)):?>
<div id="block-<?php print $block->module .'-'. $block->delta; ?>" class="block <?php print $block_classes; ?>">
  <div class="block-inner">

    <?php if ($block->subject): ?>
      <h2 class="block-title"><?php print $block->subject; ?></h2>
 	 <? else: ?>
	<h2 class="block-title"></h2>
    <?php endif; ?>

    <div class="block-content">
      <div class="block-content-inner">
        <?php print $block->content; ?>
      </div>
    </div>

  </div>
</div> <!-- /block -->
<? else: ?>
<div style="width: 220px; margin: 0 auto;">
<?php print $block->content; ?>
</div>
<? endif; ?>