function show_hide_module(module_id) {
	if ($('.module_forms_'+module_id).css('display') == 'none') {
		$('.sh_link_'+module_id).html('-');
		$('.module_forms_'+module_id).fadeIn();
	} else {
		$('.sh_link_'+module_id).html('+');
		$('.module_forms_'+module_id).fadeOut();
	}
}