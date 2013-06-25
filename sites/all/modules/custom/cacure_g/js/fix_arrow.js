(function ($) {
  Drupal.behaviors.cacureG_fixArrow = function(context) {
    $('#block-menu-menu-pvt-account ul.menu a[href*=' + Drupal.settings.fix_arrow_list + ']').attr('class', 'active').parent().attr('class', 'leaf active-trail-0');
  }
})(jQuery)