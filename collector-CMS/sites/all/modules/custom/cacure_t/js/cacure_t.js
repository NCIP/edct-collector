/* Function for Changing the text sized dynamically and saves the preference with Cookies */

window.onload = function() {
  getCookie('textsize');
}


function getCookie(c_name) {
  if (document.cookie.length > 0) {
    c_start = document.cookie.indexOf(c_name + "=");
    if (c_start != -1) {
      c_start = c_start + c_name.length + 1;
      c_end = document.cookie.indexOf(";", c_start);
      if (c_end == -1) c_end = document.cookie.length;
      var cook = unescape(document.cookie.substring(c_start, c_end));
      changeColor(cook);
      changeText(cook);
      return cook;
    }
    else {
      textSize('medium');
      /* Sets the default size */
    }
  }
  return "";
}


function setCookie(c_name, value, expiredays) {
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + expiredays);
  document.cookie = c_name + "=" + escape(value) +
          ((expiredays == null) ? "" : ";expires=" + exdate.toUTCString(),"","")
}

function changeColor(size) {
  var size_array = Array('small', 'medium', 'large');
  for ($i = 0; $i < size_array.length; $i++) {
    if (size_array[$i] == size) {
      document.getElementById(size).style.color = "#05a2fd";
    }
    else {
      document.getElementById(size_array[$i]).style.color = "#006cab";
    }
  }

}

function delete_cookie(cookie_name) {
  var cookie_date = new Date();  // current date & time
  cookie_date.setTime(cookie_date.getTime() - 1);
  document.cookie = cookie_name += "=; expires=" + cookie_date.toGMTString();
}


function changeText(size) {
  var size_array = Array('small', 'medium', 'large');
  var font_array = Array(100, 109, 125);
  for ($i = 0; $i < size_array.length; $i++) {
    var fonts = font_array[$i] + "%";
    if (size_array[$i] == size) {
      /*document.body.style.fontSize=fonts;*/
      document.getElementById('main').style.fontSize = fonts;
    }
    else {
      /*document.getElementById(size).style.fontSize=fonts; */
    }
  }

}

function textSize(size) {
  delete_cookie('textsize');
  setCookie('textsize', size, 365);
  changeColor(size);
  changeText(size);

}