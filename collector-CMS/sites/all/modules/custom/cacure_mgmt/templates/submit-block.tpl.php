<?php 

// Template for submit my questionniare block.

?>
<?php global $base_path?>
<div id="submit_questionnaire">
	<!--<h2>
		Submit your health information when all parts are completed</h2>
	<p>Thank you for taking part.  Please keep in mind that once your health information is submitted, you will not be able to make changes. </p>-->
	<div id="button-wrap">
    <p class="cancel"><a class="submit" href="/" title="Cancel">Cancel</a></p>

        <form id="cacure-submit-form" action="<?php echo $base_path?>management/submitmodule/<?php echo arg(1)?>" method="post"><input type="hidden" value="<?php echo $arg['id']?>" id="edit-cacure-mail-form" name="form_id" /></form>
        <p class="form-submit form-module-submit <?php echo $arg['class']?>" 
        <?php if($arg['class'] == "form-module-submit-enabled"){ ?> onclick="getElementById('cacure-submit-form').submit()" <? } ?>
        ></p>
        </div><!-- end button wrap -->
        <p class="button-text">
        <?php if($arg['class'] == "form-module-submit-disabled"){ ?>
        Submit is inactive because you haven't completed or approved all Quarterly Report Section parts. Please go back and check status of all forms.
        <? } else { ?>
        The Submit button is active.  If you are ready, submit your data.
        <? } ?>
		</p>

</div>
