<?php
// $Id$

/**
 * @file
 * Help file for the Custom Node Template module
 *
 * @ingroup custom_node_template
 */

$output = "<p>This</p> ";


 $output = "<p>Custom Node Template allows you to easily control the node template used to display a node on a 
		  a node-by-node basis (or by content type).</p>  
		  <p>This page has been created to provide help with using this module.  However, as this help page can
		  only be updated with new releases of the module, the following resources may be of use as sources of
		  additional and/or updated information, as well as use cases and examples on how to use this module:<ul>
		  <li><a href='http://drupal.org/project/custom_node_template' target='_blank'> Custom Node Template Project Page on Drupal.org</a></li>
		  <li><a href='http://drupal.org/node/639580' target='_blank'> Custom Node Template Handbook Page on Drupal.org</a></li> 
		  <li><a href='http://webnewcastle.com/custom-node-template' target='_blank'>Custom Node Template page on WebNewCastle.com</a></li></ul></p> 
		  <h2>How To Use</h2>
                  <p>You can specify the use of a specific node template (node.tpl) file when you are creating or 
		  editing a node.  Under Node Template Settings, you will find a list of the node templates in 
		  your current theme.  Simply select the one you wish to use to display the node.  (As an optional 
		  feature, if you have included thumbnails to graphically illustrate your node templates, these 
		  will be displayed with the list - see below).  You can also specify the node template to be used 
		  by default for a content type in the Content  Type settings.  Nodes you create will use this setting 
		  as the default unless you choose a  different node template when you create a new node.  
		  <p><b>Please Note</b>:&nbsp;that this does not mean that specifying a node template in a content 
		  type will affect existing nodes of that content type.  It simply means that the node template 
		  will be listed as the default template to be used when you create a new node.</p>
		  <p>When using this module, it is not necessary to specify the use of a particular node template. 
		  If one is not specified, the default node template will be loaded.  If you have more than one
		  node template in your theme, then Drupal will load a specific node template based on existing
		  template suggestions and available node templates.  If you do specify a node template, then your
		  selection will override default node templates and template suggestions.</p>
		  <h2>Settings</h2>
                  <p>There are no specific settings associated with this module aside from the Configure Permissions
		  settings.  You can edit the Node Template Settings for each of your content types if you wish to 
		  set which node template will be used by default when creating a new node.  If you are using CCK,
		  this module will allow you to move the location of the Node Template settings fieldset on your node
		  add/edit form.  Support for the Vertical Tabs module has also been added.  Other aspects of using
		  this module simply involves adding additional node templates to your theme, and optionally you can
		  add thumbnails to be included in the list of node templates.</p>
                  <h2>Custom Templates</h2>
		  <p>To add a Custom Node Template, simply create an additional node.tpl file and add it to your
		  theme.  The format for naming the file should be node-XXXXXX.tpl where XXXXXX is a custom name
		  that you specify.  See the Tips section below on some suggestions for naming schemes.  If your
		  theme includes templates for the node edit form (i.e. node template ending in -edit.tpl.php'), 
		  these will be excluded by the module since they don't apply to the display of a node.</p>
		  <p>If you wish to have thumbnails included in the list of node templates, simply add PNG images
		  to your theme (main folder) using the same name as the node template (minus the file extension).
		  For example, if you have a node template with the name of node-This-One.tpl.php, then adding a thumbnail
		  image named node-This-One.png will cause it to be displayed in the list of node templates when adding
		  or editing a node (and in the content type settings).</p>
                  <h2>Tips</h2>
                  <p>If you are only using one node template per content type, you do not need to specify the node
		  template in the Content Type settings.  By default, Drupal will load the appropriate node template 
		  for the content type if one exists in your theme.  For such usage, you actually don't need to use 
		  this module at all.</p>
		  <p>There are some naming schemes you will probably wish to avoid when adding custom node templates 
		  to your theme.  For example, unless you are doing so deliberately, you will probably not want to 
		  name a custom node template file node-4.tpl.  Depending on your theme, you may have a preprocess
		  function that adds the Node ID as a template suggestion for Drupal.  If this is the case with the
		  theme you are using, Drupal will load the node-4.tpl file for Node/4.  If you name your custom node
		  template with this same name, it would work when you choose the node template for a particular node,
		  but it will also cause the same node template to be loaded for Node/4 which you may or may not wish
		  to have happen.  As another example, you could include a node template that has a content type in 
		  the name of the node template.  But if you don't want to have that node template loaded as the default
		  for that content type, the another name would probably be better.</p>
                  <h2>Additional</h2>
                  <p>Additional variables are available to your node templates that allow you to display taxonomy terms 
                  separately by a specific vocabulary.  These can be added to any node template in your theme (node.tpl.php, 
                  node-blog.tpl.php, etc.).  To use the above function, instead of having the <code>\$terms</code> variable printed in your 
                  theme, you can now use <code>\$node_vocabulary[X]</code>, where 'X' is replaced with the vocabulary ID from your site. 
                  This displays the associated terms from the vocabulary in a themed list.</p>
                  <p>Separately, if needed, you can isolate to a specific term by using <code>\$node_vocabulary_term[X][Y]</code>, where 
                  'X' is the vocabulary ID and 'Y' is the term ID. This is not done as a themed list, and it is probably 
                  more useful to use with PHP if/then/else conditional statements for more advanced or specific displays of 
                  data in your node.
                  <p> By default, Drupal only displays your site's mission statement on the front page.  You can include your
                  mission statement on other pages by adding the variable <code>\$custom_mission</code> (instead of <code>\$mission</code>) (i.e. code:
                  <code>&lt;?php print \$customer_mission; ?></code> in your node or page templates (node.tpl.php, page.tpl.php, etc.).</p>";