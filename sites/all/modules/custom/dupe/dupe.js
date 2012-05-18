$(document).ready(function() {
    Drupal.dupe.clearDOB();
});

Drupal.dupe = {};

Drupal.dupe.clearDOB = function() {
    y = $("#edit-profile-dob-year").val();
    var years = '';
    var d = new Date();
    var max_year = d.getFullYear() - 18;
    for (i = 1900; i <= max_year; i++) years += '<option value="' + i + '">' + i + '</option>';    
    $("#edit-profile-dob-year").html(years);
    if (y > max_year) y = max_year;
    $("#edit-profile-dob-year").val(y);
}

Drupal.dupe.stepOne = function() {
    $("#user-register div fieldset:eq(0)").show();
    $("#user-register div fieldset:eq(1)").show();
    $("#user-register div fieldset:eq(2)").show();
    $("#edit-submit").val("Continue");
};

Drupal.dupe.stepTwo = function() {
    //$("#user-register div fieldset:eq(2)").slideDown();
};

Drupal.dupe.cancel = function() {
    window.location = '';
}

Drupal.dupe.clearErrors = function() {
    $("#user-register div fieldset input").removeClass('error');
};

Drupal.dupe.dupeUserYes = function() {
    $("#user-register").append('<input type="hidden" name="i_am_dupe" value="1">');
    $("#user-register").submit();
};

Drupal.dupe.dupeUserNo = function() {
    $("#user-register").append('<input type="hidden" name="i_am_dupe" value="0">');
    $("#user-register").submit();
};
