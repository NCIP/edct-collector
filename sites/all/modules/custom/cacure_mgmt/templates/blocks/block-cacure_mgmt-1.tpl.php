<?php
// $Id: block.tpl.php,v 1.3 2007/08/07 08:39:36 goba Exp $
  $string = "No new Quarterly Report Sections are available";
  $pos = strpos($block->content,$string);
  $context = caCure_get_contexts();
  if (!$context && arg(0) != 'form')
    $context = 'default';
  elseif (!$context) $context = arg(1);
?>

<div id="block-<?php print $block->module .'-'. 1 ?>" class="clear-block block <?php echo $block->region ?> <?php echo classify($block->title) ?>">
  <div class="content">
<?php if (strpos($_SERVER['REQUEST_URI'], 'node/71') !== false || strpos($_SERVER['REQUEST_URI'], 'my-current-questionnaire') !== false ) { ?>
    <div class="sub-head">
      <?php if($pos === false):?>
        <span class="status">
          <div id="st-def-form"></div><div class="st-text">  =  Finished</div>
        </span>
      <?php endif?>
    </div>
<?php } else { ?>
<style type="text/css">
.form-element-list { display: none; }
</style>
<?php } ?>
    <?php print $block->content; ?>
<?php if ((strpos($_SERVER['REQUEST_URI'], 'node/71') !== false || strpos($_SERVER['REQUEST_URI'], 'my-current-questionnaire') !== false) && strpos($block->content ,'No new questionnaires are available') === false ) { ?>
    <div style="clear:both"></div>
      <? if($pos === false):?>
        <p class="finish"><span class="part">Submit:</span><a href="/submit-questionnaire/<?php echo $context?>" title="Finish" >Submit your completed Quarterly Report</a><span><a href="/submit-questionnaire/<?php echo $context?>" title="Finish" >View</a></span></p>
      <?php endif?>
  </div>
<?php } ?>
</div>

