function(head, req) {
  var template = this.templates.formAllEntities;
  var Mustache = require("vendor/couchapp/lib/mustache");
  var caCURE = require("hcit/lib/cacure"); 
  var row;
  var globalDocument = new Object();
  var owners = new Array();
  globalDocument["owners"] = owners;

  while (row = getRow())
  {
	var doc = row["value"];
	document = caCURE.prepareDoc(doc);
	var ownerObject = new Object();
	globalDocument.name=document.name;
	globalDocument.formId = document.formId;
	ownerObject.ownerId = doc["ownerId"];

	ownerObject["questions"] = document["questions"];
	ownerObject["simpleTables"] = document["simpleTables"];
	ownerObject["complexTables"] = document["complexTables"];
	
    owners.push(ownerObject);
  }
  return Mustache.to_html(template, globalDocument);
}