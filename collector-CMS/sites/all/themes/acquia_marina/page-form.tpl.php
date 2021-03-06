<?php
  global $base_path; $xml = $_SESSION['xml_arg'];

  $site_fields = array();

  if ($site_name) {
    $site_fields[] = check_plain($site_name);
  }
  if ($site_slogan) {
    $site_fields[] = check_plain($site_slogan);
  }
  $site_title = implode(' ', $site_fields);
  if ($site_fields) {
    $site_fields[0] = '<span>'. $site_fields[0] .'</span>';
  }
  $site_html = implode(' ', $site_fields);
  if($is_front){ $class = "home"; } else{$class = $body_classes . " " . classify($title);}
?>
<?php /*
  <?php print '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>'."\n"; ?> */
//  header("Content-type: text/xml");
?>
<!DOCTYPE ROOT_XML_ELEMENT [ <!ENTITY copy  "&#169;"> <!ENTITY nbsp  "&#160;"> ]> 

<?php print '<?xml-stylesheet href="' . $base_path . drupal_get_path('module', 'cacure_x').'/xsltforms/xsltforms.xsl" type="text/xsl"?>'."\n"; ?>
<?php echo '<?css-conversion no?>' ?>
<?php echo '<?xsltforms-options debug="no"?>' ?>

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xform="http://www.w3.org/2002/xforms">
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <!--[if IE 8]>
      <?php print $ie8_styles; ?>
    <![endif]-->

    <title><?php print $head_title; ?></title>

    <?php print $head; ?>
    <?php print $styles; ?>
    <?php print $setting_styles; ?>
