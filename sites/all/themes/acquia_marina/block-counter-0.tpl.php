<?php
// $Id: block.tpl.php,v 1.3 2007/08/07 08:39:36 goba Exp $
?>

<div id="block-<?php print $block->module .'-'. classify($block->title); ?>" class="clear-block block <?php echo $block->region ?> <?php echo classify($block->title) ?>">

<?php $content = $block->content;
	$start = strpos($content, '<div>') + 5;
	$end = strpos($content, '</div>');
	$length = $end - $start;
	$content = substr($content, $start, $length);
  while(strlen($content) < 7){
    $content = "0" . $content;
  }
?>
<?php if (!empty($block->subject)): ?>
  <h2><?php print $block->subject ?></h2>
<?php endif;?>

  <div class="content">
  	<span class="date">As of <? echo date('n\/j\/Y'); ?></span>
  	<div id="counter"><?php print $content ?></div>
    <p class="rteright">
    <a href="join-now" title="Join Now">Sign up now</a>
    <a href="join-now" title="Join Now">&gt;</a>
    </p>
    </div><!-- end content -->

</div>