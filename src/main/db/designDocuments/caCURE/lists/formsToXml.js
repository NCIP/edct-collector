function(head, req) {
  var template = this.templates.forms;
  var Mustache = require("vendor/couchapp/lib/mustache");
  var caCURE = require("hcit/lib/cacure"); 
  var row;
  var formsDocument = new Object();
  var formsArray = new Array();
  var document = new Object();

  while (row = getRow())
  {
	var doc = row["value"];
	document = caCURE.prepareDoc(doc);
	document["ownerId"] = doc.ownerId;
	document["moduleId"] = doc.moduleId;
	formsArray.push(document);	
  }
  formsDocument["forms"] = formsArray;
  return Mustache.to_html(template, formsDocument);
}