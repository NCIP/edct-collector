$Id: README.txt,v 1.1.2.2 2009/11/28 10:38:16 WebNewCastle Exp $

-- SUMMARY --

Custom Node Template allows one to specify different node templates to be used on 
a node by node basis or by content type.


-- REQUIREMENTS --

None.


-- INSTALLATION --

* Install as usual, see http://drupal.org/node/70151 for further information.


-- CONFIGURATION --

* Configure user permissions in Administer >> User management >> Permissions >>
  custom_node_template module:

  - administer custom node templates

    Users in roles with the "administer custom node templates" permission will 
    see the node template settings fieldset on node and content type forms.


-- HELP --

If you have activated the core Help module, you will find additional information
in the Online Help section - /admin/help.  Additional help and information is
also available in the Drupal Handbook - http://drupal.org/node/639580.


-- FAQ --


Q: How do I use this module?

A: You can specify the use of a specific node template (node.tpl) file when you 
are creating or editing a node.  Under Node Template Settings, you will find a 
select list of the node templates in your current theme.  Simply the select the 
one you wish to have used when the node is displayed.  You can also specify the 
node template to be used by default in the Content Type settings.  Nodes you 
create will use this setting as the default unless you choose a different node 
template when you create a new node.


Q: How do I add custom node templates?

A: To add a custom node template, simply create an additional node.tpl file and 
add it to your theme.  The format for naming the file should be node-XXXXXX.tpl 
where XXXXXX is a custom name that you specify.  Additional tips and suggestions
for naming tpl files can be found in the Help module.


Q: Why would I use this module?

A: This module is probably most useful for customizing the node template to be 
used on a node-by-node basis.  It can also be used to specify a particular node
template by content type.  However, it is not necessary to do this if you are 
only using one node template per content type.  By default Drupal will load the 
appropriate node template for the content type if one exists in your theme in
the appropriate format for template suggestons.  In general, this module would
most likely be useful for overriding or customing the display of node on a node-
by-node basis or to provide such options to those who don't have access or the
ability to customize a theme.  It can still be useful for advanced users and 
developers as well when it is not convenient, feasible, or efficient to customize 
a theme to achieve the same functionality.



-- CONTACT --

Current maintainers:
* Matthew Winters (WebNewCastle) - http://drupal.org/user/449192


This project is sponsored/maintained by:
* WebNewCastle
  As "Builders of Your Next Home on the Web", WebNewCastle provides various Drupal 
  services including custom theme development, training, consulting, and site 
  development for projects ranging from simple business sites, to eCommerce, to 
  online community, and more.  Visit http://www.webnewcastle.com for more information.



