/**
 * @TODO: add theming message function
 */
//TODO Можно вставить в log
//var consoleLog = false;

/**
 * Log Objects.
 * @param message
 */
jQuery.log = function(message) {
  if(window.console) {
     console.debug(message);
  } else {
     alert(message.toSource() + '    Enable browser console to see more..');
  }
};
/**
 * To trim strings.
 * @param string
 */
function trim(string) {
  return string.replace(/(^\s+)|(\s+$)/g, "");
}