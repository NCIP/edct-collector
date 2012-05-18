function consentAccept() {
  var consent = $('#consent_options input:radio:checked').val();
  if (consent == 1) {
    window.location = '/user/register?c';
  } else {
    $("#dialog-modal").dialog('open');
  }
}

$(function() {
  $("#dialog-modal").dialog({
    height: 140,
    modal: true,
    resizable: false,
    autoOpen: false
  });
});

function consentBack() {
    $("#dialog-modal").dialog('close');
}

function consentReject() {
    window.location = '/';
}

function consentQuestion() {
    window.location = '/contact-HOW';
}
