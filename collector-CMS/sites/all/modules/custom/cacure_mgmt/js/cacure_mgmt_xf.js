// Theme menu navigation js file, use jQuery
//window.onbeforeunload = linkRedirect;


(function ($) {

  $(document).ready(function (){

     pathArray = window.location.pathname.split( '/' );

    
     var id = pathArray['3'];

     $('#block-cacure_mgmt-1 div.form-element .text').removeClass('active');

     $('#block-cacure_mgmt-1 div.form-element #' + id).addClass('active');
  });
})(jQuery);