<!--        --><?php //print $scripts; ?>

    <?php if ($local_styles): ?>
      <?php print $local_styles; ?>
    <?php endif; ?>

    <?php echo $xml['head']?>
    <link type="text/css" rel="stylesheet" media="all" href="<?php echo $base_path . drupal_get_path('module', 'cacure_x')?>/xsltforms/custom_styles.css" />
    <script type="text/javascript" src="/misc/jquery.js" ></script>
    <script type="text/javascript" src="<?php echo $base_path . drupal_get_path('module', 'cacure_x')?>/xsltforms/xsltforms.js" ></script>
    <script type="text/javascript" src="<?php echo $base_path . drupal_get_path('module', 'cacure_x')?>/xsltforms/dialog_box.js" ></script>
    <script type="text/javascript" src="<?php echo $base_path . drupal_get_path('module', 'cacure_mgmt')?>/js/cacure_mgmt_xf.js" ></script>

    <script type="text/javascript">
      (function ($) {
        $(document).ready(function (){
          $('#block-menu-menu-pvt-account ul.menu a[href*=/my-current-questionnaire]').attr('class', 'active').parent().attr('class', 'leaf active-trail-0');

          pathArray = window.location.pathname.split( '/' );
           var id = pathArray['3'];

           $('#block-cacure_mgmt-1 div.form-element .text').removeClass('active');

           $('#block-cacure_mgmt-1 div.form-element #' + id).addClass('active');
           
           $('table.hcitComplexTable').each(function(){
                $(this).find('th:last').attr('colspan', 10);
            });
            $('.menu li a').click(function(){
				menuclicks++;
				if (menuclicks == 1) {
					if (confirm('Are you sure you want to navigate away from this page?' + "\r\n" +
						'Navigating away from this screen will cause any changes to be lost. You can click on \'Save for Later\' at the bottom of the form page before trying to navigate away.' + "\r\n" +
						'Please OK to continue, or Cancel to stay on the current page.')) {
						return true;
					} else {
						return false;
					}
				} else {
					menuclicks = 0;
				}
			});
            //correct buttons
            correctButtons('.hcit-approve-action-group', '.hcit-decline-action-group');
            correctButtons('.hcit-save-for-later-group', '.hcit-submit-action-group');
			/*var content = $('#sidebar-first').html();
			content = content + "\r\n" + '<div class="sidebar-first-spacer"></div>';
			$('#sidebar-first').html(content);*/
            $('.hcitSimpleTable tr').each(function(){
                var items = $(this).find('.xforms-item');
                
                if (items.length == 5)
                {
                    $(this).find('.xforms-itemset').css('width', '100%');
                    items.css({'padding' : 0, 'margin' : 0, 'width' : '80px'});
                }
            });
        });
        
        function correctButtons(main, related)
        {
            main = $(main);
           
            if (main.length)
            {
                main.css('overflow', 'hidden').css('visibility', 'visible');
                related = $(related + ' .xforms-group-content');
                main.find('.xforms-group-content').css({'float' : 'left', 'margin-right' : '10px'});
                related.css({'float' : 'left'});
                main.append(related);
            }
        }
      })(jQuery)
    </script>


    <?php if(isset($head_title)):?><title><?php echo $head_title?></title><?php endif?>

  </head>
  <body id="<?php print $body_id; ?>" class="<?php print $body_classes; ?>">
    <?php if(team_devel_detect_browser() == 'IE(remove)'):?>
       <script>
        var url = location.protocol + '//' + location.host + '/';
        // You may want to place these lines inside an onload handler
        CFInstall.check({
          mode: "overlay",
          destination: url
        });
      </script>
    <?php endif?>

 <div id="page" class="page xform-page">
    <div id="page-inner" class="page-inner">
    <div id="skip">
      <a href="#main-content-area"><?php print t('Skip to Main Content Area'); ?></a>
    </div>

    <!-- header-top row: width = grid_width -->
  <?php print theme('grid_row', $header_top, 'header-top', 'full-width', $grid_width); ?>

  <?php if ($logged_in): ?>
    <h2 class="user-name">Hello <?php print l(getName($user->uid), 'user/edit'); ?></h2>
    <!-- Displays User Name when Logged in -->
  <?php endif; ?>

      <!-- header-top row: width = grid_width -->

      <!-- header-group row: width = grid_width -->
      <div id="header-group-wrapper" class="header-group-wrapper full-width">
        <div id="header-group" class="header-group row <?php print $grid_width; ?>">
          <div id="header-group-inner" class="header-group-inner inner clearfix">
            <?php if ($search_box || $secondary_links): ?>
            <div id="header-group-inner-top" class="clearfix">
              <?php print theme('grid_block', $search_box, 'search-box'); ?>
              <?php print theme('grid_block', theme('links', $secondary_links), 'secondary-menu'); ?>
            </div>
            <?php endif; ?>
            <?php if ($logo || $site_name || $site_slogan || $header): ?>
            <div id="header-group-inner-inner" class="clearfix">
              <?php if ($logo || $site_name || $site_slogan): ?>
              <div id="header-site-info" class="header-site-info block">
                <div id="header-site-info-inner" class="header-site-info-inner inner">
                  <?php if ($logo): ?>
                  <div id="logo">
                    <a href="<?php print check_url($front_page); ?>" title="<?php print t('Home'); ?>"><img src="<?php print $logo; ?>" alt="<?php print t('Home'); ?>" /></a>
                  </div>
                  <?php endif; ?>
                  <?php if ($site_name || $site_slogan): ?>
                  <div id="site-name-wrapper" class="clearfix">
                    <?php if ($site_name): ?>
                    <span id="site-name"><a href="<?php print check_url($front_page); ?>" title="<?php print t('Home'); ?>"><?php print $site_name; ?></a></span>
                    <?php endif; ?>
                    <?php if ($site_slogan): ?>
                    <span id="slogan"><?php print $site_slogan; ?></span>
                    <?php endif; ?>
                  </div><!-- /site-name-wrapper -->
                  <?php endif; ?>
                </div><!-- /header-site-info-inner -->
              </div><!-- /header-site-info -->
              <?php endif; ?>
              <?php print $header; ?>
            </div>
            <?php endif; ?>
            <?php print theme('grid_block', $primary_links_tree, 'primary-menu'); ?>
          </div><!-- /header-group-inner -->
        </div><!-- /header-group -->
      </div><!-- /header-group-wrapper -->

      <!-- preface-top row: width = grid_width -->
      <?php print theme('grid_row', $preface_top, 'preface-top', 'full-width', $grid_width); ?>
  
      <?php if (!$preface_top): ?>
      <div id="preface-top-wrapper" class="preface-top-wrapper full-width">
        <div id="preface-top" class="preface-top row grid16-16">
          <div id="preface-top-inner" class="preface-top-inner inner clearfix">
          </div><!-- /preface-top-inner -->
        </div><!-- /preface-top -->
      </div><!-- /preface-top-wrapper -->
      <?php endif; ?>

    <!-- main row: width = grid_width -->
    <div id="main-wrapper" class="main-wrapper full-width">
      <div id="main" class="main row <?php print $grid_width; ?>">
        <div id="main-inner" class="main-inner inner clearfix">
          <?php print theme('grid_row', $sidebar_first, 'sidebar-first', 'nested', $sidebar_first_width); ?>

          <!-- main group: width = grid_width - sidebar_first_width -->
          <div id="main-group" class="main-group row nested <?php print $main_group_width; ?>">
            <div id="main-group-inner" class="main-group-inner inner clearfix">
              <?php print theme('grid_row', $preface_bottom, 'preface-bottom', 'nested'); ?>

              <div id="main-content" class="main-content row nested">
                <div id="main-content-inner" class="main-content-inner inner clearfix">
                  <!-- content group: width = grid_width - (sidebar_first_width + sidebar_last_width) -->
                    <div id="content-group" class="content-group row nested <?php print $content_group_width; ?>">
		      <div id="content-group-inner" class="content-group-inner inner clearfix">
                        <?php print theme('grid_block', $breadcrumb, 'breadcrumbs'); ?>

                        <?php if ($content_top || $help || $messages): ?>
                        <div id="content-top" class="content-top row nested">
                          <div id="content-top-inner" class="content-top-inner inner clearfix">
                            <?php print theme('grid_block', $help, 'content-help'); ?>
                            <?php print theme('grid_block', $messages, 'content-messages'); ?>
                            <?php print $content_top; ?>
                          </div><!-- /content-top-inner -->
                        </div><!-- /content-top -->
                        <?php endif; ?>

                        <div id="content-region" class="content-region row nested">
                          <div id="content-region-inner" class="content-region-inner inner clearfix">
                            <a name="main-content-area" id="main-content-area"></a>
                            <?php print theme('grid_block', $tabs, 'content-tabs'); ?>
                            <div id="content-inner" class="content-inner block">
                              <div id="content-inner-inner" class="content-inner-inner inner clearfix">
                                <?php if ($content): ?>
                                <div id="content-content" class="content-content">
                                  <?php print $content; ?>
                                  <?php print $feed_icons; ?>
                                </div><!-- /content-content -->
                                <?php endif; ?>
                                <div id="xform-block" class="xform-block block block-<?php echo $xml['block_id']?>"><?php  echo  $xml['body']?></div>
                              </div><!-- /content-inner-inner -->
                            </div><!-- /content-inner -->
                          </div><!-- /content-region-inner -->
                        </div><!-- /content-region -->

                        <?php print theme('grid_row', $content_bottom, 'content-bottom', 'nested'); ?>
                      </div><!-- /content-group-inner -->
                    </div><!-- /content-group -->

                    <?php print theme('grid_row', $sidebar_last, 'sidebar-last', 'nested', $sidebar_last_width); ?>
                  </div><!-- /main-content-inner -->
                </div><!-- /main-content -->

                <?php print theme('grid_row', $postscript_top, 'postscript-top', 'nested'); ?>
              </div><!-- /main-group-inner -->
            </div><!-- /main-group -->
          </div><!-- /main-inner -->
        </div><!-- /main -->
      </div><!-- /main-wrapper -->

      <!-- postscript-bottom row: width = grid_width -->
      <?php print theme('grid_row', $postscript_bottom, 'postscript-bottom', 'full-width', $grid_width); ?>

      <!-- footer row: width = grid_width -->
      <?php print theme('grid_row', $footer, 'footer', 'full-width', $grid_width); ?>

      <!-- footer-message row: width = grid_width -->
      <div id="footer-message-wrapper" class="footer-message-wrapper full-width">
        <div id="footer-message" class="footer-message row <?php print $grid_width; ?>">
          <div id="footer-message-inner" class="footer-message-inner inner clearfix">
            <?php print theme('grid_block', $footer_message, 'footer-message-text'); ?>
          </div><!-- /footer-message-inner -->
        </div><!-- /footer-message -->
      </div><!-- /footer-message-wrapper -->

    </div><!-- /page-inner -->
  </div><!-- /page -->
  <?php  print $closure; ?>
  </body>
</html>
