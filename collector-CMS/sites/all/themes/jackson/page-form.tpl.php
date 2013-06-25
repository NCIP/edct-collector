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

    <![endif]-->

    <title><?php print $head_title; ?></title>

    <?php print $head; ?>
    <?php print $styles; ?>
    <?php print $setting_styles; ?>
<!--    --><?php //print $scripts; ?>


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

<body id="xform-page" class="<?php print $body_classes; ?>">
<div id="page" class="clear-block page-forms">
    <div id="top-menu-wrap">
      <div id="top-menu">
        <?php if ($logged_in): ?>
          <!-- Displays User Name when Logged in -->
          <div class="top-user-name">Hello <?php print l(getName($user->uid), 'user/edit'); ?></div>
        <?php endif; ?>
        <?php print $top_menu; ?>
      </div>
    </div>
    <div id="header-wrap">
      <div id="header" class="container-12 clear-block">
        <div id="site-header" class="clear-block">
          <div id="branding" class="grid-6">
          <?php if ($linked_logo_img): ?>
            <span id="logo"><?php print $linked_logo_img; ?></span>
          <?php endif; ?>
          <?php if ($linked_site_name): ?>
            <h1 id="site-name"><?php print $linked_site_name; ?></h1>
          <?php endif; ?>
          <?php if ($site_slogan): ?>
            <div id="site-slogan"><?php print $site_slogan; ?></div>
          <?php endif; ?>
          </div>
          <div class="grid-6">
            <div id="user-links" class="clear-block"><?php print $header; ?></div>
            <?php if ($search_box): ?>
              <div id="search-box"><?php print $search_box; ?></div>
            <?php endif; ?>
          </div>



          <div id="site-menu" class="grid-12 alpha omega">
            <?php if ($primary_links || $superfish_menu): ?>
      <!-- PRIMARY -->
             <div id="<?php print $primary_links ? 'nav' : 'superfish' ; ?>">
               <?php
			      if ($primary_links) {
		            print theme('links', $primary_links);
				  }
				  elseif (!empty($superfish_menu)) {
				    print $superfish_menu;
				  }
               ?>
             </div>
            <?php endif; ?>
		  </div>
        </div>
      </div>
    </div>
    <!-- end of header -->

    <?php if ($slider): ?>
    <div id="slider-wrap" class="slider-content <?php if(!$slider) print 'empty' ?>">

           <div id="slider" class="container-12 clear-block">
             <div class="grid-12 region">
               <?php print $slider; ?>
             </div>
           </div>

    </div>
  <?php endif; ?>

    <div id="content-wrap-background">
    <?php if ($preface_one || $preface_two || $preface_three): ?>
      <div id="preface" class="container-12 clear-block">
         <?php if ($preface_one): ?>
        <div class="<?php print'grid-'.  12/$preface; ?>">
          <?php print $preface_one; ?>
        </div>
        <?php endif; ?>
        <?php if ($preface_two): ?>
        <div class="<?php print'grid-'.  12/$preface; ?>">
          <?php print $preface_two; ?>
        </div>
        <?php endif; ?>
        <?php if ($preface_three): ?>
        <div class="<?php print'grid-'. 12/$preface; ?>">
          <?php print $preface_three; ?>
        </div>
        <?php endif; ?>
      </div>
    <?php endif; ?>

    <div id="content-wrap" class="<?php print ns('container-12', $two_sidebars, -4); ?> clear-block">
      <div id="main-top" class="column <?php print ns('grid-12', $two_sidebars, -4, $left, 4, $right, 4) . ' ' . ns('push-0', $left, -4); ?>">
      <?php print $pre_content; ?>
      </div>
      <?php if ($left): ?>
      <div id="sidebar-left" class="column sidebar region grid-4 pull-8">
        <div class="border">
        <?php print $left; ?>
        </div>
      </div>
    <?php endif; ?>
      <div id="main" class="column <?php print ns('grid-12', $two_sidebars, -4, $left, 4, $right, 4) . ' ' . ns('push-0', $left, -4); ?>">
      <?php if ($tabs): ?>
        <div class="tabs"><?php print $tabs; ?></div>
      <?php endif; ?>
      <?php print $messages; ?>
      <?php print $help; ?>

      <div id="main-content" class="region clear-block">
        <?php print $content; ?>
        <div id="xform-block" class="xform-block block block-<?php echo $xml['block_id']?>"><?php  echo  $xml['body']?></div>
        <?php print $post_content; ?>
      </div>
      <?php print $feed_icons; ?>
      </div>

    

    <?php if ($right): ?>
      <div id="sidebar-right" class="column sidebar region grid-4">
        <div class="border">
        <?php print $right; ?>
        </div>
      </div>
    <?php endif; ?>

    </div>
    </div>



  <div id="footer-wrap" class="container-12 clear-block">
    <?php if ($bottom_one || $bottom_two || $bottom_three || $bottom_four): ?>
    <div id="bottom" class="container-12 clear-block">
      <?php if ($bottom_one): ?>
        <div class="region <?php print'grid-'.  12/$bottom; ?>">
          <?php print $bottom_one; ?>
        </div>
      <?php endif; ?>
      <?php if ($bottom_two): ?>
        <div class="region <?php print'grid-'.  12/$bottom; ?>">
          <?php print $bottom_two; ?>
        </div>
      <?php endif; ?>
      <?php if ($bottom_three): ?>
        <div class="region <?php print'grid-'. 12/$bottom; ?>">
          <?php print $bottom_three; ?>
        </div>
      <?php endif; ?>
      <?php if ($bottom_four): ?>
        <div class="region <?php print'grid-'. 12/$bottom; ?>">
          <?php print $bottom_four; ?>
        </div>
      <?php endif; ?>
    </div>
    <?php endif; ?>


    <div id="footer" class="region grid-12">
      <?php print $footer; ?>
    </div>
  </div>


  </div>
  <?php print $closure; ?>
</body>
</html>
