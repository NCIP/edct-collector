function(doc)
{  
    if (doc.entityId && doc.formId)
 	{   
	    emit([doc.entityId, doc.formId], doc);
	}
}